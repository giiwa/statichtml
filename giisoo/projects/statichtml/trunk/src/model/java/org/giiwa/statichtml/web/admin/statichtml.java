package org.giiwa.statichtml.web.admin;

import org.giiwa.core.bean.X;
import org.giiwa.core.json.JSON;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.giiwa.core.base.IOUtil;
import org.giiwa.framework.bean.Repo;
import org.giiwa.framework.bean.Repo.Entity;
import org.giiwa.framework.web.Model;
import org.giiwa.framework.web.Path;

public class statichtml extends Model {

  @Path(login = true, access = "access.config.admin")
  public void onGet() {

    String p = this.getString("f");
    if (X.isEmpty(p)) {
      p = "/";
    }
    File f = module.getFile(p);

    if (f != null) {
      if (!X.isSame(p, "/")) {
        this.set("f", f);
      }
      
      File[] list = f.listFiles();
      this.set("list", list);
    }

    this.show("/admin/statichtml.index.html");
  }

  @Path(path = "delete", login = true, access = "access.config.admin")
  public void delete() {
    JSON jo = new JSON();
    String f = this.getString("f");
    File f1 = module.getFile(f);
    if (f1 != null) {
      try {
        IOUtil.delete(f1);
      } catch (IOException e) {
        log.error(e.getMessage(), e);
      }
      jo.put(X.STATE, 200);
    } else {
      jo.put(X.STATE, 201);
      jo.put(X.MESSAGE, "not found the file");
    }
    this.response(jo);
  }

  public String path(File f) {
    try {
      String root = module.getPath() + "/view";
      String s1 = f.getCanonicalPath().replace(root, "");
      return s1;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return X.EMPTY;
  }

  @Path(path = "add", login = true, method = Model.METHOD_POST, access = "access.config.admin")
  public void add() {
    JSON jo = JSON.create();

    String url = this.getString("url");
    Entity e = Repo.load(url);

    if (e != null) {
      File f1 = module.getFile("/");
      File[] ff = f1.listFiles();
      if (ff != null) {
        for (File f2 : ff) {
          if (X.isSame("admin", f2.getName())) {
            continue;
          }
          try {
            IOUtil.delete(f2);
          } catch (IOException e1) {
            log.error(e1.getMessage(), e1);
          }
        }
      }

      String root = module.getPath() + "/view/";

      try {
        ZipInputStream in = new ZipInputStream(e.getInputStream());

        /**
         * store all entry in temp file
         */

        ZipEntry z = in.getNextEntry();
        byte[] bb = new byte[4 * 1024];
        while (z != null) {
          File f = new File(root + z.getName());

          if (z.isDirectory()) {
            f.mkdirs();
          } else {
            if (!f.exists()) {
              f.getParentFile().mkdirs();
            }

            FileOutputStream out = new FileOutputStream(f);
            int len = in.read(bb);
            while (len > 0) {
              out.write(bb, 0, len);
              len = in.read(bb);
            }

            out.close();
          }

          z = in.getNextEntry();
        }
      } catch (Exception e1) {
        log.error(e1.getMessage(), e1);
      } finally {
        e.delete();
      }

      jo.put(X.STATE, 200);
    } else {
      jo.put(X.STATE, 201);
      jo.put(X.MESSAGE, lang.get("file.missed"));
    }

    this.response(jo);
  }

}

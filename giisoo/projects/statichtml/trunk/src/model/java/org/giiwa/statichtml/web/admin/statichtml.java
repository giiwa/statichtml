package org.giiwa.statichtml.web.admin;

import org.giiwa.core.bean.X;
import org.giiwa.core.json.JSON;
import org.giiwa.core.bean.Helper.V;
import org.giiwa.core.bean.Helper.W;
import org.giiwa.framework.web.Model;
import org.giiwa.framework.web.Path;

public class statichtml extends Model {

	@Path(login = true, access = "access.config.admin")
	public void onGet() {
		int s = this.getInt("s");
		int n = this.getInt("n", 20, "number.per.page");

		W q = W.create();
		String name = this.getString("name");

		if (!X.isEmpty(name) && path == null) {
			q.and("name", name, W.OP_LIKE);
			this.set("name", name);
		}

		this.show("/admin/statichtml.index.html");
	}

	@Path(path = "delete", login = true, access = "access.config.admin")
	public void delete() {
		String id = this.getString("id");
		JSON jo = new JSON();
		jo.put(X.STATE, 200);
		this.response(jo);
	}

	@Path(path = "create", login = true, access = "access.config.admin")
	public void create() {
		if (method.isPost()) {
		  JSON jo = this.getJSON();
			V v = V.create().copy(jo, "name");
			v.set("content", this.getHtml("content"));

			this.set(X.MESSAGE, lang.get("create.success"));
			onGet();
			return;
		}

		this.show("/admin/statichtml.create.html");
	}

}

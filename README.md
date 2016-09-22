# 基于giiwa框架的静态html文件管理模块
关于giiwa， 请参阅 http://giiwa.org
用于静态HTML文件发布，管理，极大的方便静态HMTL开发。
<h1>功能介绍</h1>
<ul>
<li>静态zip包文件上传</li>
<li>文件／目录删除</li>
</ul>

<h1>开发使用</h1>
<ul>
<li>下载所有源码，然后直接导入Eclipse， 修改...</li>
<li>进入项目目录， 直接运行 ant编译打包, 会生成 statichtml_1.0.1_?????.zip </li>
<li>在你安装的giiwa 服务器中， 进入后台管理->系统管理->模块管理->上传模块，然后重启giiwa</li>
<li>重启后，进入后台管理->静态HTML，管理／上传静态zip文件。</li>
</ul>

你的静态资源文件需要使用zip压缩后，一次性上传，每次上传会自动清除上传上传的文件， 当然你也可以修改这种设计。
为了加速对静态资源文件的访问， 并没有对资源文件作映射，而直接复制文件到模块的发布目录中。

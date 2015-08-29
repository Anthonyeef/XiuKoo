#绣口网 第三方 Android 客户端
---
[绣口网](http://www.xiukoo.org) 的第三方 Android 客户端。一个网络访问以及图片加载，还有 RecyclerView 的练习作品。

使用了 Volley 和 Jsoup 库，抓取了绣口网上的数据内容。

起因在于V站上有V友询问绣口网是否有移动端支持，所以就有了这个项目。

功能上非常简单，代码也十分直观易读。用 Volley 读取了页面源代码，用 Jsoup 进行解析和简单的排版处理。解析规则在 MainActivity 里可以看到。使用了 Design Library 的 CardView 和 RecyclerView 来处理列表和样式。

特别感谢 GDG南阳，感谢[菜鸟饭团](http://www.gdgny.org/series/androidfan)提供的帮助。

#源代码在 GPLv 协议下发布
---
[LICENCE](https://github.com/Anthonyeef/XiuKoo/blob/master/LICENSE)








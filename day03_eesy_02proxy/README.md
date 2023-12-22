# 动态代理

[参考learn-jar代码](https://github.com/Zhu-junwei/learn-java/tree/master/base/src/main/java/com/zjw/proxy)

在Java升级到17后cglib不能使用了，报了如下的错误。

```
Exception in thread "main" java.lang.ExceptionInInitializerError
	at com.zjw.cglib.Client.main(Client.java:40)
Caused by: net.sf.cglib.core.CodeGenerationException: java.lang.reflect.InaccessibleObjectException-->Unable to make protected final java.lang.Class java.lang.ClassLoader.defineClass(java.lang.String,byte[],int,int,java.security.ProtectionDomain) throws java.lang.ClassFormatError accessible: module java.base does not "opens java.lang" to unnamed module @722c41f4
	at net.sf.cglib.core.ReflectUtils.defineClass(ReflectUtils.java:464)
```

文章：

https://www.cnblogs.com/zjw-blog/p/13953676.html
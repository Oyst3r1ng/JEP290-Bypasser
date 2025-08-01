# JEP290-Bypasser

使用 RASP 技术（Java Agent + 字节码插桩）来绕过 JEP-290 的反序列化限制机制。

## 说明

afanti 师傅去 Hook 的是 java.rmi.server.RemoteObjectInvocationHandler 类的 InvokeRemoteMethod 方法的第三个参数，相当于是没有动计算方法 Hash 的地方，而是去将非 Object 的参数改为 Object 的，文章在此：[Bypass JEP290攻击rmi](https://www.anquanke.com/post/id/200860)。而这个项目是去 Hook 了计算方法 Hash 的地方。

## 使用方法


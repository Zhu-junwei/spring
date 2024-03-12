[toc]

# Spring中的循环依赖注入DI

## 使用构造方法造成循环依赖

> 解决方法：在构造方法上对循环依赖对象使用`@Lazy`注解

```java
@Service("accountServiceA")
public class IAccountServiceAImpl implements IAccountServiceA {

    // 通过构造方法注入对象
    private final IAccountServiceB accountServiceB;

    public IAccountServiceAImpl(@Lazy IAccountServiceB accountServiceB) {
        this.accountServiceB = accountServiceB;
    }
}
```

```java
@Service("accountServiceB")
public class IAccountServiceBImpl implements IAccountServiceB {

    private IAccountServiceA accountServiceA;

    public IAccountServiceBImpl(@Lazy IAccountServiceA accountServiceA) {
        this.accountServiceA = accountServiceA;
    }
}
```

##  使用setter方法造成循环依赖

> setter方法造成循环依赖：这个是没有问题的，不需要处理，但是官方不建议使用setter进行依赖注入。
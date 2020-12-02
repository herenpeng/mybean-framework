# mybean-framework

> MyBean框架是一个仿Spring的轻量级框架，拥有IOC和DI两种功能。


## Bean实例注册

MyBean框架可以通过注解和配置文件两种方式注册一个Bean实例，被注册的Bean实例会进入MyBean框架的核心容器之中。

- 注解
 在Java类上标注@MyBean注解，即可将JavaBean注入核心容器。
 
  
 例：
 ```java
@MyBean
public class Student {
}
```
- 配置
 在application.properties配置文件中进行Java类名和容器Key值的对应配置，即可将JavaBean注入核心容器。
 
 例：
 ```java
com.mybean.test.Student=student
```


## Bean实例注入

MyBean框架目前只允许通过注解的方式注入一个Bean实例属性。

- 注解
 在类属性上添加@SetBean注解，即可将对应的实例注入类属性中。
 
 [备注]注入属性的Java类必须是已经在MyBean框架核心容器注册的JavaBean，否则无法注入。
 ```java
@MyBean
public class Person {

    @SetBean
    private Student student;

}
```
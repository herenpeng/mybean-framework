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
 
 在application.properties配置文件中进行容器Key值和Java类名的对应配置，即可将JavaBean注入核心容器。
 
 例：
 ```java
student=com.mybean.test.Student
```


## Bean实例注入

MyBean框架目前只允许通过注解的方式注入一个Bean实例属性。

- 注解
 
 在类属性上添加@SetBean注解，即可将对应的实例注入类属性中。
 
 [备注]注入属性的Java类必须是已经在MyBean框架核心容器注册的JavaBean，否则无法注入。

 例：
 ```java
@MyBean
public class Person {

    @SetBean
    private Student student;

}
```

# 包扫描

如果使用@MyBean注解的方式注册Bean实例，必须配置包扫描范围，这样MyBean框架核心容器才能够扫描被注解的Bean实例。

包扫描方式分为两种，注解和配置方式。

- 注解

 在启动类或者测试类上添加@PackageScan注解，即可扫描对应包下的所有@MyBean注解实例。
 
 @PackageScan有一个value属性，为包扫描范围，如果该值为空，则默认扫描标识@PackageScan注解的包下的所有@MyBean实例。
 
 例：
 ```java
@PackageScan("com.mybean.test")
public class ApplicationTest {

}
```

 ```java
package com.mybean.test;

@PackageScan
public class ApplicationTest {

}
```
 
 - 配置
 
 在application.properties配置文件中配置package.scan属性即可
 
 例：
 
 ```java
package.scan=com.mybean.test
```
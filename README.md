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
- xml配置
 
 在application.xml配置文件中进行容器id值和Java全限定类名的对应配置，即可将JavaBean注入核心容器。
 
 例：
 ```xml
<?xml version="1.0" encoding="UTF-8" ?>
<mybean>
    <beans>
        <bean id="person" class="com.mybean.test.Person"></bean>
        <bean id="student" class="com.mybean.test.Student"></bean>
    </beans>
</mybean>
```
- properties配置
 
 在application.properties配置文件中进行容器id值和Java全限定类名的对应配置，即可将JavaBean注入核心容器。
 
 例：
 ```java
student=com.mybean.test.Student
```


## Bean实例注入

MyBean框架目前允许通过注解的方式和xml配置的方式注入一个Bean实例属性。

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


- xml配置
 
 [备注]注入属性的Java类必须是已经在xml配置文件中配置过的JavaBean，否则无法注入。
 
 
 `<set>`标签有两个属性，其中name属性为Java类中对应的属性名称，ref为已经在xml配置文件中注册，或者开启包扫描后使用@MyBean注解标识的JavaBean的Id。

 例：
 ```xml
<?xml version="1.0" encoding="UTF-8" ?>
<mybean>
    <beans>
        <bean id="person" class="com.mybean.test.Person">
            <set name="student" ref="student"></set>
        </bean>
        <bean id="student" class="com.mybean.test.Student"></bean>
    </beans>
</mybean>
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
 - xml配置
 
  
 在application.xml配置文件中配置package-scan标签即可，range属性即为包扫描范围值。
 
 例：
 
 ```xml
<?xml version="1.0" encoding="UTF-8" ?>
<mybean>
    <package-scan range="com.test"></package-scan>
</mybean>
```
 
 - properties配置
 
 在application.properties配置文件中配置package.scan属性即可。
 
 例：
 
 ```java
package.scan=com.mybean.test
```
# mybean-framework

> MyBean框架是一个类似于Spring的简化版本的轻量级框架，拥有IOC和DI两种功能。


## Bean实例注册

MyBean框架可以通过注解、xml配置文件、properties配置文件三种方式注册一个Bean实例，被注册的Bean实例会进入MyBean框架的核心容器之中。

- 注解

 1、在Java类上标注@MyBean注解，即可将JavaBean注入核心容器。
 
 2、使用@MyBean注解方式注册Bean实例，必须开启包扫描。
 
 3、@MyBean注解有一个属性值，如果该属性值为空，则注册的Bean实例id默认为对应的类的首字母小写名称，如果该属性不为空，则注册的Bean实例id则为属性值。
  
 例：
 ```java
@MyBean
public class Student {
}
```
- xml配置
 
 1、在application.xml配置文件中进行容器id值和Java全限定类名的对应配置，即可将JavaBean注入核心容器。
 
 2、bean节点必须放在`<mybean>` `<beans>`节点下面，否则bean实例无法注册。
 
 3、bean节点的id值为必填，id值即为Bean实例注册的id名称，class属性必填，class值即为Bean实例的全限定类名。
 
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
 
 1、在application.properties配置文件中进行容器id值和Java全限定类名的对应配置，即可将JavaBean注入核心容器。
 
 例：
 ```java
student=com.mybean.test.Student
```


## Bean实例注入

MyBean框架目前允许通过注解的方式和xml配置的方式注入一个Bean实例属性。

- 注解
 
1、 在类属性上添加@SetBean注解，即可将对应的实例注入类属性中。

2、注入属性的Java类必须是已经在MyBean框架核心容器注册的JavaBean，否则无法注入。
  
3、@SetBean有一个属性值，如果属性值为空，则使用类型注入模式，如果属性值不为空，则使用id名称注入方式。

4、MyBean框架的注解注入方式是默认开启的，如果在一个属性中，既使用了@SetBean注解注入，又使用了xml配置文件注入，默认使用@SetBean注解注入的方式，xml配置方式默认放弃注入。

 例：
 ```java
@MyBean
public class Person {

    @SetBean
    private Student student;

    @SetBean("student")
    private Student student2;

}
```


- xml配置
 
 1、注入属性的Java类必须是已经在xml配置文件中配置过的JavaBean，否则无法注入。
 
 2、`<set>`标签有两个属性，其中name属性为Java类中对应的属性名称，ref为已经在xml配置文件中注册，或者开启包扫描后使用@MyBean注解标识的JavaBean的Id。

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

## 包扫描

如果使用@MyBean注解的方式注册Bean实例，必须配置包扫描范围，这样MyBean框架核心容器才能够扫描被注解的Bean实例。

包扫描方式分为三种，注解和xml配置文件和properties配置文件方式。

- 注解

 1、在启动类或者测试类上添加@PackageScan注解，即可扫描对应包下的所有@MyBean注解实例。
 
 2、@PackageScan有一个value属性，为包扫描范围，如果该值为空，则默认扫描标识@PackageScan注解的包下的所有@MyBean实例。
 
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
  
 1、在application.xml配置文件中配置package-scan标签即可，range属性即为包扫描范围值。
 
 例：
 
 ```xml
<?xml version="1.0" encoding="UTF-8" ?>
<mybean>
    <package-scan range="com.test"></package-scan>
</mybean>
```
 
 - properties配置
 
 1、在application.properties配置文件中配置package.scan属性即可。
 
 例：
 
 ```java
package.scan=com.mybean.test
```


## MyBeanMVC

MyBeanMVC是MyBean框架的Web开发部分，能够简化原生的Servlet开发。

### @RequestPath

@RequestPath为一个MVC层的注解，可以注解在类和方法上，有两个属性：
- value：value属性为请求路径，使用该属性可以将对应的类或方法映射到注解的类和方法上。
- type：type属性为一个ResponseTypeEnum枚举类型，该属性在类上无效，在方法上可以映射为响应数据的类型，该属性默认为ResponseTypeEnum.VIEW，默认解析返回值为视图。

例：
```java
@MyBean
@RequestPath("user")
public class UserController {

    @RequestPath("index")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        return "index.html";
    }
}
```

### 静态资源解析

1、MyBeanMVC目前只支持静态资源文件，对于`.jsp`，`.ftl`等动态文件暂不支持。

2、在MyBeanMVC中，所有的静态资源文件都需要放在resources包下的static文件夹中，否则无法解析。例如上述的案例代码中，返回的inex.html会默认解析为resources/static包下的index.html文件。

3、在MyBeanMVC的静态文件中，如果需要引入其他静态文件，请使用绝对路径，MyBeanMVC专属的`@`符号解析路径。使用相对路径引入静态文件，会无法解析静态文件。

4、其中的@符号为MyBean框架的静态资源解析符号，能够直接解析到resources/static文件下面。

例：

```java
resources
    |__static
        |__index.css
        |__index.html
```

在上述的文件目录层级中，在index.html文件中，应该使用如下方式引入index.css文件。

正确：
```html
<link rel="stylesheet" type="text/css" href="/index.css"/>
<link rel="stylesheet" type="text/css" href="@index.css"/>
```

错误：
```html
<link rel="stylesheet" type="text/css" href="index.css"/>
```

### ResponseTypeEnum枚举类型

@RequestPath注解的type属性为ResponseTypeEnum枚举类型，该枚举有三个属性值。

- VIEW：该值为@RequestPath注解type属性的默认值，默认为视图解析，会将返回值作为路径解析，会自动寻找resources/static包下的静态文件。

例：
```html
@MyBean
@RequestPath("user")
public class UserController {

    @RequestPath("index")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        // 会跳转到resources/static包的index.html文件
        return "index.html";
    }
}
```

- TEXT：该值会将方法返回值作为普通文本解析，
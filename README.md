# mybean-framework

> MyBean框架是一个类似于Spring的简化版本的轻量级框架。

> github地址：https://github.com/herenpeng/mybean-framework.git

**mybean-framwork目前支持功能：**

- IOC和DI功能
- 兼容Web开发
- HTTP请求路径映射
- 控制层注解式绑定请求参数
- 静态视图解析
- Json解析工具
- JDBC封装模板

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
 ```properties
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
 
 ```properties
package.scan=com.mybean.test
```


# mybean-mvc

MyBeanMVC是MyBean框架的Web开发部分，能够简化原生的Servlet开发。

## @RequestPath

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

## @RequestParam

@RequestParam是一个MVC层注解，用于解析HTTP请求上的参数，该注解只能标注在方法参数上。

1、@RequestParam注解有一个value属性值，该值为对应的参数名称，如果请求中有对应名称和类型的参数值，该注解会自动将参数解析到方法参数中。

2、@RequestParam注解目前只支持解析Java的八种基本数据类型和对应的包装类，以及Map类型，其余类型无法解析。Map类型入参，会将布尔类型参数和数字类型参数进行解析，并转换为Boolean，Integer，Long等类型。

3、MyBeanMVC的控制层方法参数注入只支持10个参数，超过10个参数，框架会出现解析异常。

4、如果控制层方法参数中有HttpServletRequest和HttpServletResponse类型，对应的参数会自动注入。

例：

```java
@MyBean
@RequestPath("user")
public class UserController {

    @RequestPath(value = "string", type = ResponseTypeEnum.JSON)
    public String string(@RequestParam("username") String username) {
        return username;
    }

    @RequestPath(value = "number", type = ResponseTypeEnum.JSON)
    public Integer number(HttpServletRequest request, HttpServletResponse response, @RequestParam("number") Integer number) {
        return number;
    }

}
```

## 静态资源解析

1、MyBeanMVC目前只支持静态资源文件，对于`.jsp`，`.ftl`等动态文件暂不支持。

2、在MyBeanMVC中，所有的静态资源文件都需要放在resources包下的static文件夹中，否则无法解析。例如上述的案例代码中，返回的inex.html会默认解析为resources/static包下的index.html文件。

3、在MyBeanMVC的静态文件中，如果需要引入其他静态文件，请使用绝对路径，MyBeanMVC专属的`@`符号解析路径。使用相对路径引入静态文件，会无法解析静态文件。

4、其中的@符号为MyBean框架的静态资源解析符号，能够直接解析到resources/static文件下面。

例：

```text
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

## ResponseTypeEnum枚举类型

@RequestPath注解的type属性为ResponseTypeEnum枚举类型，该枚举有三个属性值。

- VIEW：该值为@RequestPath注解type属性的默认值，默认为视图解析，会将返回值作为路径解析，会自动寻找resources/static包下的静态文件。

例：
```java
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

- TEXT：该值会将方法返回值作为普通文本解析，如果返回值为String类型，会直接输出字符串，如果返回值为其他类型，会默认调用返回对象的toString()方法输出字符串。

例：
```java
@MyBean
@RequestPath("user")
public class UserController {

    @RequestPath(value = "string", type = ResponseTypeEnum.TEXT)
    public String string(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        return username;
    }

    @RequestPath(value = "text", type = ResponseTypeEnum.TEXT)
    public User text(HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        user.setUsername("池总");
        user.setPassword("123456");
        return user;
    }
}
```


- JSON：该值会将方法返回值作为JSON格式解析。MyBean框架的JSON解析方式为MyBean框架自带的JSON解析工具，不依赖第三方JSON解析库，由于自带的JSON解析工具比较简单，目前JSON格式支持的类型有：
  
  - Java八大基本数据类型以及对应的包装类型
  - Date类型
  - BigDecimal类型
  - Map类型
  - Collection类型
  - 遵循规范的JavaBean类型

例：
```java
@MyBean
@RequestPath("user")
public class UserController {

    @RequestPath(value = "json", type = ResponseTypeEnum.JSON)
    public User json(HttpServletRequest request, HttpServletResponse response) {
        User user = new User("池总", "123456");
        return user;
    }

    @RequestPath(value = "map", type = ResponseTypeEnum.JSON)
    public Map<String, Object> map(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", "池总");
        map.put("password", "123456");
        map.put("enabled", true);
        return map;
    }

    @RequestPath(value = "list", type = ResponseTypeEnum.JSON)
    public List<User> list(HttpServletRequest request, HttpServletResponse response) {
        List<User> list = new ArrayList<>();
        list.add(new User("池总", "123456"));
        list.add(new User("刘老板", "111111"));
        list.add(new User("波波", "bbbbbb"));
        return list;
    }

    @RequestPath(value = "set", type = ResponseTypeEnum.JSON)
    public Set<User> set(HttpServletRequest request, HttpServletResponse response) {
        Set<User> Set = new HashSet<>();
        Set.add(new User("池总", "set:123456"));
        Set.add(new User("刘老板", "set:111111"));
        Set.add(new User("波波", "set:bbbbbb"));
        return Set;
    }

    @RequestPath(value = "listMap", type = ResponseTypeEnum.JSON)
    public List<Map<String, Object>> listMap(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("username", "池总");
        map1.put("password", "123456");
        map1.put("enabled", true);
        list.add(map1);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("username", "刘老板");
        map2.put("password", "111111");
        map2.put("enabled", false);
        map2.put("birthday", new Date());
        list.add(map2);
        Map<String, Object> map3 = new HashMap<>();
        map3.put("username", "刘老板");
        map3.put("password", "111111");
        map3.put("money", new BigDecimal(100000000));
        map3.put("enabled", false);
        map3.put("person", new User("员工1", "123456"));
        List<User> list2 = new ArrayList<>();
        list2.add(new User("池总", "123456"));
        list2.add(new User("刘老板", "111111"));
        list2.add(new User("波波", "bbbbbb"));
        map3.put("list2", list2);
        list.add(map3);
        return list;
    }
}
```

# mybean-common

## JsonUtils

MyBean框架没有依赖于第三方的Json解析框架，而是MyBean框架自己实现了一个JsonUtils，用于解析Json数据。

- Json工具类可以对以下的Java类型进行解析：
    - Java八大基本数据类型以及对应的包装类型
    - Date类型
    - BigDecimal类型
    - Map类型
    - Collection类型
    - 遵循规范的JavaBean类型
  
- @JsonNullIgnore

    @JsonNullIgnore可以注解在JavaBean的属性上，被注解的属性如果值为null，在格式化Json字符串的时候会自动忽略该属性。

例：
```java
public class User {

    private String username;

    @JsonNullIgnore
    private String password;
    // 省略get/set方法
}
```

- @JsonDateFormat

    @JsonDateFormat可以注解在JavaBean的Date类型的属性上，被注解的属性会按照注解的value值的格式，对日期格式进行Json格式化，如果value属性值为null，则会使用默认的`yyyy-MM-dd HH:mm:ss`格式进行日期格式化。


例：
```java
public class User {

    private String username;

    @JsonDateFormat
    private Date birthday;
    // 省略get/set方法
}
```

## StringUtils

StringUtils是MyBean框架封装的字符串工具类，包含了对于字符串的处理方法。

## ObjectUtils

对象工具类

## CollectionUtils

集合工具类

# mybean-jdbc

mybean-jdbc是MyBean框架的持久层部分，封装了MyBean框架对持久层的操作。
其中mybean-jdbc默认使用MySQL驱动，并内置了druid连接池。

## DataSource

在使用mybean-jdbc之前，需要在application.xml配置文件中配置数据源。

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<mybean>
    <data-source>
        <driver-name>com.mysql.cj.jdbc.Driver</driver-name>
        <url>jdbc:mysql://localhost:3306/mybean?serverTimezone=Asia/Shanghai&amp;useSSL=false&amp;characterEncoding=UTF-8</url>
        <username>root</username>
        <password>admin</password>
    </data-source>
</mybean>
```

## JdbcTemplate

1、在使用JdbcTemplate之前，需要在application.xml配置文件中配置该Bean实例。

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<mybean>
    <beans>
        <bean id="jdbcTemplate" class="org.mybeanframework.jdbc.JdbcTemplate"></bean>
    </beans>
</mybean>
```

2、然后就直接在Java类中注入即可使用。

```java
@MyBean
@RequestPath("user")
public class UserController {

    @SetBean
    private JdbcTemplate jdbcTemplate;

    @RequestPath(value = "list", type = ResponseTypeEnum.JSON)
    public List<User> list() {
        List<User> list = jdbcTemplate.selectList("select * from sys_user", User.class);
        return list;
    }

}
```

 - List<E> selectList(String sql, Object[] params, Class<E> clazz)
    
        有参查询所有，并返回一个泛型对应的List集合
 
 - List<E> selectList(String sql, Class<E> clazz)
    
        无参查询所有，并返回一个泛型对应的List集合
 
 - selectOne(String sql, Object[] params, Class<E> clazz)
    
        有参查询一个泛型对象，多条记录时，默认返回第一条记录
 
 - selectOne(String sql, Class<E> clazz)
 
        无参查询一个泛型对象，多条记录时，默认返回第一条记录
 
 - int update(String sql, Object[] params)
 
        有参更新方法
 
 - delete(String sql, Object[] params)
 
        有参删除方法
 
 - insert(String sql, Object[] params)
 
        有参插入方法
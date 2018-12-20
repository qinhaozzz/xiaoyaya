# Spring Bean的注册方式
## 注册方式
常用的bean注册方式有以下四种
* @ComponentScan自动扫描配置包名下所有使用@Service、@Compent、@Repository、@Controller的类，并注册为Bean

* @Bean在配置类中进行注册，经常用来导入引入的第三方Jar

* @Import，快速给容器中注册一个bean

* FactoryBean，首先它是一个bean，是一个可以创建对象的工厂bean，可以用来手动创建bean

## Spring Bean注册相关注解
### @Configuration
**简介**

声明当前类是一个配置类，相当于一个Spring配置的xml文件。把一个类作为一个IoC容器
和@Bean搭配使用，某个方法头上如果注册了@Bean，就会作为这个Spring容器中的Bean。

**详情**

1. 可以通过AnnotationConfigApplicationContext获取IOC容器

```java
// 方法一
AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
ctx.register(AppConfig.class);
ctx.refresh();
MyBean myBean = ctx.getBean(MyBean.class);

// 方法二
AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
MyBean myBean = ctx.getBean(MyBean.class);
```
### @Bean
**简介**

声明当前方法的返回值为一个Bean，和`<bean/>标签`功能相同

**详情**
1. bean默认id为方法名（可以通过applicationContext.getBeanDefinitionNames()查看bean的id值）

2. bean默认类型为方法返回类型

### @ComponentScan
**简介**

自动扫描配置包名下所有使用@Service、@Compent、@Repository、@Controller的类，并注册为Bean.

**详情**
1. basePackages = {"com.lim.bean.inner"}，指定扫描的包

2. excludeFilters={}，扫面的时候不包含的注解
  * 通过@ComponentScan.Filter配置不扫描的注解，参数type和classes
  * type=""，过滤方式，通过FilterType配置
  * classes={}，类对象

3. includeFilters={}，扫描的时候只包含的注解，**配置时需要禁用默认扫描规则，即useDefaultFilters=false**

4. JDK1.8后可以在一个类上面配置多个@ComponentScan注解，或者使用@ComponentScans配置多个扫描策略即@ComponentScan

```java
// 示例
@ComponentScan(
        basePackages = {"com.lim.bean.inner"}
        , excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Repository.class}), @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {InnerService.class})}
        , includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})}
        , useDefaultFilters = false
)

// FilterType过滤方式
public enum FilterType {
  /**
   * 注解 >常用<
   */
	ANNOTATION,
  /**
   * 类型 >常用<
   */
	ASSIGNABLE_TYPE,
  /**
   * ASPECTJ表达式
   */
	ASPECTJ,
  /**
   * 正则表达式
   */
	REGEX,
  /**
   * 自定义规则,即实现TypeFilter接口,重写match方法
   */
	CUSTOM
}

// 配置多个ComponentScan
@ComponentScans(value={@ComponentScan(),@ComponentScan()})
```
### @ComponentScan自定义扫描规则——TypeFilter
**简介**

当@Filter中type为FilterType.CUSTOM时，需要传入TypeFilter的实现类，通过自定义的规则判断当前扫描的类是否匹配

**详情**
```java

package com.lim.filter;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * @author qinhao
 */
public class CustomTypeFilter implements TypeFilter {
    /**
     * @param metadataReader        获取扫描类的信息
     * @param metadataReaderFactory 可以获取其他类的信息
     * @return
     * @throws IOException
     */
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        // 获取当前扫描类的注解信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        // 获取当前扫描类的类信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        // 获取当前扫描类的资源信息
        Resource resource = metadataReader.getResource();
        // 返回true表示匹配
        return false;
    }
}
```
### @Scope
**简介**

IOC容器中注册bean的作用域

**详情**

1. ConfigurableBeanFactory#SCOPE_PROTOTYPE("singleton")，默认是单实例模式 > IOC容器启动时将创建并初始化单实例对象 > 一个spring容器中只有一个Bean的实例，全容器共享一个实例。

2. ConfigurableBeanFactory#SCOPE_SINGLETON("prototype")，多实例模式 > IOD容器启动时不会创建对象，当获取对象getBean()时都会新建一个对象

3. org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST("request") > 给每一个http request新建一个Bean实例

4. org.springframework.web.context.WebApplicationContext#SCOPE_SESSION("session") > 给每一个http session新建一个Bean实例

### @Lazy
**简介**

懒加载：容器启动时不创建对象，第一次获取对象getBean时才会创建并初始化对象

```java
import com.lim.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

/**
 * <h3>{@code @Scope}</h3>
 * <h3>懒加载{@code @Lazy}</h3>
 *
 * @author qinhao
 */
@Configuration
public class MainConfig2 {

    @Scope("singleton")
    @Bean
    public Person singletonPerson() {
        System.out.println("singletonPerson()...");
        return new Person();
    }

    @Lazy
    @Scope("singleton")
    @Bean
    public Person singletonLazyPerson() {
        System.out.println("singletonLazyPerson()...");
        return new Person();
    }

    @Scope("prototype")
    @Bean
    public Person prototypePerson() {
        System.out.println("prototypePerson()...");
        return new Person();
    }
}
```
### @Conditaional
**简介**

根据条件判断该Bean是否需要被注册

**详情**

### @Profile
**简介**

profile就是一组配置，不同profile提供不同组合的配置，程序运行时可以选择使用哪些profile来适应环境。功能与@Conditional类似，都是当bean达到特定的条件时才会被注册。常用的profile配置方法有三种如下:
* 设置IOC容器环境变量Environment

```java
// 注册bean
@Bean("japanese")
@Profile("mainConfig3")
public Person japanese() {
    return new Person("东野圭吾", 60);
}
// 通过environment设置active profile
AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
applicationContext.getEnvironment().setActiveProfiles("mainConfig");
/**
 * 需要先给context设置参数，然后再注册Bean配置类，否则会出现Bean未定义异常NoSuchBeanDefinitionException
 */
applicationContext.register(MainConfig3.class);
// 刷新容器
applicationContext.refresh();
Person japanese = applicationContext.getBean("japanese");
```
* JVM参数spring.profiles.active

```java
-Dspring.profiles.active=mainConfig3
```
* web中通过servlet的context paramter参数设置

```java
// servlet2.5版本之前
<servlet>
        <servlet-name>dispatcher</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>spring.profiles.active</param-name>
            <param-value>production</param-value>
        </init-param>
</servlet>

// servlet3.0版本之后
import javax.servlet.ServletContext;

import org.springframework.web.WebApplicationInitializer;

public class CustomInitializer implements WebApplicationInitializer{

    @Override
    public void onStartup(ServletContext container) throws ServletException {
        container.setInitParameter("spring.profiles.active","mainConfig");
    }
}
```

@Configuration
------
**告诉Spring是IOC配置类，和spring中的xml配置文件相同**
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
@Bean
------
**注册bean，和`<bean/>标签`功能相同**
1. bean默认id为方法名（可以通过applicationContext.getBeanDefinitionNames()查看bean的id值）
2. bean默认类型为方法返回类型

@ComponentScan
------
**简介**

配置扫描带有Spring注解的包

**用法**
1. basePackages = {"com.lim.bean.inner"}，指定扫描的包
2. excludeFilters={}，扫面的时候不包含的注解
  1. 通过@ComponentScan.Filter配置不扫描的注解，参数type和classes
  2. type=""，过滤方式，通过FilterType配置
  3. classes={}，类对象
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
@ComponentScan自定义扫描规则——TypeFilter
------
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

Spring Boot中默认配置
======
Banner
------
```java
public ConfigurableApplicationContext run(String... args) {
		...
		try {
			ApplicationArguments applicationArguments = new DefaultApplicationArguments(
					args);
			ConfigurableEnvironment environment = prepareEnvironment(listeners,
					applicationArguments);
			configureIgnoreBeanInfo(environment);
      // 默认加载Banne.model=CONSOLE，可以通过spring.main.banner-mode=off属性更改
			Banner printedBanner = printBanner(environment);
			context = createApplicationContext();
			exceptionReporters = getSpringFactoriesInstances(
					SpringBootExceptionReporter.class,
					new Class[] { ConfigurableApplicationContext.class }, context);
			prepareContext(context, environment, listeners, applicationArguments,
					printedBanner);
    ....
```
Spring Boot默认Package
------
默认打包方式为`java package -> *.java`，所以需要在pom文件中配置以下内容：
``` xml
<build>
    <resources>
        <resource>
            <directory>src/main/resources</directory>
        </resource>
        <resource>
            <directory>src/main/java</directory>
            <includes>
                <include>**/.*</include>
            </includes>
            <filtering>true</filtering>
        </resource>
    </resources>
</build>
```
@ComponentScan注解
------
ComponentScan 注解是 @SpringBootApplication 继承的注解之一，它的作用是搜索需要被IOC容器管理的bean类，就是通常所说的扫描注解的包。(This part of “telling Spring where to search for Spring Beans is by adding the right annotation” is called a ComponentScan.)

`Spring Boot中，@ComponentScan 默认值就是 SpringBootApplication 启动类所在的包。`
当然也可以定义被扫描的包：
```java
Define @ComponentScan(“com.lim.xyyutil)
Define @ComponentScan({“com.lim.xyyutil.controller,”com.lim.other.service})
```

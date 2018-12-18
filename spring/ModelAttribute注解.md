# @ModelAttribute注解

@ModelAttribute注解的主要作用就是将数据添加到数据模型即ModelMap中，在视图展示时使用。@ModelAttribute("name")等价于model.addAttribute("name","")

**@ModelAttribute主要用法有三方面：**
1. 应用在参数上，会将客户端传递过来的参数按名称注入到指定对象中，并且会将这个对象自动加入ModelMap中，便于View层使用；
2. 应用在方法上，会在每一个@RequestMapping标注的方法前执行，如果有返回值，则自动将该返回值加入到ModelMap中；
3. 应用在方法上，并且方法也使用了@RequestMapping注解，这种情况下方法的返回值并不是表示一个视图名称，而是model属性的值。视图名称由RequestToViewNameTranslator根据请求"/url"转换为逻辑视图url。Model属性名称有@ModelAttribute(value="")指定，相当于在request中封装了key=注解的value值，value=方法返回值.

## @ModelAttribute应用在方法上

### @ModelAttribute注释的方法返回void
```java
    /**
     * -----------------返回值为void-------------------
     */
    @ModelAttribute
    public void testA1() {
        System.out.println("testA-@ModelAttribute方法执行");
    }

    @RequestMapping(value = "testA")
    public String testA2() {
        System.out.println("testA-Controller方法执行");
        return "test";
    }

    /**
     * ----------------通过model传递值-----------------
     */
    @ModelAttribute
    public void testB1(Model model) {
        System.out.println("testB-@ModelAttribute方法执行");
        model.addAttribute("attr", "testB");
    }

    @RequestMapping(value = "testB")
    public String testB2() {
        System.out.println("testB-Controller方法执行");
        return "test";
    }

    <h3 th:text="${attr}"></h3>
```
### @ModelAttribute注释的方法返回具体类
```java
    /**
     * -----------------返回对象--------------------
     * @ModelAttribute没有指定name属性，它由返回类型隐含表示，即project。
     * @ModelAttribute("pro")，则testC2(@ModelAttribute("pro")Project project)，<h3 th:text="${pro.name}"></h3>
     */
    @ModelAttribute
    public Project testC1() {
        System.out.println("testC-@ModelAttribute方法执行");
        Project p = new Project();
        p.setName("yunwei");
        return p;
    }

    @RequestMapping(value = "testC")
    public String testC2(Project p) {
        System.out.println("testC-Controller方法执行");
        System.out.println(p.getName());
        p.setName("yunwei-modify");
        return "test";
    }

    <h3 th:text="${project.name}"></h3>
```
## @ModelAttribute应用在参数上
![Image text](/imgs/ModelAttribute.png)

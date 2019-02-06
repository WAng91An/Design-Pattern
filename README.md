# Design-Pattern

## 23种设计模式

- 创建型模式： 
    - 单例模式
    - 工厂模式
    - 抽象工厂模式
    - 建造者模式
    - 原型模式。 
- 结构型模式： 
    - 适配器模式
    - 桥接模式
    - 装饰模式
    - 组合模式
    - 外观模式
    - 享元模式
    - 代理模式。 
- 行为型模式： 
    - 模版方法模式
    - 命令模式
    - 迭代器模式
    - 观察者模式
    - 中介者模式
    - 备忘录模式
    - 解释器模式
    - 状态模式
    - 策略模式
    - 职责链模式
    - 访问者模式
    
## 单例模式

### 介绍

**核心作用:** 保证一个类只有一个实例，并且提供一个访问该实例的全局访问点。

**常见应用场景：**
 
- Windows的任务管理器就是很典型的单例模式 
- windows的回收站也是典型的单例应用。在整个系统运行过程中，回收站一直维护着仅有的一个实例。 
- 项目中，读取配置文件的类，一般也只有一个对象。没有必要每次使用配置文件数据，每次new一个对象去读取。 
- 网站的计数器，一般也是采用单例模式实现，否则难以同步。 
- 应用程序的日志应用，一般都何用单例模式实现，这一般是由于共享的日志文件一直处于打开状态，因为只能有一个实例去操作 ，否则内容不好追加。 
- 数据库连接池的设计一般也是采用单例模式，因为数据库连接是一种数据库资源。 
- 操作系统的文件系统，也是大的单例模式实现的具体例子，一个操作系统只能有一个文件系统。 
- Application 也是单例的典型应用（Servlet编程中会涉及到） 
- 在Spring中，每个Bean默认就是单例的，这样做的优点是Spring容器可以管理 
- 在servlet编程中，每个Servlet也是单例 
- 在spring MVC框架/struts1框架中，控制器对象也是单例

**单例模式的优点：** 
- 由于单例模式只生成一个实例，减少了系统性能开销，当一个对象的产生需要 比较多的资源时，如读取配置、产生其他依赖对象时，则可以通过在应用启动 时直接产生一个单例对象，然后永久驻留内存的方式来解决 
- 单例模式可以在系统设置全局的访问点，优化环共享资源访问，例如可以设计 一个单例类，负责所有数据表的映射处理

**常见的五种单例模式实现方式：** 
- 主要： 
    - 饿汉式（线程安全，调用效率高。 但是，不能延时加载。） 
    - 懒汉式（线程安全，调用效率不高。 但是，可以延时加载。） 
- 其他： 
    - 双重检测锁式（由于JVM底层内部模型原因，偶尔会出问题。不建议使用） 
    - 静态内部类式(线程安全，调用效率高。 但是，可以延时加载) 
    - 枚举单例(线程安全，调用效率高，不能延时加载,可以天然方式反射和反序列化的漏洞)
### 饿汉式

**特点：** 线程安全，调用效率高。 但是，不能延时加载。

```
public class Singleton01 {

    // 创建类也就是类加载的时候是天然的线程安全。
    // 当我们加载类的时候立即创建一个对象，这也对应饿汉式的饿字。不能延时加载
    private static Singleton01 instance = new Singleton01();

    private Singleton01(){

    }

    // 由于类加载的时候是天然的线程安全，不用同步，因此调用效率高
    public static Singleton01 getInstance(){
        return instance;
    }
}
```
饿汉式单例模式代码中，static变量会在类装载时初始化，此时也不会涉及多个线程对象访问该对象的问题。虚拟机保证只会装载一次该类，肯定不会发生并发访问的问题。因此，getInstance方法可以省略synchronized关键字，这样就会调用效率高。
问题：如果只是加载本类，而不是要调用getInstance()，甚至永远没有调用，则会造成资源浪费！


### 懒汉式

**特点：** 单例对象延迟加载，真正用的时候才加载！资源利用率高了。每次调用getInstance()方法都要同步，并发效率较低。
```
public class Singleton02 {

    // 类初始化的时候不创建对象，真正用的时候再创建对象。
    private static Singleton02 instance;

    private Singleton02(){ // 私有化构造器

    }

    // 方法同步，调用效率低。不添加synchronized关键字在高并发的时候可能会创建多个对象
    public static synchronized Singleton02 getInstance(){
        if ( instance == null){
            instance = new Singleton02();
        }
        return instance;
    }
}
```


### 双重检测锁式
懒汉式是对方法进行同步,如果此方法被调用的时候就必须等待.这样效率比较地底.而双重检测锁式将同步内容下放到if内部，提高了执行的效率,不必每次获取对象时都进行同步，只有第一次才同步创建了以后就没必要了。
```
public class Singleton03 {

    private static Singleton03 instance = null;

    public static Singleton03 getInstance()
    {
        if (instance == null) {
            Singleton03 sc;
            synchronized (Singleton03.class)
            {
                sc = instance;
                if (sc == null) {
                    synchronized (Singleton03.class) {
                        if(sc == null) {
                            sc = new Singleton03();
                        }
                    }
                    instance = sc;
                }
            }
        }
        return instance;
    }
    private Singleton03() {

    }
}
```
**优点:**  即提高了效率,也可以延时加载.但是不建议使用,偶尔会出问题.

**问题:** 由于编译器优化原因和JVM底层内部模型原因， 偶尔会出问题。不建议使用。


### 静态内部类式
静态内部类模式是线程安全的,并且可以延时加载,调用效率高.兼备了并发高效调用和延迟加载的优势！
```
public class Singleton04 {

    /* 内部类加载的时候是天然的线程安全 */
    private static class SingletonInnerClass{
        /* instance是 static final 类型，保证了内存中只有这样一个实例存在，而且只能被赋值一次，从而保证了线程安全性.  */
        private static final Singleton04 instance = new Singleton04();
    }

    /* 外部类没有static属性，则不会像饿汉式那样立即加载对象。  */
    public static Singleton04 getInstance () {
        return SingletonInnerClass.instance;
    }

    private Singleton04(){

    }

}
```
- 外部类没有static属性，则不会像饿汉式那样立即加载对象。 
-  只有真正调用getInstance(),才会加载静态内部类。加载类时是线程安全的。 
- instance是static final 类型，保证了内存中只有这样一个实例存在，而且只能被赋值一次，从而保证了线程安全性. 
- 兼备了并发高效调用和延迟加载的优势！

### 枚举单例
枚举本身就是单例模式。由JVM从根本上提供保障！避免通过反射和反序列化的漏洞,但是不能延时加载.
```
public enum Singleton05 {

    /* 定义一个枚举的元素，它就代表了Singleton的一个实例 */
    INSTANCE;

    /* 单例可以有自己的操作 */
    public void singletonOperation(){
        //功能处理
    }
}
```

### 如何选用?
- 单例对象  占用资源少，不需要延时加载
    - 枚举式好于饿汉式 
- 单例对象占用资源大，需要延时加载
    - 静态内部类式好于懒汉式

### 问题
- 反射可以破解上面四种实现方式，不包含枚举式。可以在构造方法中手动 抛出异常控制
- 反序列化可以破解上面几种((不包含枚举式))实现方式,可以通过定义readResolve()防止获得不同对象。反序列化时，如果对象所在类定义了readResolve()，（实际是一种回调）， 定义返回哪个对象。 
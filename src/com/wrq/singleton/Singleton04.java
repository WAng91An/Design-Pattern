package com.wrq.singleton;

/**
 * 静态内部类式: 这个单例模式不错,常用!
 * 调用效率高, 线程安全, 可以延时加载
 * Created by wangqian on 2019/2/6.
 */
public class Singleton04 {
    /* 外部类没有static属性，则不会像饿汉式那样立即加载对象。  */

    /* 内部类加载的时候是天然的线程安全 */
    private static class SingletonInnerClass{
        /* instance是 static final 类型，保证了内存中只有这样一个实例存在，而且只能被赋值一次，从而保证了线程安全性.  */
        private static final Singleton04 instance = new Singleton04();
    }

    private Singleton04(){

    }

    /* 不需要同步 */
    public static Singleton04 getInstance () {
        return SingletonInnerClass.instance;
    }


}

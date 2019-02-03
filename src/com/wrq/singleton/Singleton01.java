package com.wrq.singleton;

/**
 * Created by wangqian on 2019/2/3.
 * 饿汉式单例模式: 线程安全，调用效率高。不能延时加载
 */
public class Singleton01 {

    // 创建类也就是类加载的时候是天然的线程安全。
    // 当我们加载类的时候立即创建一个对象，这也对应饿汉式的饿字。不能延时加载
    private static Singleton01 instance = new Singleton01();

    private Singleton01(){ // 私有化构造器

    }

    // 由于类加载的时候是天然的线程安全，不用同步，因此调用效率高
    public static Singleton01 getInstance(){
        return instance;
    }
}
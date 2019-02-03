package com.wrq.singleton;

/**
 * Created by wangqian on 2019/2/3.
 * 懒汉式单例模式: 单例对象延迟加载，真正用的时候才加载！资源利用率高了。并发效率较低。
 */
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
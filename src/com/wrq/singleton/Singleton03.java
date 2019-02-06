package com.wrq.singleton;

/**
 * 双重检测锁式: 懒加载,也能提供效率.不建议使用,会出问题
 * Created by wangqian on 2019/2/6.
 */
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
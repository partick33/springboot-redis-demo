package com.partick.redisdemo.message.guard;

import com.partick.redisdemo.message.RedisMessageListener;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

/**
 * @author patrick_peng
 * @description GuardObject模式 非官方说法：多线程if
 * @date 2023-02-01 09:42
 **/
public class GuardObject<T> {

    @Resource
    private RedisMessageListener messageListener;

    /**
     * 受保护资源
     */
    T t;

    final Lock lock = new ReentrantLock();

    final Condition condition = lock.newCondition();

    final Integer time = 2;

    static Map<Object, GuardObject> map = new ConcurrentHashMap<>();



    static <K> GuardObject create(K key){
        GuardObject object = new GuardObject<>();
        map.put(key, object);
        return object;
    }

    public static<K, V> void findEvent(K key, V v){
        GuardObject object = map.remove(key);
        if (!ObjectUtils.isEmpty(object)) {
            object.onChange(v);
        }
    }

    //获取保护对象
    T get(Predicate<T> p){
        lock.lock();
        try {
            while (!p.test(t)){
                condition.await(time, TimeUnit.SECONDS);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
            //重置中断标识位置
            Thread.currentThread().interrupt();
        }finally {
            lock.unlock();
        }
        return t;
    }

    //通知模型
    void onChange(T t){
        lock.lock();
        try {
            this.t = t;
            condition.notifyAll();
        }finally {
            lock.unlock();
        }
    }
}

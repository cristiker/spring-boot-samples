package com.cristik.springcache.interceptor;

import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.cache.interceptor.CacheOperationInvoker;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author cristik
 */
public class CacheLockInterceptor extends CacheInterceptor {

    @Nullable
    @Override
    protected Object execute(CacheOperationInvoker invoker, Object target, Method method, Object[] args) {
        Lock lock = new ReentrantLock();
        lock.lock();
        Object object = null;
        try {
            object = super.execute(invoker, target, method, args);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return object;
    }
}
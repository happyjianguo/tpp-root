package com.fxbank.cap.paf.nettty.client;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ClientSyncFuture<T> implements Future<T> {
    private CountDownLatch latch = new CountDownLatch(1);
    private T response;
    
    @Override
    public T get() throws InterruptedException {
        latch.await();
        return this.response;
    }
    
    // 获取响应结果，直到有结果或者超过指定时间就返回。
    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException {
        if (latch.await(timeout, unit)) {
            return this.response;
        }
        return null;
    }
    
    // 用于设置响应结果，并且做countDown操作，通知请求线程
    public void setResponse(T response) {
        this.response = response;
        latch.countDown();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }
    
    @Override
    public boolean isCancelled() {
        return false;
    }
    
    @Override
    public boolean isDone() {
        if (response != null) {
            return true;
        }
        return false;
    }

}


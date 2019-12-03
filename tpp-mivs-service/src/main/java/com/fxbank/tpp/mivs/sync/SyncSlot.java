package com.fxbank.tpp.mivs.sync;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class SyncSlot<T> implements Future<T> {
    private CountDownLatch latch = new CountDownLatch(1);
    private boolean isCancel = false;
    private T response;

    @Override
    public T get() throws InterruptedException {
        latch.await();
        return this.response;
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException {
        if (latch.await(timeout, unit)) {
            return this.response;
        }
        return null;
    }

    public void setResponse(T response) {
        this.response = response;
        latch.countDown();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        this.isCancel = true;
        latch.countDown();
        return true;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancel;
    }

    @Override
    public boolean isDone() {
        if (response != null) {
            return true;
        }
        return false;
    }

}

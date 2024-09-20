package com.future.threads;

import java.util.concurrent.Callable;

import static java.lang.Thread.sleep;

public class InventoryChecker implements Callable<Boolean> {
    private Integer Order;


    public InventoryChecker(Integer Order){
        this.Order= Order;
    }
    @Override
    public Boolean call() throws Exception {
        sleep(200);
        if (this.Order % 2 == 0)
            return Boolean.TRUE;
        else
            return Boolean.FALSE;
    }

}
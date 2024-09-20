package com.future.threads;

import java.util.concurrent.Callable;

import static java.lang.Thread.sleep;

public class PaymentAccepter implements Callable<String> {
    private Boolean inventoryPresent;


    public PaymentAccepter(Boolean inventoryPresent){
        this.inventoryPresent= inventoryPresent;

    }
    @Override
    public String call() throws Exception {
        if (inventoryPresent)
            return "payment accepted";
        else
            return "payment not accepted";

    }

}
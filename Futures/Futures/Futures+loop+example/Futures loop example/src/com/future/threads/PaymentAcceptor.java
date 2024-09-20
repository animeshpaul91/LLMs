package com.future.threads;

import java.util.concurrent.Callable;

import static java.lang.Thread.sleep;

public class PaymentAcceptor implements Callable<String> {
    private final Boolean inventoryPresent;


    public PaymentAcceptor(Boolean inventoryPresent){
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
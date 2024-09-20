package com.future.threads;

import java.util.Random;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class OrderFetcher implements Callable<Integer> {
    public static Random random;
    public OrderFetcher (){
        random = new Random();
    }
    @Override
    public Integer call() throws Exception {
        sleep(200);
        return random.nextInt(6);
    }

}
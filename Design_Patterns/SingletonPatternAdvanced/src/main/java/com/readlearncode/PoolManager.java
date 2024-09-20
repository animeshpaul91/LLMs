package com.readlearncode;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
@DependsOn("Configuration") // Instantiation of PoolManager will only occur after Configuration bean is instantiated
@Startup // Eager Instantiation (by default this is Lazy)
@Singleton
public class PoolManager {

    private Queue<Object> pooledObjects;

    @PostConstruct // this will be called after bean is successfully created by the container
    void constructExpensiveObjects(){
        pooledObjects = new LinkedBlockingQueue<>(1_000);
        for (int i = 0; i <= 1_000; i++) {
            pooledObjects.offer(new Object());
        }
    }


    public void returnObject(Object obj) {
        pooledObjects.offer(obj);
    }

    public Object borrowObject() {
        return pooledObjects.poll();
    }
}

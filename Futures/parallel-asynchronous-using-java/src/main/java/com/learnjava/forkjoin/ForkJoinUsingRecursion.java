package com.learnjava.forkjoin;

import com.learnjava.util.DataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import static com.learnjava.util.CommonUtil.delay;
import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

public class ForkJoinUsingRecursion extends RecursiveTask<List<String>> {
    private List<String> inputList;

    public ForkJoinUsingRecursion(List<String> inputList) {
        this.inputList = inputList;
    }

    public static void main(String[] args) {
        stopWatch.start();
        final List<String> names = DataSet.namesList();
        log("Input Names : " + names);

        ForkJoinUsingRecursion forkJoinUsingRecursion = new ForkJoinUsingRecursion(names);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        List<String> result = forkJoinPool.invoke(forkJoinUsingRecursion); // Task gets added to shared work queue. Will invoke compute
        stopWatch.stop();
        log("Final Result : " + result);
        log("Total Time Taken : " + stopWatch.getTime());
    }


    private static String addNameLengthTransform(String name) {
        delay(500);
        return name.length() + " - " + name;
    }

    @Override
    protected List<String> compute() { // DAC Task
        if (inputList.size() <= 1) {
            List<String> resultList = new ArrayList<>();
            inputList.forEach(name -> resultList.add(addNameLengthTransform(name)));
            return resultList;
        }

        int midPoint = inputList.size() / 2;
        ForkJoinTask<List<String>> leftTask = new ForkJoinUsingRecursion(inputList.subList(0, midPoint)).fork();
        // task gets added to worker thread work queue. Non Blocking task
        inputList = inputList.subList(midPoint, inputList.size());
        List<String> rightResult = compute();
        List<String> leftResult = leftTask.join();
        leftResult.addAll(rightResult);
        return leftResult;
    }
}

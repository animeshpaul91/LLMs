package com.webapp.example.webappdemo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.lang.Thread.sleep;

@Service
public class EmployeeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);
    @Autowired
    private EmployeeRepository employeeRepository;

    @Async
    public CompletableFuture<List<Employee>> getAllEmployees() throws InterruptedException {
        System.out.println("SupplyIds is currently running in " + Thread.currentThread().getName());
        LOGGER.info("Request to get a list of employees" + Thread.currentThread().getName());
        sleep(4000);
        final List<Employee> employees = employeeRepository.findAll();
        System.out.println(employees);
        return CompletableFuture.completedFuture(employees);
    }
}

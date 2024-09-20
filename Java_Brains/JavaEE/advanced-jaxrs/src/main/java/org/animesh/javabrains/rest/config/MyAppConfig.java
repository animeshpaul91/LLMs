package org.animesh.javabrains.rest.config;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/webapi") // this is equivalent to /webapi/*
public class MyAppConfig extends Application {
    // extends Application tells Jersey that this is a JAX RS Application
    // @ApplicationPath tells that any REST API endpoint will be prefixed with /webapi/
}

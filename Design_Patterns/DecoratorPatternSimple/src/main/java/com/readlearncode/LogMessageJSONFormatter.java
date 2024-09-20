package com.readlearncode;

import com.readlearncode.thirdpartylogger.LogMessage;

import javax.annotation.Priority;
import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
@Decorator
@Priority(20) // JSON Formatter will be applied after the timestamp is prefixed. Priority adds this effect
public abstract class LogMessageJSONFormatter implements LogMessage {

    @Any
    @Inject
    @Delegate
    @ComplexMessage // only instances of LogMessage annotated with @ComplexMessage are decorated and all other instances remain in their original form
    private LogMessage logMessage;

    @Override
    public void printMessage() {
        String message = logMessage.getMessage();
        String jsonMessage = JsonbBuilder.create().toJson(message);
        logMessage.setMessage(jsonMessage);
    }
}
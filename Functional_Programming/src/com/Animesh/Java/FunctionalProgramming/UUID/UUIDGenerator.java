package com.Animesh.Java.FunctionalProgramming.UUID;

import java.util.UUID;

public class UUIDGenerator {
    private static final String EVENT_SID_PREFIX = "EV";
    public static void main(String[] args) {
        System.out.println(getEventID());
    }

    private static String getEventID() {
        final String uuid = UUID.randomUUID().toString().replace("-", "");
        final var result = EVENT_SID_PREFIX + uuid;
        System.out.println(result.length());
        return result;
    }
}

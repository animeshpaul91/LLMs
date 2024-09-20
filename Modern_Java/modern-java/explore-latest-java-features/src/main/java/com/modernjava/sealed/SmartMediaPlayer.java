package com.modernjava.sealed;

public sealed interface SmartMediaPlayer permits Car {
    void connectPhone();
}

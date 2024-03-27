package service.impl;

import logare.LogLevel;
import service.Logger;

import java.util.HashMap;
import java.util.Map;

public class MyLogger implements Logger {
    private Map<LogLevel, String> log = new HashMap<>();
    private static MyLogger instance;

    private MyLogger() {}

    public static MyLogger getInstance() {
        if (instance == null) {
            instance = new MyLogger();
        }
        return instance;
    }

    @Override
    public void log(LogLevel loglevel, String message) {
        this.log.put(loglevel, message);
        System.out.println("[" + loglevel + "]: " + message + ';');
    }

    @Override
    public void logInfo(String message) {
        this.log.put(LogLevel.INFO, message);
        System.out.println("[INFO]: " + message + ';');
    }
}

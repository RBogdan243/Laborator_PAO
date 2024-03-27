package service;

import logare.LogLevel;

public interface Logger {
    void log(LogLevel loglevel, String message);
    void logInfo(String message);
}

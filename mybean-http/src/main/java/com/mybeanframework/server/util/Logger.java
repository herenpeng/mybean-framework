package com.mybeanframework.server.util;

import java.io.PrintStream;

/**
 * @author herenpeng
 * @since 2021-06-07 23:04
 */
public class Logger {

    private static final Logger LOGGER = new Logger();

    private Logger() {
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public static final PrintStream OUT = System.out;

    enum LogLevel {
        INFO,
        ERROR;
    }

    public void info(Object o) {
        OUT.println("[" + LogLevel.INFO + "]" + o);
    }

    public void error(Object o) {
        OUT.println("[" + LogLevel.ERROR + "]" + o);
    }

    public void println(Object o) {
        OUT.println(o);
    }

    public void print(Object o) {
        OUT.print(o);
    }


}

package org.karolgurecki.autotask.utils;

/**
 * Created by goreckik on 09.10.13.
 */
public class ExceptionUtils {
    private ExceptionUtils() {

    }

    /**
     * Converts stack trace to string from from given exception
     *
     * @param e the exception
     *
     * @return created string
     */
    public static String stackTraceToString(Exception e) {
        StringBuilder builder = new StringBuilder();

        builder.append(e.getMessage());

        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            builder.append("\n" + stackTraceElement);
        }

        return builder.toString();
    }
}

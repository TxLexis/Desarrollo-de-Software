package edu.ucr.exceptions.c24249;

import java.io.IOException;

public class Ej6_WrappingCause {
    public static void main(String[] args) {
        try {
            serviceLoad();
        } catch (ServiceException e) {
            // TODO: generic UI message
            System.out.println("Failed to load service configuration");

            // TODO: verify e.getCause() != null (traceability)
            if (e.getCause() != null) {
                System.out.println("Cause: " + e.getCause().getMessage());
            }
        }
    }

    static void serviceLoad() {
        // TODO: try/catch IOException, wrap in ServiceException preserving cause
        try {
            repoLoad();
        } catch (IOException e) {
            throw new ServiceException("Failed to load service configuration: " , e);
        }
    }

    static void repoLoad() throws IOException {
        // TODO: simulate failure: throw new IOException("Simulated IO failure");
        throw new IOException("Simulated IO failure");
    }

    static class ServiceException extends RuntimeException {
        // TODO: constructor (String message, Throwable cause)
        ServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
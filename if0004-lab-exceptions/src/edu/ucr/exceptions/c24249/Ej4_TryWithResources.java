package edu.ucr.exceptions.c24249;

public class Ej4_TryWithResources {

    public static void main(String[] args) {
        try {
            useConnection();
            System.out.println("Operation finished successfully");
        } catch (IllegalStateException e) {
            // TODO: user-facing message
            System.out.println("Connection error: " + e.getMessage());
        }
    }

    static void useConnection() {
        // TODO:
        // Use try-with-resources
        // Create FakeConnection
        // Call connect()
        // Call sendData()
        try (FakeConnection connection = new FakeConnection()) {
            connection.connect();
            connection.sendData();
        }
    }


    static class FakeConnection implements AutoCloseable {

        void connect() {
            // TODO:
            // Optionally simulate failure:
            //throw new IllegalStateException("Cannot connect to service");
            System.out.println("Connection established");
        }

        void sendData() {
            System.out.println("Sending data...");
        }

        @Override
        public void close() {
            System.out.println("Connection closed");
        }
    }
}
package edu.ucr.exceptions.c24249;

public class Ej3_ThrowsPropagation {

    public static void main(String[] args) {
        try {
            String token = fetchSecurityToken();
            System.out.println("Token obtained: " + token);
        } catch (TokenServiceException e) {
            // TODO: show user-friendly message (no stack trace)
            System.out.println("Unable to contact security service. Please try later.");
        }
    }

    static String fetchSecurityToken() throws TokenServiceException {
        // TODO:
        // Simulate service failure
        throw new TokenServiceException("Security service unavailable");
        //return ""; // placeholder
    }

    static class TokenServiceException extends Exception {
        TokenServiceException(String message) {
            super(message);
        }
    }
}

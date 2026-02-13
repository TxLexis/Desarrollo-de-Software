package edu.ucr.exceptions.c24249;

public class Ej8_StackTraceRootCause {
    public static void main(String[] args) {
        try{
            registerUser("12a");
        } catch (IllegalArgumentException e) {
            System.out.println("Registration failed: " + e.getMessage());
            System.out.println("Please provide a valid numeric age.");
        }

    }

    static void registerUser(String ageRaw) {
        int age = parseAge(ageRaw);
        System.out.println("Registered age: " + age);

    }

    static int parseAge(String raw) {
        // TODO: handle invalid input without hiding errors.
        // Requirement: do NOT use generic catch to swallow the exception.
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("Age cannot be null or blank");
        }
        try{
            int age = Integer.parseInt(raw);
            if (age < 0 || age > 150) {
                throw new IllegalArgumentException("Age must be between 0 and 150. Provided: " + age);
            }
            return age;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid age format: " + raw + " is not a valid number", e);
        }

    }
}
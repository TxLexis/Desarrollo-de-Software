package edu.ucr.exceptions.c24249;

public class Ej7_AntiPatternFix {
    public static void main(String[] args) {
        try {
            int value = loadImportantNumber("abc");
            System.out.println("Loaded: " + value);
        } catch (IllegalArgumentException e) {
            System.out.println("invalid number format: " + e.getMessage());
        }

    }

    static int loadImportantNumber(String raw) {
        try {
            return Integer.parseInt(raw);

            // TODO: fix antipattern:
            // - NO generic catch
        } catch (NumberFormatException e) {
            // - NO silent failure
            // - Choose: propagate, wrap, or specific handling with explicit outcome
            //return 0; // antipattern placeholder
            throw new IllegalArgumentException("Invalid input for important number: " + raw, e);
        }
    }
}
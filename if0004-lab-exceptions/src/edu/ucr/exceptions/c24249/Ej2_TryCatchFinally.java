package edu.ucr.exceptions.c24249;

import java.sql.SQLOutput;

public class Ej2_TryCatchFinally {
    public static void main(String[] args) {
        try {
            int result = riskyCompute(10, 0, false);
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            // TODO: clear message
            System.out.println("Cannot divide by zero.");

        } catch (IllegalStateException e) {
            // TODO: clear message
            System.out.println("Error: " + e.getMessage());

        } finally {
            // TODO: print "End of operation"
            System.out.println("End of operation");

        }
    }

    static int riskyCompute(int a, int b, boolean badState) {
        // TODO:
        // - if badState -> throw IllegalStateException("...")
        if(badState) {
            throw new IllegalStateException("System is in an invalid state");
        }

        // - return a / b (may throw ArithmeticException)
        return a / b; // placeholder
    }
}

package edu.ucr.exceptions.c24249;

public class Ej1_CheckedVsUnchecked {
    public static void main(String[] args) {
        try{
            int n = parseNonNegativeInt(args);
            System.out.println("Valid number: " + n);

        } catch (NumberFormatException e) {
            System.out.println("Argument is not a valid integer");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    static int parseNonNegativeInt(String[] args) {
        // - if args is empty -> IllegalArgumentException("...")
        if(args == null || args.length == 0) {
            throw new IllegalArgumentException("Missing numeric argument");
        }
        // - parse args[0] -> Integer.parseInt
        int n = Integer.parseInt(args[0]);

        // - if n < 0 -> IllegalArgumentException("...")
        if(n < 0) {
            throw new IllegalArgumentException("Number must be non-negative");
        }

        return n; // placeholder
    }
}
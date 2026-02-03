package edu.ucr.stringlist.c24249;


/**
 * A simple dynamic list of Strings.
 * Implementations MUST NOT use arrays or Java Collections.
 */
public interface StringList {

    /**
     * Adds a value to the list.
     * If the value already exists, the implementation must NOT add it again
     * (i.e., ignore duplicates).
     *
     * @param value the String to add (null is not allowed)
     * @throws IllegalArgumentException if value is null
     */
    void add(String value);

    /**
     * Removes the first occurrence of the given value from the list.
     * If the value is not found, the list remains unchanged.
     *
     * @param value the String to remove (null is not allowed)
     * @throws IllegalArgumentException if value is null
     */
    void remove(String value);

    /**
     * @return the number of elements currently stored in the list.
     */
    Integer size();

    /**
     * Returns all values separated by commas, in insertion order.
     * Example: "A,B,C"
     *
     * @return a comma-separated String with all elements; empty String if the list is empty.
     */
    String toString();

    /**
     * Checks if the value exists in the list.
     *
     * @param value the value to look for (null is not allowed)
     * @return true if the value is present; false otherwise.
     * @throws IllegalArgumentException if value is null
     */
    Boolean exists(String value);
}
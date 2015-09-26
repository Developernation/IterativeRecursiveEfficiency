/**
 * Created by Thomas Hodges on 9/23/2015.
 * Project: HodgesCMIS242Project3
 * Filename: Sequence.java
 *
 * Course: CMIS 242
 * Professor: Ioan Salomie
 * Assignment: Project 3
 *
 * Platform: Windows 10, IntelliJ IDEA 14.1.4
 * Compiler: jdk1.8.0_45
 */

// Final class so that it may not be extended
public final class Sequence {

    private static int efficiency = 0;

    /**
     * computIterative receives the user input as "n" and
     * checks for two base cases before iterating. Each
     * base case must add to efficiency in order for the
     * counter to be accurate, then the fori loop will begin
     * with i=2, increasing program efficiency by looping less.
     */
    public static int computeIterative(int n) {
        efficiency = 0;
        int returnValue = 0;
        if (n == 0) {
            efficiency++;
            return 0;
        } else if (n == 1) {
            efficiency++;
            return 1;
        } else {
            int last = 1;
            int nextLast = 0;
            for (int i = 2; i <= n; i++) {
                efficiency++;
                returnValue = 2 * last + nextLast;
                nextLast = last;
                last = returnValue;
            }
        }
        return returnValue;
    }

    /**
     * computeRecursive receives user input through "n" and sets
     * the efficiency counter to 0. It then calls the helper method
     * to perform the recursion.
     */
    public static int computeRecursive(int n) {
        efficiency = 0;
        return computeRecursiveHelper(n);
    }

    /**
     * computeRecursiveHelper has two base cases similar to computeIterative,
     * each increasing the efficiency counter by one. This method must call
     * itself twice per cycle in order to perform the operation.
     */
    private static int computeRecursiveHelper(int n) {
        if (n == 0) {
            efficiency++;
            return 0;
        } else if (n == 1) {
            efficiency++;
            return 1;
        } else {
            efficiency++;
            return 2 * computeRecursiveHelper(n - 1) + computeRecursiveHelper(n - 2);
        }
    }

    public static int getEfficiency() {
        return efficiency;
    }

    // Private constructor so that no object can be created of the class
    private Sequence() {}

}

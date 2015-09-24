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

    public static int computeRecursive(int n) {
        efficiency = 0;
        return computeRecursiveHelper(n);
    }

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

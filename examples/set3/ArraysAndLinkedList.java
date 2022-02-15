import java.util.Arrays;

public class ArraysAndLinkedList {
    /**
     * takes in a 2-D array x and returns a 1-D array that
     * contains all of the arrays in x concatenated together.
     *
     * @param x the input 2-D array.
     * @return a 1-D array
     */
    public static int[] flatten(int[][] x) {
        int totalLength = 0;

        for (int[] arr : x) {
            totalLength += arr.length;
        }

        int[] a = new int[totalLength];
        int aIndex = 0;

        for (int[] arr : x) {
            for (int item : arr) {
                a[aIndex] = item;
                aIndex += 1;
            }
        }

        return a;
    }

    public static class IntList {
        public int first;
        public IntList rest;

        public IntList(int f, IntList r) {
            this.first = f;
            this.rest = r;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof IntList)) {
                return false;
            }

            IntList self = this;
            IntList other = (IntList) o;
            while (self != null && other != null) {
                if (self.first != other.first) {
                    return false;
                }
                self = self.rest;
                other = other.rest;
            }

            return self == null && other == null;
        }

        /**
         * Creates an int-list with items.
         *
         * @param args The items.
         * @return The int-list.
         */
        public static IntList list(int... args) {
            if (args.length == 0) {
                return null;
            }

            IntList res = null;
            for (int i = args.length - 1; i >= 0; i--) {
                res = new IntList(args[i], res);
            }

            return res;
        }

        /**
         * The result of calling skippify on A and B are as below:
         * IntList A = IntList.list(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
         * IntList B = IntList.list(9, 8, 7, 6, 5, 4, 3, 2, 1);
         * - After calling A.skippify(), A: (1, 3, 6, 10)
         * - After calling B.skippify(), B: (9, 7, 4)
         */
        public void skippify() {
            IntList p = this;
            int n = 1;

            while (p != null) {
                IntList next = p.rest;
                for (int i = 0; i < n; i++) {
                    if (next == null) {
                        return;
                    }
                    next = next.rest;
                }

                p.rest = next;
                p = next;
                n += 1;
            }
        }

        /**
         * Given a sorted linked list of items - remove duplicates.
         * For example given  1 -> 2 -> 2 -> 2 -> 3,
         * Mutate it to become 1 -> 2 -> 3 (destructively)
         */
        public static void removeDuplicates(IntList p) {
            if (p == null) {
                return;
            }

            IntList current = p.rest;
            IntList previous = p;

            while (current != null) {
                // Invariant: previous is the first one with last value. previous.rest = current. The nodes before current are scanned.
                if (current.first == previous.first) {
                    previous.rest = current.rest;
                } else {
                    previous = current;
                }
                current = current.rest;
            }
        }

        public static void removeDuplicatesV2(IntList p) {
            if (p == null) {
                return;
            }

            IntList current = p;
            IntList previous = p;

            while (current != null) {
                // Invariant: previous is the first one with last value. The nodes before current and current are scanned. previous.rest = origin.
                if (current.rest == null || current.rest.first != current.first) {
                    previous.rest = current.rest;
                    previous = previous.rest;
                }
                current = current.rest;
            }
        }

        public static void removeDuplicatesV3(IntList p) {
            if (p == null) {
                return;
            }

            IntList current = p;
            IntList previous = p;

            while (current != null) {
                current = current.rest;
                // Invariant: previous is the first one with last value. previous.rest = origin.
                if (current == null || current.first != previous.first) {
                    previous.rest = current;
                    previous = current;
                }
            }
        }

        public static void removeDuplicatesV4(IntList p) {
            if (p == null) {
                return;
            }
            
            IntList previous = p;

            while (previous.rest != null) {
                // Invariant: previous is the first one with last value. Nodes before previous.rest are scanned.
                if (previous.first == previous.rest.first) {
                    previous.rest = previous.rest.rest;
                } else {
                    previous = previous.rest;
                }
            }
        }

        public static void display(IntList p) {
            while (p != null) {
                System.out.print(p.first + ".");
                p = p.rest;
            }
            System.out.println(".");
        }
    }

    public static void main(String[] args) {
        System.out.println("Test flatten:");
        int[] res = flatten(new int[][]{{1, 2, 3}, {4, 5}, {}, {7, 8}});
        System.out.println(Arrays.toString(res));
        res = flatten(new int[][]{});
        System.out.println(Arrays.toString(res));
        System.out.println();

        System.out.println("Test equals:");
        IntList L1 = IntList.list(1, 2, 3);
        IntList L2 = IntList.list(1, 2, 3);
        System.out.println(L1.equals(L2));
        L2 = IntList.list();
        System.out.println(L1.equals(L2));
        L2 = IntList.list(1, 2, 3, 4);
        System.out.println(L1.equals(L2));
        L2 = IntList.list(1, 3, 2);
        System.out.println(L1.equals(L2));
        System.out.println();

        System.out.println("Test skippify:");
        IntList LS = IntList.list(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        LS.skippify();
        IntList.display(LS);
        LS = IntList.list(9, 8, 7, 6, 5, 4, 3, 2, 1);
        LS.skippify();
        IntList.display(LS);
        System.out.println();

        System.out.println("Test removeDuplicates:");
        IntList LR = IntList.list();
        IntList.removeDuplicates(LR);
        IntList.display(LR);
        LR = IntList.list(1);
        IntList.removeDuplicates(LR);
        IntList.display(LR);
        LR = IntList.list(1, 1, 1, 1, 1);
        IntList.removeDuplicates(LR);
        IntList.display(LR);
        LR = IntList.list(1, 2, 2, 2, 2, 3);
        IntList.removeDuplicates(LR);
        IntList.display(LR);
        LR = IntList.list(1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3);
        IntList.removeDuplicates(LR);
        IntList.display(LR);
        System.out.println();

        System.out.println("Test removeDuplicatesV2:");
        IntList LRV2 = IntList.list();
        IntList.removeDuplicatesV2(LRV2);
        IntList.display(LRV2);
        LRV2 = IntList.list(1);
        IntList.removeDuplicatesV2(LRV2);
        IntList.display(LRV2);
        LRV2 = IntList.list(1, 1, 1, 1, 1);
        IntList.removeDuplicatesV2(LRV2);
        IntList.display(LRV2);
        LRV2 = IntList.list(1, 2, 2, 2, 2, 3);
        IntList.removeDuplicatesV2(LRV2);
        IntList.display(LRV2);
        LRV2 = IntList.list(1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3);
        IntList.removeDuplicatesV2(LRV2);
        IntList.display(LRV2);
        System.out.println();

        System.out.println("Test removeDuplicatesV3:");
        IntList LRV3 = IntList.list();
        IntList.removeDuplicatesV3(LRV3);
        IntList.display(LRV3);
        LRV3 = IntList.list(1);
        IntList.removeDuplicatesV3(LRV3);
        IntList.display(LRV3);
        LRV3 = IntList.list(1, 1, 1, 1, 1);
        IntList.removeDuplicatesV3(LRV3);
        IntList.display(LRV3);
        LRV3 = IntList.list(1, 2, 2, 2, 2, 3);
        IntList.removeDuplicatesV3(LRV3);
        IntList.display(LRV3);
        LRV3 = IntList.list(1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3);
        IntList.removeDuplicatesV3(LRV3);
        IntList.display(LRV3);
        System.out.println();

        System.out.println("Test removeDuplicatesV4:");
        IntList LRV4 = IntList.list();
        IntList.removeDuplicatesV4(LRV4);
        IntList.display(LRV4);
        LRV4 = IntList.list(1);
        IntList.removeDuplicatesV4(LRV4);
        IntList.display(LRV4);
        LRV4 = IntList.list(1, 1, 1, 1, 1);
        IntList.removeDuplicatesV4(LRV4);
        IntList.display(LRV4);
        LRV4 = IntList.list(1, 2, 2, 2, 2, 3);
        IntList.removeDuplicatesV4(LRV4);
        IntList.display(LRV4);
        LRV4 = IntList.list(1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3);
        IntList.removeDuplicatesV4(LRV4);
        IntList.display(LRV4);
        System.out.println();
    }
}

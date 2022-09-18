package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int[] buckets = new int[M];
        for (Oomage oomage : oomages) {
            int bucketNum = (oomage.hashCode() & 0x7FFFFFFF) % M;
            buckets[bucketNum] += 1;
        }

        int lowerThreshold = oomages.size() / 50;
        int upperThreshold = (int) (oomages.size() / 2.5);
        for (int bucket : buckets) {
            if (bucket < lowerThreshold || bucket > upperThreshold) {
                return false;
            }
        }
        return true;
    }
}

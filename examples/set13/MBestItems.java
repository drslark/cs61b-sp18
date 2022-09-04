import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MBestItems {

    public static List<Integer> mBestFromNRandom(int N, int M) {
        MinPQ<Integer> minPQ = new Heap<>();
        Random random = new Random();

        for (int i = 0; i < N; i++) {
            int item = random.nextInt(N);
            minPQ.add(item);
            if (minPQ.size() > M) {
                minPQ.removeSmallest();
            }
        }

        List<Integer> list =  new ArrayList<>();
        while (minPQ.size() > 0) {
            list.add(minPQ.removeSmallest());
        }
        return list;
    }

    public static void main(String[] args) {
        for (int number : mBestFromNRandom(1000, 20)) {
            System.out.println(number);
        }
    }

}

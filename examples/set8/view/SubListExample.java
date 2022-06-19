package view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubListExample {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>() {
            {
                add(1);
                add(2);
                add(3);
                add(4);
            }
        };

        List<Integer> subList = list.subList(1, 3);

        System.out.println(list);
        subList.set(1, 5);
        System.out.println(list);
        Collections.reverse(subList);
        System.out.println(list);
    }

}

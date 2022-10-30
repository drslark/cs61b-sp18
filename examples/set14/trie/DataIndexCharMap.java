package trie;

import java.util.ArrayList;
import java.util.List;

/**
 * A map that maps each character to an item.
 */
class DataIndexCharMap<E> {

    private final E[] items;
    private final int r;

    DataIndexCharMap(int R) {
        r = R;
        items = (E[]) new Object[r];
    }

    E get(char c) {
        checkChar(c);
        return items[c];
    }

    void put(char c, E e) {
        checkChar(c);
        items[c] = e;
    }

    Iterable<Character> keys() {
        List<Character> keys = new ArrayList<>();
        for (char i = 0; i < r; i++) {
            if (items[i] != null) {
                keys.add(i);
            }
        }
        return keys;
    }

    void checkChar(char c) {
        if (c >= items.length) {
            throw new IllegalArgumentException("Invalid character for DataIndexCharMap!");
        }
    }

}

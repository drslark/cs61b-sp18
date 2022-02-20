public class OffByN implements CharacterComparator {

    private final int N;

    public OffByN(int n) {
        N = n;
    }

    @Override
    public boolean equalChars(char A, char B) {
        return Math.abs(A - B) == N;
    }

}

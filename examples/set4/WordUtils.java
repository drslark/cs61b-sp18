public class WordUtils {

    public static String longestWord(List61B<String> wordList) {
        String longestWord = "";
        for (int i = 0; i < wordList.size(); i++) {
            String word = wordList.get(i);
            if (word.length() > longestWord.length()) {
                longestWord = word;
            }
        }

        return longestWord;
    }

    public static void main(String[] args) {
        for (List61B<String> someList : (List61B<String>[]) new List61B[]{new SLList<>(), new AList<>()}) {
            someList.addLast("elk");
            someList.addLast("are");
            someList.addLast("watching");
            System.out.println(longestWord(someList));
        }
    }
}

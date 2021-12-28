public class HelloNumbers {
    public static void main(String[] args) {
        int i = 0;
        int sum = 0;
        while (i <= 9) {
            sum += i;
            System.out.print(sum + " ");
            i++;
        }
        System.out.println();
    }
}

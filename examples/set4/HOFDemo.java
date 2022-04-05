/** Demonstrates higher order function in java. */
public class HOFDemo {

    public static int doTwice(IntUnaryFunction f, int x) {
        return f.apply(f.apply(x));
    }

    public static void main(String[] args) {
        System.out.println(doTwice(new TenX(), 2));
    }

}

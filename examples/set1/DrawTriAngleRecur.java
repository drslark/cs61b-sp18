public class DrawTriAngleRecur {
    /**
     * Draw a triangle with a given size.
     *
     * @param n The size of the triangle.
     */
    public static void drawTriangle(int n) {
        if (n <= 0) {
            return;
        }
        drawTriangle(n - 1);
        drawStars(n);
        System.out.println();
    }

    /**
     * Draw stars with a given number.
     *
     * @param n The number of the stars.
     */
    public static void drawStars(int n) {
        if (n <= 0) {
            return;
        }
        System.out.print("*");
        drawStars(n - 1);
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            throw new RuntimeException("Shape should be input!");
        }
        drawTriangle(Integer.parseInt(args[0]));
    }

}

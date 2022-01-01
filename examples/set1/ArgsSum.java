/**
 * Test sum of command line parameters.
 */
public class ArgsSum {
    /**
     * Get the sum of the command line parameters.
     *
     * @param args The parameters (which can be supposed to be numbers) to be summed.
     */
    public static void main(String[] args) {
        int size = args.length;
        int i = 0;
        Double sum = 0.0;
        while (i < size) {
            sum += Double.parseDouble(args[i]);
            i++;
        }
        System.out.println(sum);
    }

}

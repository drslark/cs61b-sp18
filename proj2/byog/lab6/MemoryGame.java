package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        setFont("Monaco", Font.BOLD, 30);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.enableDoubleBuffering();

        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            char letter = (char) (rand.nextInt(26) + 97);
            stringBuilder.append(letter);
        }
        return stringBuilder.toString();
    }

    private void clearKeyTypeCache() {
        while (StdDraw.hasNextKeyTyped()) {
            StdDraw.nextKeyTyped();
        }
    }

    private void setFont(String name, int style, int size) {
        Font font = new Font(name, style, size);
        StdDraw.setFont(font);
    }

    private void displayHeader() {
        setFont("Monaco", Font.BOLD, 20);
        StdDraw.textLeft(1, this.height - 1, "Round: " + round);
        StdDraw.text(this.width / 2, this.height - 1, playerTurn ? "Type!" : "Watch!");
        StdDraw.textRight(
            this.width - 1, this.height - 1, ENCOURAGEMENT[round % ENCOURAGEMENT.length]
        );
        StdDraw.line(0, this.height - 2, this.width, this.height - 2);
    }

    public void drawFrame(String s) {
        StdDraw.clear(Color.BLACK);

        if (!gameOver) {
            displayHeader();
        }

        setFont("Monaco", Font.BOLD, 30);
        StdDraw.text(this.width / 2, this.height / 2, s);

        StdDraw.show();
    }

    public void flashSequence(String letters) {
        for (int i = 0; i < letters.length(); i++) {
            drawFrame(letters.substring(i, i + 1));
            StdDraw.pause(1000);
            drawFrame("");
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        StringBuilder stringBuilder = new StringBuilder();

        drawFrame("");
        clearKeyTypeCache();

        while (stringBuilder.length() < n) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }

            char letter = StdDraw.nextKeyTyped();
            stringBuilder.append(letter);

            drawFrame(stringBuilder.toString());
        }

        return stringBuilder.toString();
    }

    public void startGame() {
        round = 1;
        gameOver = false;

        while (!gameOver) {
            playerTurn = false;
            drawFrame("Round: " + round);
            StdDraw.pause(1000);

            String expectedLetters = generateRandomString(round);
            flashSequence(expectedLetters);

            playerTurn = true;
            String typedLetters = solicitNCharsInput(round);

            if (!expectedLetters.equals(typedLetters)) {
                gameOver = true;
                drawFrame("Game Over! You made it to round: " + round);
            } else {
                drawFrame("Correct, well done!");
                StdDraw.pause(1000);

                round += 1;
            }

        }

    }

}

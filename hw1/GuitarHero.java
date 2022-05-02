import synthesizer.GuitarString;

import java.util.HashMap;
import java.util.Map;

/**
 * A full version client that uses the synthesizer package
 * to replicate a plucked guitar string sound.
 */
public class GuitarHero {

    private static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    private static final double BASE_CONCERT = 440.0;

    public static void main(String[] args) {
        /* create 37 guitar strings, for concert from "q" to " " */
        Map<Character, GuitarString> concerts = new HashMap<>();
        for (int i = 0; i < KEYBOARD.length(); i++) {
            double frequency = BASE_CONCERT * Math.pow(2.0, (i - 24.0) / 12.0);
            concerts.put(KEYBOARD.charAt(i), new GuitarString(frequency));
        }

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (concerts.containsKey(key)) {
                    concerts.get(key).pluck();
                }
            }

            /* compute the superposition of samples */
            double sample = 0.0;
            for (GuitarString guitarString : concerts.values()) {
                sample += guitarString.sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (GuitarString guitarString : concerts.values()) {
                guitarString.tic();
            }
        }
    }

}

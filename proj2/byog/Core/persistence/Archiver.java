package byog.Core.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import byog.Core.context.GameInfo;
import byog.Core.coordinate.Location;

/**
 * Controls the save and load of the game process.
 */
public class Archiver {

    /**
     * Saves the game to a file.
     *
     * @param gameInfo Game info stores all the game information.
     * @param archiveFile The file to save the game process.
     */
    public static <E extends Location<E>> void save(GameInfo<E> gameInfo, String archiveFile) {
        try {
            File file = new File(archiveFile);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(gameInfo);
            objectOutputStream.close();
            return;
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        throw new RuntimeException("Save game failed!");
    }

    /**
     * Loads the game from a file.
     *
     * @param archiveFile The file where the game process loads.
     * @return Game info stores all the game information.
     */
    public static <E extends Location<E>> GameInfo<E> load(String archiveFile) {
        try {
            File file = new File(archiveFile);
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return  (GameInfo<E>) objectInputStream.readObject();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        throw new RuntimeException("Load game failed!");
    }

}

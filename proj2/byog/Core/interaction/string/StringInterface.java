package byog.Core.interaction.string;

import byog.Core.context.Context;
import byog.Core.context.GameInfo;
import byog.Core.coordinate.Location;
import byog.Core.interaction.User;
import byog.Core.interaction.constant.Command;
import byog.Core.interaction.constant.State;
import byog.Core.persistence.Archiver;
import byog.Core.terrain.Players;
import byog.Core.terrain.Worlds;
import byog.Core.terrain.view.Player;
import byog.Core.terrain.view.World;
import byog.Core.tiles.Filler;
import byog.TileEngine.TETile;

/**
 * User plays the game via string.
 */
public class StringInterface<E extends Location<E>> {

    private TETile[][] worldFrame;
    private World<E> world;
    private Player<E> player;

    private StringBuilder seedBuilder;
    private boolean gameOver;

    /**
     * Constructor of the string interface.
     */
    public StringInterface() {
        Context.gameInfo().setUser(new User());
        worldFrame = new TETile[Context.width()][Context.height()];
        Filler.fillBackGround(worldFrame);

        seedBuilder = new StringBuilder();
        gameOver = false;
    }

    /**
     * Processes the input string as a sequence of user commands.
     *
     * @param input The input string.
     * @return The frame to represents the world.
     */
    public TETile[][] process(String input) {
        checkGameOver();

        if (input == null || input.length() == 0) {
            throw new RuntimeException("The input should not be empty!");
        }

        for (char key : input.toCharArray()) {
            step(key);
            if (gameOver) {
                break;
            }
        }

        return worldFrame;
    }

    private TETile[][] step(char key) {
        checkGameOver();
        State state = Context.user().getState();
        Command command = Context.user().parseKeyToCommand(key);
        switch (state) {
            case INITIAL:
                stepInitialState(command);
                break;
            case WAIT_FOR_SEED:
                stepSeedState(command);
                break;
            case WAIT_FOR_COMMAND:
                stepCommandState(command);
                break;
            case PLAY:
                stepPlayState(command);
                break;
            default:
        }

        return worldFrame;
    }

    private void stepInitialState(Command command) {
        if (command == Command.LOAD_GAME) {
            GameInfo<E> gameInfo = GameInfo.loadGameInfo();
            Context.setGameInfo(gameInfo);

            world = Context.world();
            Players.setPassableLocations(world.getContent());
            player = Context.player();
            Players.initializeMovements();

            Filler.fillWorld(worldFrame, world);
            Filler.fillPlayer(worldFrame, player);
        } else if (command == Command.QUIT_GAME) {
            gameOver = true;
        }
    }

    private void stepSeedState(Command command) {
        if (command == Command.CONFIRM_SEED) {
            Context.user().setRandom(Long.parseLong(seedBuilder.toString()));

            world = Worlds.generateRandomWorld();
            Players.setPassableLocations(world.getContent());
            player = Players.generateRandomPlayer();
            Players.initializeMovements();

            Filler.fillWorld(worldFrame, world);
            Filler.fillPlayer(worldFrame, player);
        } else if (command != Command.NULL) {
            seedBuilder.append(command.getValue());
        }
    }

    private void stepCommandState(Command command) {
        if (command == Command.QUIT_GAME) {
            Context.gameInfo().setWorld((World) world);
            Context.gameInfo().setPlayer((Player) player);
            Archiver.save((GameInfo<E>) Context.gameInfo(), Context.archiveFile());
            gameOver = true;
        } else {
            stepPlayState(command);
        }
    }

    private void stepPlayState(Command command) {
        if (command != Command.NULL) {
            Filler.removePlayer(worldFrame, player);
            player = Players.move(player, Players.getMovement(command));
            Filler.fillPlayer(worldFrame, player);
        }
    }

    private void checkGameOver() {
        if (gameOver) {
            throw new RuntimeException("Game over!");
        }
    }

}

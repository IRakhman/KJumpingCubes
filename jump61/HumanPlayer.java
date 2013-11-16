package jump61;

/** A Player that gets its moves from manual input.
 *  @author Iskander Rakhmanberdiyev
 */
class HumanPlayer extends Player {

    /** A new player initially playing COLOR taking manual input of
     *  moves from GAME's input source. */
    HumanPlayer(Game game, Color color) {
        super(game, color);
    }

    @Override
    void makeMove() {
        Game game = getGame();
        int[] move = new int[2];
        if (game.getMove(move)) {
            game.makeMove(move[0], move[1]);
        }
    }

}

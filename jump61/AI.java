package jump61;

import java.util.ArrayList;
import java.util.Iterator;

/** An automated Player.
 *  @author Iskander Rakhmanberdiyev
 */
class AI extends Player {

    /** A new player of GAME initially playing COLOR that chooses
     *  moves automatically.
     */
    AI(Game game, Color color) {
        super(game, color);
        _board = (MutableBoard) getGame()._board;
    }

    @Override
    void makeMove() {
        Game game = getGame();
        int move = minmax(getColor(), _board, 4, Integer.MIN_VALUE);
        game.makeMove(move);
        game.message("%s moves %d %d\n",
                     getColor(), _board.row(move), _board.col(move));
    }
    /** Return the minimum of CUTOFF and the minmax value of board B
     *  (which must be mutable) for player P to a search depth of D
     *  (where D == 0 denotes evaluating just the next move).
     *  If MOVES is not null and CUTOFF is not exceeded, set MOVES to
     *  a list of all highest-scoring moves for P; clear it if
     *  non-null and CUTOFF is exceeded. the contents of B are
     *  invariant over this call. */
    private int minmax(Color p, MutableBoard b, int d, int cutoff) {
        if (d == 0) {
            return staticEval(p, b);
        }
        int size = getGame().getBoard().size();
        int bestSoFar = -1;
        for (int i = 0; i < size*size; i++) {
            if (getGame()._board.isLegal(getColor(), i)) {
                b.addSpot(p, i);
                int value = minmax(p.opposite(), b, d - 1, -bestSoFar);
                b.undo();
                if (value > cutoff) {
                    cutoff = value;
                    bestSoFar = i;
                }
            }
        }
        return bestSoFar;
    }

    /** Returns heuristic value of board B for player P.
     *  Higher is better for P. */
    private int staticEval(Color p, Board b) {
        int color = 0;
        int opp = 0;
        if (b.won()) {
            return (b.squares[0].getColor() == p ? Integer.MAX_VALUE
                    : Integer.MIN_VALUE);
        }
        for (Board.Square square : b.squares) {
            if (square.getColor() == p) {
                color++;
            }
        }
        for (Board.Square square : b.squares) {
            if (square.getColor() == p) {
                opp++;
            }
        }
        return color - opp;
    }

    MutableBoard _board;

    /** A nested class containing information for a Move to be
     *  potentially added to the current game */
    class Move {
        int _move;
        int _value;
        
        Move(int move) {
            _move = move;
        }
        void setVal(int val) {
            _value = val;
        }
        int getVal() {
            return _value;
        }
        void setMove(int move) {
            _move = move;
        }
        int getMove() {
            return _move;
        }
    }
}



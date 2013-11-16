package jump61;

import java.util.Formatter;

import static jump61.Color.*;

/** Represents the state of a Jump61 game.  Squares are indexed either by
 *  row and column (between 1 and size()), or by square number, numbering
 *  squares by rows, with squares in row 1 numbered 0 - size()-1, in
 *  row 2 numbered size() - 2*size() - 1, etc.
 *  @author Iskander Rakhmanberdiyev
 */
abstract class Board {

    /** (Re)initialize me to a cleared board with N squares on a side. Clears
     *  the undo history and sets the number of moves to 0. */
    void clear(int N) {
        unsupported("clear");
    }

    /** Copy the contents of BOARD into me. */
    void copy(Board board) {
        unsupported("copy");
    }

    /** Return the number of rows and of columns of THIS. */
    abstract int size();

    /** Returns the number of spots in the square at row R, column C,
     *  1 <= R, C <= size (). */
    abstract int spots(int r, int c);

    /** Returns the number of spots in square #N. */
    abstract int spots(int n);

    /** Returns the color of square #N, numbering squares by rows, with
     *  squares in row 1 number 0 - size()-1, in row 2 numbered
     *  size() - 2*size() - 1, etc. */
    abstract Color color(int n);

    /** Returns the color of the square at row R, column C,
     *  1 <= R, C <= size(). */
    abstract Color color(int r, int c);

    /** Returns the total number of moves made (red makes the odd moves,
     *  blue the even ones). */
    abstract int numMoves();

    /** Returns the Color of the player who would be next to move.  If the
     *  game is won, this will return the loser (assuming legal position). */
    Color whoseMove() {
        return (numMoves() % 2 == 0 ? Color.RED : Color.BLUE);
    }

    /** Return true iff row R and column C denotes a valid square. */
    final boolean exists(int r, int c) {
        return 1 <= r && r <= size() && 1 <= c && c <= size();
    }

    /** Return true iff S is a valid square number. */
    final boolean exists(int s) {
        int N = size();
        return 0 <= s && s < N * N;
    }

    /** Return the row number for square #N. */
    final int row(int n) {
        return (int) Math.floor(n / size()) + 1;
    }

    /** Return the column number for square #N. */
    final int col(int n) {
        return n % size() + 1;
    }

    /** Return the square number of row R, column C. */
    final int sqNum(int r, int c) {
        return size()*(r - 1) + (c - 1);
    }


    /** Returns true iff it would currently be legal for PLAYER to add a spot
        to square at row R, column C. */
    boolean isLegal(Color player, int r, int c) {
        return isLegal(player, sqNum(r, c));
    }

    /** Returns true iff it would currently be legal for PLAYER to add a spot
     *  to square #N. */
    boolean isLegal(Color player, int n) {
        return (squares[n].color == player | squares[n].color == WHITE);
    }

    /** Returns true iff PLAYER is allowed to move at this point. */
    boolean isLegal(Color player) {
        if (player == BLUE) {
            return numMoves() % 2 != 0;
        } else {
            return numMoves() % 2 == 0;
        }
    }

    /** Returns the winner of the current position, if the game is over,
     *  and otherwise null. */
    final Color getWinner() {
        return (numMoves() % 2 == 0 ? BLUE : RED);
    }

    /** Return the number of squares of given COLOR. */
    abstract int numOfColor(Color color);

    /** Add a spot from PLAYER at row R, column C.  Assumes
     *  isLegal(PLAYER, R, C). */
    void addSpot(Color player, int r, int c) {
        unsupported("addSpot");
    }

    /** Add a spot from PLAYER at square #N.  Assumes isLegal(PLAYER, N). */
    void addSpot(Color player, int n) {
        unsupported("addSpot");
    }

    /** Set the square at row R, column C to NUM spots (0 <= NUM), and give
     *  it color PLAYER if NUM > 0 (otherwise, white).  Clear the undo
     *  history. */
    void set(int r, int c, int num, Color player) {
        unsupported("set");
    }

    /** Set the square #N to NUM spots (0 <= NUM), and give it color PLAYER
     *  if NUM > 0 (otherwise, white).  Clear the undo history. */
    void set(int n, int num, Color player) {
        unsupported("set");
    }

    /** Set the current number of moves to N.  Clear the undo history. */
    void setMoves(int n) {
        unsupported("setMoves");
    }

    /** Undo the effects one move (that is, one addSpot command).  One
     *  can only undo back to the last point at which the undo history
     *  was cleared, or the construction of this Board. */
    void undo() {
        unsupported("undo");
    }

    /** Returns my dumped representation. */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("===\n");
        final int n = size();
        for (int row = 1; row <= n; row++) {
            sb.append("    ");
            for (int col = 1; col <= n; col++) {
                final Square square = squares[sqNum(row, col)];
                sb.append(square.toString());

                final boolean lastCol = (col == (n));
                sb.append(lastCol ? "\n" : " ");
            }
        }
        sb.append("===\n");
        return sb.toString();
    }

    /** Returns the number of neighbors of the square at row R, column C. */
    int neighbors(int r, int c) {
        if ((r == 1 || r == size()) && (c == 1 || c == size())) {
            return 2;
        } else if (r == 1 || r == size() || c == 1 || c == size()) {
            return 3;
        } else {
            return 4;
        }
    }

    /** Returns the number of neighbors of square #N. */
    int neighbors(int n) {
        return neighbors(row(n), col(n));
    }

    /** Returns:
     *      The index of the neighbor above square # SQR if N == 0
     *      The index of the neighbor below if N == 1
     *      The index of the neighbor to the left if N == 2
     *      The index of the neighbor to the right if N == 3.
     *  If the neighbor doesn't exist, returns -1. */
    int adjacent(int n, int sqr) {
        assert (sqr < (size() * size()));
        if ((sqr % size() == 0 && n == 2)) {
            return -1;
        }
        if (sqr % size() == size() - 1 && n == 3) {
            return -1;
        }
        if (sqr < size() && n == 0) {
            return -1;
        }
        if (((sqr >= ((size() - 1) * size())) && (sqr < (size() * size())))
            && n == 1) {
            return -1;
        }
        if (n == 0) { return sqr - size(); }
        if (n == 1) { return sqr + size(); }
        if (n == 2) { return sqr - 1; }
        if (n == 3) { return sqr + 1; }
        else { return -1; }
    }

    /** Indicate fatal error: OP is unsupported operation. */
    private void unsupported(String op) {
        String msg = String.format("'%s' operation not supported", op);
        throw new UnsupportedOperationException(msg);
    }

    /** The length of an end of line on this system. */
    private static final int NL_LENGTH =
        System.getProperty("line.separator").length();

    /** A board saving the contents of the previous board for purposes
     *  of undoing moves. Each PREVBOARD may itself have another PREVBOARD. */
    Board _prevBoard;

    /** An getter that returns this instance of board's previous board. */
    public Board getPrevBoard() {
        return _prevBoard;
    }

    public boolean won() {
        Color first = squares[0].getColor();
        if (first == WHITE) {return false;}
        for (Square square : squares) {
            if (square.getColor() != first) {
                return false;
            }
        }
        return true;
    }
    
    Square[] squares;

    /** A nested class representing an instance of a board square.
     *  Each square has a COLOR, which is White by default
     *  and a number of DOTS, which is 1 by default. */
    class Square {
        private Color color = WHITE;
        private int spots = 0;

        @Override
        /** Returns a string representation of THIS. */
        public String toString() {
            if (color == WHITE) {
                return "--";
            }
            final String cl = (color == RED ? "r" : "b");
            return String.format("%d%s", spots, cl);
        }

        /** Returns the color of the THIS. */
        public Color getColor() {
            return color;
        }

        /** Sets the color of THIS to COLOR. */
        public void setColor(Color color) {
            this.color = color;
        }

        /** Returns the number of spots on THIS. */
        public int getSpots() {
            return spots;
        }

        /** Sets THIS's number of spots to NUM. */
        public void setSpots(int num) {
            spots = num;
        }

        /** Adds one spot to THIS. */
        public void addSpot() {
            spots += 1;
        }

        /** Removes one spot from THIS. */
        public void remSpot() {
            spots -= 1;
        }

        /** Returns a new Square with the same attributes as THIS. */
        public Square clone() {
            Square result = new Square();
            result.setSpots(this.spots);
            result.setColor(this.color);
            return result;
        }
    }
    
    
}

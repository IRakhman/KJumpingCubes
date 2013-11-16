package jump61;

/** A Jump61 board state.
 *  @author Iskander Rakhmanberdiyev
 */
class MutableBoard extends Board {

    /** An N x N board in initial configuration. */
    MutableBoard(int N) {
        _N = N;
        squares = new Square[_N * _N];
        for (int i = 0; i < squares.length; i++) {
            squares[i] = new Square();
        }
    }

    /** A board whose initial contents are copied from BOARD0. Clears the
     *  undo history. */
    MutableBoard(Board board0) {
        copy(board0);
    }

    @Override
    void clear(int N) {
        _N = N;
        squares = new Square[_N * _N];
        _moves = 0;
        _prevBoard = null;
        for (int i = 0; i < squares.length; i++) {
            squares[i] = new Square();
        }
    }

    @Override
    void copy(Board board) {
        _N = board.size();
        _moves = board.numMoves();
        squares = new Square[board.squares.length];
        for (int i = 0; i <squares.length; i++) {
            squares[i] = board.squares[i].clone();
        }
        _prevBoard = board.getPrevBoard();
    }

    @Override
    int size() {
        return _N;
    }

    @Override
    int spots(int r, int c) {
        return spots(sqNum(r, c));
    }

    @Override
    int spots(int n) {
        return squares[n].getSpots();
    }

    @Override
    Color color(int r, int c) {
        return color(sqNum(r, c));
    }

    @Override
    Color color(int n) {
        return squares[n].getColor();
    }

    @Override
    int numMoves() {
        return _moves;
    }

    @Override
    int numOfColor(Color color) {
        int count = 0;
        for (Square elem : squares) {
            if (elem.getColor() == color) {
                count += 1;
            }
        }
        return count;
    }

    @Override
    void addSpot(Color player, int r, int c) {
        addSpot(player, sqNum(r, c));
    }

    @Override
    void addSpot(Color player, int n) {
        _prevBoard = new MutableBoard(this);
        _moves++;
        squares[n].setColor(player);
        squares[n].addSpot();
        jump(n);
    }
    /** Adds a spot  to square N for PLAYER without saving the board
     *  or jumping squares. Only used internally in JUMP. */
    private void _addSpot(Color player, int n) {
        squares[n].setColor(player);
        squares[n].addSpot();
    }
    @Override
    void set(int r, int c, int num, Color player) {
        set(sqNum(r, c), num, player);
    }

    @Override
    void set(int n, int num, Color player) {
        _prevBoard = null;
        squares[n].setColor(player);
        squares[n].setSpots(num);
    }

    @Override
    void setMoves(int num) {
        assert num > 0;
        _prevBoard = null;
        _moves = num;
    }

    @Override
    void undo() {
        assert _prevBoard != null;
        copy(_prevBoard);
    }
    /** True iff the square at index S is overfull. */
    private boolean overfull(int S) {
        return squares[S].getSpots() > neighbors(S);
    }

    /** Do all jumping on this board, assuming that initially, S is the only
     *  square that might be over-full. */
    private void jump(int S) {
        int k = 0;
        if (!overfull(S) || won()) {
            return;
        }
        while (k < 4) {
            try {
                _addSpot(squares[S].getColor(), adjacent(k, S));
                squares[S].remSpot();
                k++;
            } catch (ArrayIndexOutOfBoundsException e) {
                k++;
                continue;
            }
        }
        for (k = 0; k < 4; k++) {
            try {
                jump(adjacent(k, S));
            } catch (ArrayIndexOutOfBoundsException e) {
                continue;
            }
        }
    }

    /** Total combined number of moves by both sides. */
    protected int _moves;
    /** Convenience variable: size of board (squares along one edge). */
    private int _N;

    
}

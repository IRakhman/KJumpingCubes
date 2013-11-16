package jump61;

/** An unchecked exception that represents internally any kind of user
 *  input error.
 *  @author Iskander Rakhmanberdiyev
 */
class GameException extends RuntimeException {

    /** Just to make Java happy... */
    private static final long serialVersionUID = 1L;

    /** A GameException with no message. */
    GameException() {
    }

    /** A GameException for which .getMessage() is MSG. */
    GameException(String msg) {
        super(msg);
    }

    /** Returns an exception containing an error message formatted according
     *  to FORMAT and ARGS, as for printf or String.format. Typically, one uses
     *  this by throwing the result in a context where there is a 'try' block
     *  that handles it by printing the message (esp. via reportError). */
    static GameException error(String format, Object... args) {
        return new GameException(String.format(format, args));
    }

}

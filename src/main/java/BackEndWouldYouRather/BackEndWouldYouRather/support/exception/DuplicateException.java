package BackEndWouldYouRather.BackEndWouldYouRather.support.exception;

/**
 * When try to insert entity into database, but such entity already exists, then
 * throw this exception
 * 
 * @author Wenbo Wang (jackie-1685@163.com)
 */
@SuppressWarnings("serial")
public class DuplicateException extends RuntimeException {

	// ~ Constructors
	// ===================================================================================================

	/**
	 * Constructs a <code>DuplicateException</code> with the specified message.
	 *
	 * @param msg
	 *            the detail message.
	 */
	public DuplicateException(String msg) {
		super(msg);
	}

	/**
	 * Constructs a {@code DuplicateException} with the specified message and
	 * root cause.
	 *
	 * @param msg
	 *            the detail message.
	 * @param t
	 *            root cause
	 */
	public DuplicateException(String msg, Throwable t) {
		super(msg, t);
	}
}

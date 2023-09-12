package BackEndWouldYouRather.BackEndWouldYouRather.support.exception;

/**
 * When try to find entity from database, but such entity not exists, then throw
 * this exception
 * 
 * @author Wenbo Wang (jackie-1685@163.com)
 */
@SuppressWarnings("serial")
public class NotFoundException extends RuntimeException {

	// ~ Constructors
	// ===================================================================================================

	/**
	 * Constructs a <code>NotFoundException</code> with the specified message.
	 *
	 * @param msg
	 *            the detail message.
	 */
	public NotFoundException(String msg) {
		super(msg);
	}

	/**
	 * Constructs a {@code NotFoundException} with the specified message and
	 * root cause.
	 *
	 * @param msg
	 *            the detail message.
	 * @param t
	 *            root cause
	 */
	public NotFoundException(String msg, Throwable t) {
		super(msg, t);
	}
}

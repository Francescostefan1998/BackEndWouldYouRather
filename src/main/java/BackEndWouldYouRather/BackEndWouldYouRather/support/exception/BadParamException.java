package BackEndWouldYouRather.BackEndWouldYouRather.support.exception;

/**
 * When request send one or more bad parameters, then throw this exception
 * 
 * @author Wenbo Wang (jackie-1685@163.com)
 */
@SuppressWarnings("serial")
public class BadParamException extends RuntimeException {

	// ~ Constructors
	// ===================================================================================================

	/**
	 * Constructs a <code>BadParamException</code> with the specified message.
	 *
	 * @param msg
	 *            the detail message.
	 */
	public BadParamException(String msg) {
		super(msg);
	}

	/**
	 * Constructs a {@code BadParamException} with the specified message and
	 * root cause.
	 *
	 * @param msg
	 *            the detail message.
	 * @param t
	 *            root cause
	 */
	public BadParamException(String msg, Throwable t) {
		super(msg, t);
	}
}

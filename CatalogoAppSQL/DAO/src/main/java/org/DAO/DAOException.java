package org.DAO;

public class DAOException extends RuntimeException {
	// Source / Generate Constructors from superclass
	// generate Serial ID sobre DAOException

	private static final long serialVersionUID = -5499725691387714157L;

	public DAOException() {
		super();

	}

	public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public DAOException(String message, Throwable cause) {
		super(message, cause);

	}

	public DAOException(String message) {
		super(message);

	}

	public DAOException(Throwable cause) {
		super(cause);

	}

}

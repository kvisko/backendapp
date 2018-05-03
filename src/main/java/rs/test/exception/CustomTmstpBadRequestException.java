package rs.test.exception;

public class CustomTmstpBadRequestException extends BaseException {

	public CustomTmstpBadRequestException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}

	public CustomTmstpBadRequestException() {
		// TODO Auto-generated constructor stub
		super("error_tmstp");
	}

}

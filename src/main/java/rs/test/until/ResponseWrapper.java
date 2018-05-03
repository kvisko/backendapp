package rs.test.until;

import java.util.List;

public class ResponseWrapper {

	private Object data;

	private List<String> messages;

	public ResponseWrapper() {

	}

	public ResponseWrapper(Object data) {
		super();
		this.data = data;
	}

	public ResponseWrapper(Object data, List<String> messages) {
		super();
		this.data = data;
		this.messages = messages;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

}

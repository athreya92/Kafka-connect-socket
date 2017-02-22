package org.apache.kafka.connect.socket;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.source.SourceRecord;

/**
 * 
 * @author Shreyas Athreya S
 *
 */
public class SocketMessage {
	private String client;
	private String message;

	public SocketMessage() {
	}

	public SocketMessage(String client, String message) {
		this.client = client;
		this.message = message;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public SourceRecord getSourceRecord(String kafkaTopic) {
		return new SourceRecord(null, null, kafkaTopic, Schema.STRING_SCHEMA, client, Schema.STRING_SCHEMA, message);
	}
}

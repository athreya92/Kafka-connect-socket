package org.apache.kafka.connect.socket;

import java.util.Map;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;

/**
 * Define the connector config.
 *
 * @author Shreyas Athreya S
 */

public class KafkaConnectorConfig extends AbstractConfig {
	
	final static String TOPIC = "topic";
	final static String SCHEMA_NAME = "schema.name";
	final static String PORT = "port";
	final static String BATCH_SIZE = "batch.size";
	
	public static ConfigDef CONFIG_DEF = initConfigs();
	public KafkaConnectorConfig(Map<?, ?> props) {
		super(CONFIG_DEF, props);
	}
	public KafkaConnectorConfig(ConfigDef definition, Map<?, ?> originals) {
		super(CONFIG_DEF, originals);
	}
	private static ConfigDef initConfigs() {
		return new ConfigDef()
				.define(TOPIC, Type.STRING, null, Importance.HIGH, "MQTT Broker")
				.define(SCHEMA_NAME, Type.STRING, null, Importance.LOW, "MQTT Clean session")
				.define(PORT, Type.STRING, null, Importance.HIGH, "Client ID")
				.define(BATCH_SIZE, Type.STRING, null, Importance.HIGH, "MQTT QOS")
				;
	}
	public static void main(String[] args) {
		System.out.println(CONFIG_DEF.toRst());
	}


}

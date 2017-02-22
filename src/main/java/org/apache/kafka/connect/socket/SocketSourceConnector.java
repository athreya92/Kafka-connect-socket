package org.apache.kafka.connect.socket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.utils.AppInfoParser;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SocketSourceConnector implements the connector interface
 * to write on Kafka messages received on a Socket
 *
 * @author Shreyas Athreya S
 */
public class SocketSourceConnector extends SourceConnector {
    private final static Logger log = LoggerFactory.getLogger(SocketSourceConnector.class);

    private KafkaConnectorConfig kafkaConnectorConfig;
    private Map<String, String> connectorConfigProperties;
    

    /**
     * Get the version of this connector.
     *
     * @return the version, formatted as a String
     */
    @Override
    public String version() {
        return AppInfoParser.getVersion();
    }

    /**
     * Start this Connector. This method will only be called on a clean Connector, i.e. it has
     * either just been instantiated and initialized or {@link #stop()} has been invoked.
     *
     * @param map configuration settings
     */
    @Override
    public void start(Map<String, String> map) {
        log.trace("Parsing configuration");

        connectorConfigProperties = map;
        kafkaConnectorConfig = new KafkaConnectorConfig(connectorConfigProperties);

    }

    /**
     * Returns the Task implementation for this Connector.
     *
     * @return tha Task implementation Class
     */
    @Override
    public Class<? extends Task> taskClass() {
        return SocketSourceTask.class;
    }

    /**
     * Returns a set of configurations for the Task based on the current configuration.
     * It always creates a single set of configurations.
     *
     * @param i maximum number of configurations to generate
     * @return configurations for the Task
     */
    @Override
    public List<Map<String, String>> taskConfigs(int i) {
        List<Map<String, String>> taskConfigs = new ArrayList<>(1);
        Map<String, String> taskProps = new HashMap<>(connectorConfigProperties);
        taskConfigs.add(taskProps);
        return taskConfigs;
    }

    /**
     * Stop this connector.
     */
    @Override
    public void stop() {
    }


	@Override
	public ConfigDef config() {
		return KafkaConnectorConfig.CONFIG_DEF;
	}
}

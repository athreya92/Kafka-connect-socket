package org.apache.kafka.connect.socket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SocketSourceTask is a Task that reads records from a Socket for storage in Kafka.
 *
 * @author Shreyas Athreya S
 */
public class SocketSourceTask extends SourceTask {
    private final static Logger log = LoggerFactory.getLogger(SocketSourceTask.class);
    
    private SocketServerThread socketServerThread;
    private String topic;
    private int port;

    @Override
    public String version() {
        return new SocketSourceConnector().version();
    }

    /**
     * Start the Task. Handles configuration parsing and one-time setup of the Task.
     *
     * @param map initial configuration
     */
    @Override
    public void start(Map<String, String> map) {
        topic = map.get("topic");
        port = Integer.parseInt(map.get("port"));
        
        log.trace("Opening Socket");
        socketServerThread = new SocketServerThread(port);
        new Thread(socketServerThread).start();
    }

    /**
     * Poll this SocketSourceTask for new records.
     *
     * @return a list of source records
     * @throws InterruptedException
     */
    @Override
    public List<SourceRecord> poll() throws InterruptedException {
    	SocketMessage message = socketServerThread.messages.poll();
    	List<SourceRecord> records = new ArrayList<SourceRecord>();
    	if(message != null){
    		records.add(message.getSourceRecord(topic));
    	}
    	return records;
    }

    /**
     * Signal this SourceTask to stop.
     */
    @Override
    public void stop() {
        socketServerThread.stop();
    }
}

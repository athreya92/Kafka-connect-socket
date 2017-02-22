package org.apache.kafka.connect.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thread to handle a single Socket connection
 *
 * @author Shreyas Athreya S
 */
public class SocketThread extends Thread {
    private static final Logger log = LoggerFactory.getLogger(SocketThread.class);
    private Socket clientSocket;
    private BlockingQueue<SocketMessage> messages;

    public SocketThread(Socket clientSocket, BlockingQueue<SocketMessage> messages) {
        this.clientSocket = clientSocket;
        this.messages = messages;
    }

    public void run() {
        InputStream input;
        BufferedReader br;
        try {
            // get the input stream
            input = clientSocket.getInputStream();
            br = new BufferedReader(new InputStreamReader(input));
        } catch (IOException e) {
            log.error(e.getMessage());
            return;
        }

        // while connected, reads a line and saves it in the queue
        String line;
        while (true) {
            try {
                line = br.readLine();
                if (line == null) {
                    clientSocket.close();
                    return;
                } else {
                    messages.add(new SocketMessage(clientSocket.getRemoteSocketAddress().toString(), line));
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }
}

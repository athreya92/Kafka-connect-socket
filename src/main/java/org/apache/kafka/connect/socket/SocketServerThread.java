package org.apache.kafka.connect.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.kafka.connect.errors.ConnectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SocketServerThread accepts connections from a Socket and starts a new Thread for every new connection.
 *
 * @author Shreyas Athreya S
 */
public class SocketServerThread implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(SocketServerThread.class);
    protected BlockingQueue<SocketMessage> messages;

    private ServerSocket serverSocket;
    private Socket clientSocket;

    /**
     * Constructor of the class.
     *
     * @param port to use for creating the Socket
     */
    public SocketServerThread(Integer port) {
        this.messages = new LinkedBlockingQueue<>();
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new ConnectException("Impossible to open socket on port " + port);
        }
    }

    /**
     * Run the thread.
     */
    @Override
    public void run() {
        while (true) {
            try {
                // waits for connections
                clientSocket = serverSocket.accept();
                // when new client connected, start a new thread to handle it
                new SocketThread(clientSocket, messages).start();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

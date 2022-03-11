package net.reverseproxy.src.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ProxyClient implements Runnable {

    private final Logger logger;

    private final ProxyReceiver receiver;

    private final InputStream inputStream;
    private final OutputStream outputStream;

    public ProxyClient(Socket client) throws IOException {
        logger = LogManager.getLogger();
        logger.info("Successfully connected to the proxy server!");

        inputStream = client.getInputStream();
        outputStream = client.getOutputStream();

        receiver = new ProxyReceiver(this);

        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            int read;
            while ((read = inputStream.read()) != -1) {
                receiver.getOutputStream().write(read);
            }

            receiver.getOutputStream().flush();
        } catch (IOException e) {
            logger.error("Thread threw an exception", e);
        }
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }
}

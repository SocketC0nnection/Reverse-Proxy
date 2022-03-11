package net.reverseproxy.src.server;

import net.reverseproxy.src.client.ProxyClient;

import java.io.IOException;
import java.net.ServerSocket;

public class ProxyServer implements Runnable {

    private final ServerSocket server;

    public ProxyServer(int port) throws IOException {
        server = new ServerSocket(port);
    }

    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                new ProxyClient(server.accept());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

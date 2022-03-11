package net.reverseproxy.src.client;

import net.reverseproxy.src.ReverseProxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ProxyReceiver implements Runnable {

    private final ProxyClient client;

    private final InputStream inputStream;
    private final OutputStream outputStream;

    public ProxyReceiver(ProxyClient client) throws IOException {
        Socket socket = new Socket(ReverseProxy.getInstance().getTargetIp(), ReverseProxy.getInstance().getTargetPort());

        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();

        new Thread(this).start();

        this.client = client;
    }

    @Override
    public void run() {
        try {
            int read;
            while ((read = inputStream.read()) != -1) {
                client.getOutputStream().write(read);
            }

            client.getOutputStream().flush();
        } catch (IOException ignored) {}
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }
}

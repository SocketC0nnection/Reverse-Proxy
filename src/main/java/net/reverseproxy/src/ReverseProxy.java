package net.reverseproxy.src;

import net.reverseproxy.src.server.ProxyServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ReverseProxy {

    private static ReverseProxy instance;

    private String targetIp;
    private int targetPort;

    public ReverseProxy(String[] args) {
        instance = this;

        Logger logger = LogManager.getLogger();

        if(args.length < 2 || !args[0].contains(":")) {
            logger.error("Usage: <target-ip:target-port> <local-port>");

            return;
        }

        String[] targetData = args[0].split(":");

        targetIp = targetData[0];
        targetPort = Integer.parseInt(targetData[1]);

        int localPort = Integer.parseInt(args[1]);

        try {
            new ProxyServer(localPort).start();
            logger.info("Proxy server started on port " + localPort + "!");
        } catch (IOException e) {
            logger.error("Could not start server", e);
        }
    }

    public static void main(String[] args) {
        new ReverseProxy(args);
    }

    public int getTargetPort() {
        return targetPort;
    }

    public String getTargetIp() {
        return targetIp;
    }

    public static ReverseProxy getInstance() {
        return instance;
    }
}

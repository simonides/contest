package commons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;

public class SocketIO {
    private static final Logger logger = LogManager.getLogger(SocketIO.class.getName());

    private String host;
    private int port;

    private Socket connection;
    private DataOutputStream writer;
    private BufferedReader reader;

    public SocketIO() {
        this.host = "localhost";
        this.port = 7000;
    }

    public void connect() throws IOException {
        logger.info("Connecting to " + host + " on port " + port + "...");


        connection = new Socket(host, port);
        logger.info("Connected to " + connection.getRemoteSocketAddress());

        OutputStream outToServer = connection.getOutputStream();
        writer = new DataOutputStream(outToServer);

        InputStream inFromServer = connection.getInputStream();
        reader = new BufferedReader(new InputStreamReader(inFromServer));
    }

    public String readLine() {
        try {
            String s = reader.readLine();
//        logger.debug(s);
            return s;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeLine(String line) {
        try {
//        logger.debug(line);
            writer.writeBytes(line + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(String str) {
        try {
            writer.writeBytes(str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void newLine() {
        try {
            writer.writeBytes("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


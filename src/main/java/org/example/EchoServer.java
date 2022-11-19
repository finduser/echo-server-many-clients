package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

class EchoServer {
    static final String HOSTNAME = "127.0.0.1";
    static final int PORT = 5255;
    private ServerSocket socket;

    static final String END_CONNECTION_SIGNAL = "q!";
    static final String END_CONNECTION_ANSWER = "Goodbye!";

    public static void main(String[] args) throws IOException {
        EchoServer server = new EchoServer();
        server.start();
    }

    void start() throws IOException {
        socket = new ServerSocket(PORT);
        while (true) {
            new EchoServerHandler(socket.accept()).start();
        }
    }

    public void close() throws IOException {
        socket.close();
    }

    private static class EchoServerHandler extends Thread {
        private final Socket socket;

        private final Logger logger = Logger.getLogger(EchoServerHandler.class.getName());

        EchoServerHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()))) {

                String message;
                while ((message = in.readLine()) != null) {
                    if (END_CONNECTION_SIGNAL.equals(message)) {
                        out.println(END_CONNECTION_ANSWER);
                        break;
                    }
                    out.println(message);
                }
            } catch (IOException e) {
                logger.warning("Something went wrong.");
            }
        }
    }
}

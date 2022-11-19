package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class Client {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    void connect(int port) throws IOException {
        socket = new Socket(EchoServer.HOSTNAME, EchoServer.PORT);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    String sendMessage(String message) throws IOException {
        out.println(message);
        return in.readLine();
    }

    void close() throws IOException {
        socket.close();
        out.close();
        in.close();
    }

}

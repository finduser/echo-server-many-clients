package org.example;

import org.junit.jupiter.api.*;

import java.io.IOException;

class ClientTest {
    private Client client1;
    private Client client2;
    private Client client3;

    @BeforeEach
    void initConnections() throws IOException {
        client1 = new Client();
        client1.connect(EchoServer.PORT);
        client2 = new Client();
        client2.connect(EchoServer.PORT);
        client3 = new Client();
        client3.connect(EchoServer.PORT);
    }

    @Test
    void shouldServerResponse() throws IOException {
        String messageFromClient1 = "Blah blah";
        String answerToClient1 = client1.sendMessage(messageFromClient1);

        String messageFromClient2 = "Message from 2";
        String answerToClient2 = client2.sendMessage(messageFromClient2);

        String messageFromClient3 = "Another message, but from client no. 3";
        String answerToClient3 = client3.sendMessage(messageFromClient3);

        Assertions.assertEquals(messageFromClient1, answerToClient1);
        Assertions.assertEquals(messageFromClient2, answerToClient2);
        Assertions.assertEquals(messageFromClient3, answerToClient3);

        String answerFromServer1 = client1.sendMessage(EchoServer.END_CONNECTION_SIGNAL);
        String answerFromServer2 = client2.sendMessage(EchoServer.END_CONNECTION_SIGNAL);
        String answerFromServer3 = client3.sendMessage(EchoServer.END_CONNECTION_SIGNAL);

        Assertions.assertEquals(EchoServer.END_CONNECTION_ANSWER, answerFromServer1);
        Assertions.assertEquals(EchoServer.END_CONNECTION_ANSWER, answerFromServer2);
        Assertions.assertEquals(EchoServer.END_CONNECTION_ANSWER, answerFromServer3);
    }

    @AfterEach
    void closeClients() throws IOException {
        client1.close();
        client2.close();
        client3.close();
    }
}

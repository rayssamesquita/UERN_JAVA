package br.com.jogodavelha.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by '-Maxsuel and Rayssa on 07/05/2016.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        ListaJogadores listaJogadores = new ListaJogadores();
        ServerSocket welcomeSocket = new ServerSocket(6789);

        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            System.out.println(connectionSocket.getPort() + " - conectou.");

            Thread a = new Thread(new ThreadJogador(connectionSocket, listaJogadores));
            a.start();
        }
    }
}
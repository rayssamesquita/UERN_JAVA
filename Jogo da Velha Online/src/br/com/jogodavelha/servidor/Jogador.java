package br.com.jogodavelha.servidor;

import java.net.Socket;

/**
 * Created by '-Maxsuel and Rayssa on 07/05/2016.
 */
public class Jogador {
    private int id;
    private String nome;
    private Socket socket;
    private int idJogadorConvite;
    private boolean ocupado;

    public Jogador(int id, String nome, Socket connectionSocket) {
        this.id = id;
        this.nome = nome;
        this.socket = connectionSocket;
        this.ocupado = false;
    }

    public String toString() {
        return id + "-" + nome + "(" + socket.getPort() + (isOcupado() ? " Jogando contra: " + getIdJogadorConvite() : null) + ")#";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public int getIdJogadorConvite() {
        return idJogadorConvite;
    }

    public void setIdJogadorConvite(int idJogadorConvite) {
        this.idJogadorConvite = idJogadorConvite;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }
}

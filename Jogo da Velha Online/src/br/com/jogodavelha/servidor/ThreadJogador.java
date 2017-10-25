package br.com.jogodavelha.servidor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by '-Maxsuel and Rayssa on 07/05/2016.
 */
public class ThreadJogador implements Runnable {
    private Jogador jogadorAtual;
    public static int ID = 1;
    public int idThread;
    private String clientSentence;
    private BufferedReader inFromClient;
    private Socket connectionSocket;
    private ListaJogadores listaJogadores;
    private boolean tf = true;
    private boolean isOnline = true;

    public ThreadJogador(Socket connectionSocket, ListaJogadores listaJogadores) {
        this.connectionSocket = connectionSocket;
        this.listaJogadores = listaJogadores;
    }

    @Override
    public void run() {
        newJogador();
        while (isOnline) {
            try {
                inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

                clientSentence = inFromClient.readLine();

                Thread.sleep(100);

                String[] nivel1 = clientSentence.split(" ");

                switch (nivel1[0]) {
                    case "1":
                        outToClient.writeBytes("#" + enviarUsuariosOnline() + '\n');
                        break;
                    case "2":
                        //Enviar pedido
                        outToClient.writeBytes(jogarCom(Integer.parseInt(nivel1[1])) + '\n');
                        break;

                    case "3":
                        break;

                    default:
                        if (jogadorAtual.getIdJogadorConvite() == 0) { //se houver um convite
                            outToClient.writeBytes("0" + '\n');
                        } else if (isIDJogadoresConviteIguais()) {  //se os ID's de idJogadorConvite forem iguais o jogo inicia.
                            outToClient.writeBytes("JOGO-INICIADO" + '\n');
                            isOnline = false;
                        } else {
                            Jogador player = listaJogadores.buscarPorId(jogadorAtual.getIdJogadorConvite());
                            outToClient.writeBytes("3 Deseja jogar com " + player.getNome() + "?" + '\n');

                            clientSentence = inFromClient.readLine(); //resposta do convite

                            if (clientSentence.charAt(0) == 'Y') {
                                System.out.println("(" + jogadorAtual.getId() + "-" + jogadorAtual.getNome() + ") VS (" + player.getId() + "-" + player.getNome() + ")");
                                player.setIdJogadorConvite(jogadorAtual.getId());
                                //outToClient.writeBytes("JOGO-INICIADO" + '\n');

                                Thread.currentThread().interrupt();

                                Thread iniciarJogo = new Thread(new ThreadPartida(jogadorAtual, player, listaJogadores));
                                iniciarJogo.start();

                                isOnline = false;

                            } else if (clientSentence.charAt(0) == 'N') {
                                jogadorAtual.setIdJogadorConvite(0);
                                jogadorAtual.setOcupado(false);
                                player.setIdJogadorConvite(0);
                                player.setOcupado(false);
                                outToClient.writeBytes("JOGO-CANCELADO" + '\n');
                            }
                        }
                }
            } catch (IOException e) {
                listaJogadores.removerPorID(idThread);
                isOnline = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isIDJogadoresConviteIguais() {
        return jogadorAtual.getIdJogadorConvite() == listaJogadores.buscarPorId(jogadorAtual.getIdJogadorConvite()).getId() && jogadorAtual.getId() == listaJogadores.buscarPorId(jogadorAtual.getIdJogadorConvite()).getIdJogadorConvite();
    }

    private String jogarCom(int idJogadorAdv) {

        Jogador jogador = listaJogadores.buscarPorId(idJogadorAdv);
        if (jogadorAtual.getId() == idJogadorAdv) {
            return "mesmo-jogador";
        }
        if (!jogadorAtual.isOcupado() && !jogador.isOcupado()) {
            jogador.setIdJogadorConvite(jogadorAtual.getId());
            jogador.setOcupado(true);
            jogadorAtual.setOcupado(true);
            return "pedido-enviado";
        }
        return "jogador-ocupado";
    }

    private String enviarUsuariosOnline() {
        String todosJogadoresOnline = "";
        for (Jogador j : listaJogadores.getJogadores()) {
            todosJogadoresOnline += j.toString();
        }
        return todosJogadoresOnline;
    }

    private void newJogador() {
        try {
            inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            clientSentence = inFromClient.readLine();
            idThread = ID++;

            jogadorAtual = new Jogador(idThread, clientSentence, connectionSocket);
            listaJogadores.add(jogadorAtual);

            outToClient.writeBytes("#" + enviarUsuariosOnline() + '\n');

        } catch (IOException e) {
            listaJogadores.removerPorID(idThread);
        }

    }


}

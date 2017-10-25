package br.com.jogodavelha.servidor;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by '-Maxsuel and Rayssa on 09/05/2016.
 */
public class ThreadPartida implements Runnable {
    private ListaJogadores listaJogadores;
    private BufferedReader inFromClientPlayer1;
    private BufferedReader inFromClientPlayer2;
    private boolean isOnline = true;
    private Jogador player1;
    private Jogador player2;
    private boolean play1OK = true;
    private boolean play2OK = true;
    private String tabuleiroJogo[] = new String[9];
    private String msg;
    private String tabuleiroPreenchido;
    private int verificar = 0;

    public ThreadPartida(Jogador player1, Jogador player2, ListaJogadores listaJogadores) {
        this.player1 = player1; //dono da thread - O
        this.player2 = player2; //X
        this.listaJogadores = listaJogadores;
    }

    @Override
    public void run() {
        int inOut = 0;
        //Thread.sleep(2000);
        try {
            while (isOnline) {
                inFromClientPlayer1 = new BufferedReader(new InputStreamReader(player1.getSocket().getInputStream()));
                inFromClientPlayer2 = new BufferedReader(new InputStreamReader(player2.getSocket().getInputStream()));
                DataOutputStream outToClientPlayer1 = new DataOutputStream(player1.getSocket().getOutputStream());
                DataOutputStream outToClientPlayer2 = new DataOutputStream(player2.getSocket().getOutputStream());

                //play1
                outToClientPlayer1.writeBytes(verificar + "#" + getTabuleiroPreenchido() + '\n');
                if (verificarJogo() > 0 && verificarJogo() < 4) {
                    tabuleiroJogo = new String[9];
                }

                do {
                    msg = inFromClientPlayer1.readLine();

                    if (tabuleiroJogo[Integer.valueOf(msg)] == null) {
                        tabuleiroJogo[Integer.valueOf(msg)] = "X";
                        outToClientPlayer1.writeBytes("okay" + '\n');
                        play1OK = true;
                    } else {
                        outToClientPlayer1.writeBytes("erro" + '\n');
                        play1OK = false;
                    }
                } while (!play1OK);

                verificar = verificarJogo();

                if (verificar > 0 && verificar < 4) {
                    tabuleiroJogo = new String[9];
                }

                //TO-DO -- Verificar resultado
                outToClientPlayer1.writeBytes(verificar + "#" + getTabuleiroPreenchido() + '\n'); // | venceu?


                //play2
                outToClientPlayer2.writeBytes(verificar + "#" + getTabuleiroPreenchido() + '\n'); // | perdeu?

                do {
                    msg = inFromClientPlayer2.readLine();

                    if (tabuleiroJogo[Integer.valueOf(msg)] == null) {
                        tabuleiroJogo[Integer.valueOf(msg)] = "O";
                        outToClientPlayer2.writeBytes("okay" + '\n');
                        play2OK = true;
                    } else {
                        outToClientPlayer2.writeBytes("erro" + '\n');
                        play2OK = false;
                    }
                } while (!play2OK);

                verificar = verificarJogo();

                if (verificar > 0 && verificar < 4) {
                    tabuleiroJogo = new String[9];
                }

                outToClientPlayer2.writeBytes(verificar + "#" + getTabuleiroPreenchido() + '\n'); //  | venceu?

            }
        } catch (IOException e) {
            listaJogadores.removerPorID(player1.getId());
            listaJogadores.removerPorID(player2.getId());
            isOnline = false;
            Thread.currentThread().interrupt();
            //e.printStackTrace();
        }

    }

    private int verificarJogo() {
        if (tabuleiroJogo[0] == tabuleiroJogo[1] && tabuleiroJogo[0] == tabuleiroJogo[2] && tabuleiroJogo[0] != null) {
            if (tabuleiroJogo[1].equals("X")) {
                return 1;
            } else {
                return 2;
            }
        } else if (tabuleiroJogo[3] == (tabuleiroJogo[4]) && tabuleiroJogo[3] == tabuleiroJogo[5] && tabuleiroJogo[3] != null) {
            if (tabuleiroJogo[4].equals("X")) {
                return 1;
            } else {
                return 2;
            }
        } else if (tabuleiroJogo[6] == tabuleiroJogo[7] && tabuleiroJogo[6] == tabuleiroJogo[8] && tabuleiroJogo[6] != null) {
            if (tabuleiroJogo[7].equals("X")) {
                return 1;
            } else {
                return 2;
            }
        } else if (tabuleiroJogo[0] == tabuleiroJogo[3] && tabuleiroJogo[0] == tabuleiroJogo[6] && tabuleiroJogo[0] != null) {
            if (tabuleiroJogo[3].equals("X")) {
                return 1;
            } else {
                return 2;
            }
        } else if (tabuleiroJogo[1] == tabuleiroJogo[4] && tabuleiroJogo[1] == tabuleiroJogo[7] && tabuleiroJogo[1] != null) {
            if (tabuleiroJogo[4].equals("X")) {
                return 1;
            } else {
                return 2;
            }
        } else if (tabuleiroJogo[2] == tabuleiroJogo[5] && tabuleiroJogo[2] == tabuleiroJogo[8] && tabuleiroJogo[2] != null) {
            if (tabuleiroJogo[5].equals("X")) {
                return 1;
            } else {
                return 2;
            }
        } else if (tabuleiroJogo[0] == tabuleiroJogo[4] && tabuleiroJogo[0] == tabuleiroJogo[8] && tabuleiroJogo[0] != null) {
            if (tabuleiroJogo[4].equals("X")) {
                return 1;
            } else {
                return 2;
            }
        } else if (tabuleiroJogo[2] == tabuleiroJogo[4] && tabuleiroJogo[2] == tabuleiroJogo[6] && tabuleiroJogo[2] != null) {
            if (tabuleiroJogo[4].equals("X")) {
                return 1;
            } else {
                return 2;
            }
        } else {
            if (completo()) {
                return 3;
            }
            return 0;
        }
    }

    private boolean completo() {
        for (String s : tabuleiroJogo) {
            if (s == null) {
                return false;
            }
        }
        return true;
    }

    public String getTabuleiroPreenchido() {
        String aux = "";
        for (String s : tabuleiroJogo) {
            if (s == null) {
                aux += " " + "-";
            } else {
                aux += s + "-";
            }
        }
        return aux;
    }

    public void setTabuleiroPreenchido(String tabuleiroPreenchido) {
        this.tabuleiroPreenchido = tabuleiroPreenchido;
    }
}

package br.com.jogodavelha.cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by '-Maxsuel and Rayssa on 14/05/2016.
 */
public class Game extends JFrame implements ActionListener {
    private JPanel painel;
    private JPanel p_total;
    private JButton b[];
    private JLabel vez;
    private boolean minhaVez = true;
    private int minhaJogada = 1;
    private String SIMBOLO;

    int nJogador;
    Socket clientSocket;
    private boolean isOnline = true;
    String msg;
    String tabuleiro[];

    public Game(Socket clientSocket, int nJogador) {
        super("Jogo da Velha <3");
        this.clientSocket = clientSocket;
        this.nJogador = nJogador;

        this.SIMBOLO = (nJogador == 1) ? "X" : "O";

        vez = new JLabel("Iniciando...");

        p_total = new JPanel();
        painel = new JPanel();

        painel.setLayout(new GridLayout(3, 0));

        b = new JButton[9];
        for (int i = 0; i < 9; i++) {
            b[i] = new JButton(" ");
            b[i].setFont(new java.awt.Font("Tahoma", 0, 36));
            b[i].setForeground(new java.awt.Color(255, 153, 255));
            b[i].setBackground(new java.awt.Color(255, 255, 255));
            painel.add(b[i]);
        }

        p_total.setLayout(new BorderLayout());

        p_total.add(vez, BorderLayout.NORTH);
        p_total.add(painel, BorderLayout.CENTER);

        for (int i = 0; i < 9; i++) {
            b[i].addActionListener(this);
        }

        this.setContentPane(p_total);
        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);


        iniciarConexao();
    }

    private void iniciarConexao() {
        while (isOnline) {
            try {
                DataOutputStream enviarParaSevidor = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader lerDoServidor = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                msg = lerDoServidor.readLine();

                switch (msg.split("#")[0]) {
                    case "1":
                        if (SIMBOLO == "X") {
                            JOptionPane.showMessageDialog(this, "Você Venceu!");
                        } else {
                            JOptionPane.showMessageDialog(this, "Você Perdeu!");
                        }
                        break;
                    case "2":
                        if (SIMBOLO == "O") {
                            JOptionPane.showMessageDialog(this, "Você Venceu!");
                        } else {
                            JOptionPane.showMessageDialog(this, "Você Perdeu!");
                        }
                        break;
                    case "3":
                        JOptionPane.showMessageDialog(this, "Deu velha!");
                        break;
                }

                preenchidoTabuleiro(msg.split("#")[1]);
                msg = msg.split("#")[1];
                minhaJogada = 10;
                vez.setText("Sua vez.");

                while (minhaJogada == 10) {
                    System.out.print("");
                }

                enviarParaSevidor.writeBytes(String.valueOf(minhaJogada) + '\n');

                msg = lerDoServidor.readLine();
                //tabuleiro = msg.split("#");


                switch (msg) {
                    case "okay":
                        b[minhaJogada].setText(SIMBOLO);
                        vez.setText("Aguarde.");
                        //preenchidoTabuleiro(tabuleiro[1]);
                        break;
                    case "erro":
                        while (!("okay".equals(msg))) {
                            minhaJogada = 10;
                            while (minhaJogada == 10) {
                                System.out.print("");
                            }

                            enviarParaSevidor.writeBytes(String.valueOf(minhaJogada) + '\n'); //envia
                            msg = lerDoServidor.readLine(); //recebe
                        }
                        b[minhaJogada].setText(SIMBOLO);
                        break;
                }

                msg = lerDoServidor.readLine();

                preenchidoTabuleiro(msg.split("#")[1]);
                msg = msg.split("#")[0];

                switch (msg.split("#")[0]) {
                    case "1":
                        if (SIMBOLO == "X") {
                            JOptionPane.showMessageDialog(this, "Você Venceu!");
                        } else {
                            JOptionPane.showMessageDialog(this, "Você Perdeu!");
                        }
                        break;
                    case "2":
                        if (SIMBOLO == "O") {
                            JOptionPane.showMessageDialog(this, "Você Venceu!");
                        } else {
                            JOptionPane.showMessageDialog(this, "Você Perdeu!");
                        }
                        break;
                    case "3":
                        JOptionPane.showMessageDialog(this, "Deu velha!");
                        break;
                }

            } catch (IOException e) {
                isOnline = false;
                e.printStackTrace();
            }
        }

    }

    private void preenchidoTabuleiro(String s) {
        String stg[] = s.split("-");
        for (int i = 0; i < 9; i++) {
            b[i].setText(stg[i]);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (minhaVez == true) {
            if (ae.getSource().equals(b[0])) {
                minhaJogada = 0;
            } else if (ae.getSource().equals(b[1])) {
                minhaJogada = 1;
            } else if (ae.getSource().equals(b[2])) {
                minhaJogada = 2;
            } else if (ae.getSource().equals(b[3])) {
                minhaJogada = 3;
            } else if (ae.getSource().equals(b[4])) {
                minhaJogada = 4;
            } else if (ae.getSource().equals(b[5])) {
                minhaJogada = 5;
            } else if (ae.getSource().equals(b[6])) {
                minhaJogada = 6;
            } else if (ae.getSource().equals(b[7])) {
                minhaJogada = 7;
            } else if (ae.getSource().equals(b[8])) {
                minhaJogada = 8;
            }
        }
    }
}

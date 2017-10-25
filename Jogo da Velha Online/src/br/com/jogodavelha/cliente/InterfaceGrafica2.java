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
 * Created by '-Maxsuel and Rayssa on 17/05/2016.
 */
public class InterfaceGrafica2 extends JFrame implements ActionListener {
    private static Thread a;
    private static Thread b;

    private static String IP, idNome;

    private static String enviar;
    private static boolean stop = false;
    private static boolean isEscreverLiberado = true;
    private static String[] onLines = {" "};

    static JComboBox ips;
    JPanel painel;
    JButton jogar;
    JButton att;

    public InterfaceGrafica2(String IP, String idNome) {
        super("Jogo da Velha <3");
        this.IP = IP;
        this.idNome = idNome;
        this.enviar = idNome;
        int i;

        ips = new JComboBox();

        a = new Thread(new ThreadClienteA());
        a.start();

        jogar = new JButton("Jogar");
        att = new JButton("F5");

        att.addActionListener(this);
        jogar.addActionListener(this);

        painel = new JPanel();

        painel.setLayout(new FlowLayout());

        painel.add(att);
        painel.add(ips);
        painel.add(jogar);

        this.setContentPane(painel);
        this.setSize(500, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(att)) {
            atualizarLista();
        } else if (e.getSource().equals(jogar)) {
            enviar = "2 " + String.valueOf(ips.getSelectedItem()).split("-")[0];
        }
    }

    public static void atualizarLista() {
        enviar = "1";
        ips.removeAllItems();
        for (String str : onLines) {
            ips.addItem(str);
        }

    }

    private class ThreadClienteA implements Runnable {

        String sentence;
        String modifiedSentence;
        String nome;

        @Override
        public void run() {
            try {
                Socket clientSocket = new Socket(IP, 6789);

                do {
                    DataOutputStream enviarParaSevidor = new DataOutputStream(clientSocket.getOutputStream());
                    BufferedReader lerDoServidor = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    if (enviar != "0") {
                        enviarParaSevidor.writeBytes(enviar + '\n');
                        enviar = "0";
                    } else {
                        enviarParaSevidor.writeBytes("0" + '\n');
                    }

                    modifiedSentence = lerDoServidor.readLine();

                    if (modifiedSentence.charAt(0) != '0') {
                        if (modifiedSentence.charAt(0) == '#') {
                            onLines = modifiedSentence.split("#");
                        } else if (modifiedSentence.contains("JOGO-INICIADO")) {

                            dispose();
                            Game newGame = new Game(clientSocket, 2);

                        } else if (modifiedSentence.charAt(0) == '3') {
                            if (JOptionPane.showConfirmDialog(null, modifiedSentence, "Convite", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                enviarParaSevidor.writeBytes("YES" + '\n');
                                //modifiedSentence = lerDoServidor.readLine();

                                dispose();

                                Game newGame = new Game(clientSocket, 1);
                            } else {
                                enviarParaSevidor.writeBytes("NO" + '\n');
                            }
                        } else if (modifiedSentence.contains("jogador-ocupado")) {
                            JOptionPane.showMessageDialog(null, "Jogador ocupado.");
                        } else if (modifiedSentence.contains("mesmo-jogador")) {
                            JOptionPane.showMessageDialog(null, "Não é possível jogar contra você mesmo. Escolha outro adversário.");
                        }
                    }
                } while (!stop);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

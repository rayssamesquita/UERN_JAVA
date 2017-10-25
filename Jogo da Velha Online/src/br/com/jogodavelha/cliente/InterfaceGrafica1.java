package br.com.jogodavelha.cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by '-Maxsuel and Rayssa on 17/05/2016.
 */
public class InterfaceGrafica1 extends JFrame implements ActionListener {

    JButton ir;
    JPanel painel;
    JTextField ip;
    JTextField id;

    public InterfaceGrafica1() {
        super("Jogo da Velha <3");
        int i;

        ir = new JButton("IR");
        ip = new JTextField("Digite o IP do servidor...");
        id = new JTextField("Digite seu ID...");

        painel = new JPanel();


        painel.setLayout(new GridLayout(0, 1));

        painel.add(ip);
        painel.add(id);
        painel.add(ir);

        ir.addActionListener(this);
        ip.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ip.setText("");
            }
        });
        id.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                id.setText("");
            }
        });

        this.setContentPane(painel);
        this.setSize(300, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(ir)) {
            InterfaceGrafica2 interfaceGrafica2 = new InterfaceGrafica2(ip.getText(), id.getText());
            dispose();
        }
    }


}

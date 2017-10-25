
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Calculadora extends JFrame implements ActionListener, KeyListener {

    private JPanel painel = null;
    private JPanel painel2 = null;
    private JPanel painel3 = null;
    private JButton botao[];
    private JLabel label;
    private String calculo = "";
    private int obs = 0;

    public Calculadora() {
        super("Calculadora <3");

        painel = new JPanel();
        painel2 = new JPanel();
        painel3 = new JPanel();
        botao = new JButton[19];
        label = new JLabel(" ");

        painel2.setBackground(Color.BLACK);
        painel2.setMaximumSize(new java.awt.Dimension(220, 80));
        painel2.setPreferredSize(new java.awt.Dimension(220, 80));
        painel2.setMinimumSize(new java.awt.Dimension(220, 80));

        label.setFont(new Font("DialogInput", 0, 35));
        label.setMaximumSize(new java.awt.Dimension(220, 80));
        label.setPreferredSize(new java.awt.Dimension(220, 80));
        label.setMinimumSize(new java.awt.Dimension(220, 80));
        label.setForeground(new java.awt.Color(255, 255, 255));
        label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        painel2.add(label);

        painel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridwidth = 1;
        c.gridheight = 1;

        botao[0] = new JButton("AC");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        c.gridx = 0;
        c.gridy = 2;
        painel.add(botao[0], c);

        botao[1] = new JButton("%");
        c.gridx = 1;
        c.gridy = 2;
        painel.add(botao[1], c);

        botao[2] = new JButton("+-");
        c.gridx = 2;
        c.gridy = 2;
        painel.add(botao[2], c);

        botao[3] = new JButton("/");
        c.gridx = 3;
        c.gridy = 2;
        painel.add(botao[3], c);

        botao[4] = new JButton("7");
        c.gridx = 0;
        c.gridy = 3;
        painel.add(botao[4], c);

        botao[5] = new JButton("8");
        c.gridx = 1;
        c.gridy = 3;
        painel.add(botao[5], c);

        botao[6] = new JButton("9");
        c.gridx = 2;
        c.gridy = 3;
        painel.add(botao[6], c);

        botao[7] = new JButton("X");
        c.gridx = 3;
        c.gridy = 3;
        painel.add(botao[7], c);

        botao[8] = new JButton("4");
        c.gridx = 0;
        c.gridy = 4;
        painel.add(botao[8], c);

        botao[9] = new JButton("5");
        c.gridx = 1;
        c.gridy = 4;
        painel.add(botao[9], c);

        botao[10] = new JButton("6");
        c.gridx = 2;
        c.gridy = 4;
        painel.add(botao[10], c);

        botao[11] = new JButton("-");
        c.gridx = 3;
        c.gridy = 4;
        painel.add(botao[11], c);

        botao[12] = new JButton("1");
        c.gridx = 0;
        c.gridy = 5;
        painel.add(botao[12], c);

        botao[13] = new JButton("2");
        c.gridx = 1;
        c.gridy = 5;
        painel.add(botao[13], c);

        botao[14] = new JButton("3");
        c.gridx = 2;
        c.gridy = 5;
        painel.add(botao[14], c);

        botao[15] = new JButton("+");
        c.gridx = 3;
        c.gridy = 5;
        painel.add(botao[15], c);

        botao[16] = new JButton("0");
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 6;
        painel.add(botao[16], c);

        botao[17] = new JButton(".");
        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 6;
        painel.add(botao[17], c);

        botao[18] = new JButton("=");
        c.gridx = 3;
        c.gridy = 6;
        painel.add(botao[18], c);

        for (JButton but : botao) {
            but.addActionListener(this);
        }

        painel.setFocusable(true);
        painel.requestFocusInWindow();
        painel.addKeyListener(this);

        painel3.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();

        g.gridwidth = 4;
        g.gridheight = 2;
        g.gridx = 0;
        g.gridy = 0;
        painel3.add(painel2, g);

        g.gridwidth = 4;
        g.gridheight = 4;
        g.gridx = 3;
        g.gridy = 3;
        painel3.add(painel, g);

        this.setContentPane(painel3);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(250, 300);
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        // @(+)  #(-)  &(*)  !(/)
        if (ae.getSource() == botao[0]) { //AC
            evento(18);
        } else if (ae.getSource() == botao[1]) { //%
            evento(17);
        } else if (ae.getSource() == botao[2]) { //+-
            evento(16);
        } else if (ae.getSource() == botao[3]) { // /
            evento(13);
        } else if (ae.getSource() == botao[4]) { //7
            evento(7);
        } else if (ae.getSource() == botao[5]) { //8
            evento(8);
        } else if (ae.getSource() == botao[6]) { //9
            evento(9);
        } else if (ae.getSource() == botao[7]) { //X
            evento(12);
        } else if (ae.getSource() == botao[8]) { //4
            evento(4);
        } else if (ae.getSource() == botao[9]) { //5
            evento(5);
        } else if (ae.getSource() == botao[10]) { //6
            evento(6);
        } else if (ae.getSource() == botao[11]) { //-
            evento(11);
        } else if (ae.getSource() == botao[12]) { //1
            evento(1);
        } else if (ae.getSource() == botao[13]) { //2
            evento(2);
        } else if (ae.getSource() == botao[14]) { //3
            evento(3);
        } else if (ae.getSource() == botao[15]) { //+
            evento(10);
        } else if (ae.getSource() == botao[16]) { //0
            evento(0);
        } else if (ae.getSource() == botao[17]) { //.
            evento(15);
        } else if (ae.getSource() == botao[18]) { //=
            evento(14);
        }
        painel.requestFocusInWindow();
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        //System.out.println(ke.getKeyChar());
        if (ke.getKeyChar() == KeyEvent.VK_BACK_SPACE) { //AC
            evento(18);
        } else if (ke.getKeyChar() == '%') { //%
            evento(17);
        } else if (ke.getKeyChar() == '±') { //+-
            evento(16);
        } else if (ke.getKeyChar() == KeyEvent.VK_DIVIDE || ke.getKeyChar() == '/') { // /
            evento(13);
        } else if (ke.getKeyChar() == KeyEvent.VK_MULTIPLY || ke.getKeyChar() == '*') { // X
            evento(12);
        } else if (ke.getKeyChar() == KeyEvent.VK_MINUS || ke.getKeyChar() == '-') { //-
            evento(11);
        } else if (ke.getKeyChar() == KeyEvent.VK_PLUS || ke.getKeyChar() == '+') { //+
            evento(10);
        } else if (ke.getKeyChar() == KeyEvent.VK_ENTER) { //=
            evento(14);
        } else if (ke.getKeyChar() == KeyEvent.VK_PERIOD || ke.getKeyChar() == '.') { // .
            evento(15);
        } else if (ke.getKeyChar() == KeyEvent.VK_0) { // 0
            evento(0);
        } else if (ke.getKeyChar() == KeyEvent.VK_1) { // 1
            evento(1);
        } else if (ke.getKeyChar() == KeyEvent.VK_2) { // 2
            evento(2);
        } else if (ke.getKeyChar() == KeyEvent.VK_3) { // 3
            evento(3);
        } else if (ke.getKeyChar() == KeyEvent.VK_4) { // 4
            evento(4);
        } else if (ke.getKeyChar() == KeyEvent.VK_5) { // 5
            evento(5);
        } else if (ke.getKeyChar() == KeyEvent.VK_6) { // 6
            evento(6);
        } else if (ke.getKeyChar() == KeyEvent.VK_7) { // 7
            evento(7);
        } else if (ke.getKeyChar() == KeyEvent.VK_8) { // 8
            evento(8);
        } else if (ke.getKeyChar() == KeyEvent.VK_9) { // 9
            evento(9);
        }
    }

    private void evento(int i) {
        switch (i) {
            case 0: // 0
                calculo += "0";
                break;
            case 1: // 1
                calculo += "1";
                break;
            case 2: // 2
                calculo += "2";
                break;
            case 3: // 3
                calculo += "3";
                break;
            case 4: // 4
                calculo += "4";
                break;
            case 5: // 5
                calculo += "5";
                break;
            case 6: // 6
                calculo += "6";
                break;
            case 7: // 7
                calculo += "7";
                break;
            case 8: // 8
                calculo += "8";
                break;
            case 9: // 9
                calculo += "9";
                break;
            case 10: // +
                if (calculo.length() == 0) {
                } else if (calculo.charAt(calculo.length() - 1) == '!') {
                } else if (calculo.charAt(calculo.length() - 1) == '#') {
                } else if (calculo.charAt(calculo.length() - 1) == '@') {
                } else if (calculo.charAt(calculo.length() - 1) == '&') {
                } else {
                    calculo += "@";
                    if (operacoes()) {
                        calculo += "@";
                    }
                }
                break;
            case 11: // -
                if (calculo.length() == 0) {
                    calculo += "-";
                } else if (calculo.charAt(calculo.length() - 1) == '!') {
                } else if (calculo.charAt(calculo.length() - 1) == '#') {
                } else if (calculo.charAt(calculo.length() - 1) == '@') {
                } else if (calculo.charAt(calculo.length() - 1) == '&') {
                } else {
                    calculo += "#";
                    if (operacoes()) {
                        calculo += "#";
                    }
                }
                break;
            case 12: // *
                if (calculo.length() == 0) {
                } else if (calculo.charAt(calculo.length() - 1) == '!') {
                } else if (calculo.charAt(calculo.length() - 1) == '#') {
                } else if (calculo.charAt(calculo.length() - 1) == '@') {
                } else if (calculo.charAt(calculo.length() - 1) == '&') {
                } else {
                    calculo += "&";
                    if (operacoes()) {
                        calculo += "&";
                    }
                }
                break;
            case 13: // /
                if (calculo.length() == 0) {
                } else if (calculo.charAt(calculo.length() - 1) == '!') {
                } else if (calculo.charAt(calculo.length() - 1) == '#') {
                } else if (calculo.charAt(calculo.length() - 1) == '@') {
                } else if (calculo.charAt(calculo.length() - 1) == '&') {
                } else {
                    calculo += "!";
                    if (operacoes()) {
                        calculo += "!";
                    }
                }
                break;
            case 14: // =
                if (!(calculo.matches("[0-9,.]+"))) {
                    if (operacoes()) {
                        calculo += "";
                    }
                }
                break;
            case 15: // .
                calculo += ".";
                break;
            case 16: // +-
                if (calculo.matches("[0-9,.,-]+")) {
                    double aux = (Double.parseDouble(calculo)) * -1;
                    calculo = "" + aux;
                    if (operacoes()) {
                        calculo += "";
                    }
                }
                break;
            case 17: // %
                obs = 2;
                if (calculo.length() == 0) {
                } else if (calculo.charAt(calculo.length() - 1) == '!') {
                } else if (calculo.charAt(calculo.length() - 1) == '#') {
                } else if (calculo.charAt(calculo.length() - 1) == '@') {
                } else if (calculo.charAt(calculo.length() - 1) == '&') {
                } else {
                    obs = 2;
                    if (operacoes()) {
                        calculo += "";
                    }
                }
                break;
            case 18: // AC
                if (calculo.length() > 0) {
                    calculo = calculo.substring(0, calculo.length() - 1);
                }
                break;
        }

        String valor1Aux = calculo;
        valor1Aux = valor1Aux.replaceAll("@", "+");
        valor1Aux = valor1Aux.replaceAll("#", "-");
        valor1Aux = valor1Aux.replaceAll("&", "*");
        valor1Aux = valor1Aux.replaceAll("!", "/");

        label.setText(valor1Aux);
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }

    private boolean operacoes() {

        // @(+)  #(-)  &(*)  !(/)
        String operacao = calculo;
        String[] parts = calculo.split("[@#&!=]");

        if (obs == 0) {
            if (parts.length == 2) {

                operacao = operacao.substring(0, calculo.length() - 1);

                if (operacao.contains("@")) {
                    calculo = String.valueOf(Double.parseDouble(parts[0]) + Double.parseDouble(parts[1]));
                }
                if (operacao.contains("#")) {
                    calculo = String.valueOf(Double.parseDouble(parts[0]) - Double.parseDouble(parts[1]));
                }
                if (operacao.contains("&")) {
                    calculo = String.valueOf(Double.parseDouble(parts[0]) * Double.parseDouble(parts[1]));
                }
                if (operacao.contains("!")) {
                    calculo = String.valueOf(Double.parseDouble(parts[0]) / Double.parseDouble(parts[1]));
                }

                //calculo +=  parts[2];

                /*
                 switch (i) {
                 case 0:
                 calculo = String.valueOf(Double.parseDouble(parts[0]) + Double.parseDouble(parts[1]));
                 break;
                 case 1:
                 calculo = String.valueOf(Double.parseDouble(parts[0]) - Double.parseDouble(parts[1]));
                 break;
                 case 2:
                 calculo = String.valueOf(Double.parseDouble(parts[0]) / Double.parseDouble(parts[1]));
                 break;
                 case 3:
                 calculo = String.valueOf(Double.parseDouble(parts[0]) * Double.parseDouble(parts[1]));
                 break;
                 }
                 */
                //calculo +=  parts[2];
            } else {
                return false;
            }
            return true;
        } else {
            if (parts.length == 2) {

                operacao = operacao.substring(0, calculo.length() - 1);

                if (operacao.contains("@")) {
                    calculo = String.valueOf(Double.parseDouble(parts[0]) + (Double.parseDouble(parts[0]) * (Double.parseDouble(parts[1]) / 100)));
                }
                if (operacao.contains("#")) {
                    calculo = String.valueOf(Double.parseDouble(parts[0]) - (Double.parseDouble(parts[0]) * (Double.parseDouble(parts[1]) / 100)));
                }
                if (operacao.contains("&")) {
                    calculo = String.valueOf(Double.parseDouble(parts[0]) * (Double.parseDouble(parts[0]) * (Double.parseDouble(parts[1]) / 100)));
                }
                if (operacao.contains("!")) {
                    calculo = String.valueOf(Double.parseDouble(parts[0]) / (Double.parseDouble(parts[0]) * (Double.parseDouble(parts[1]) / 100)));
                }
                obs = 0;
            }
            return true;
        }
    }

}

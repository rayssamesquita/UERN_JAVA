
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPanel;

public class Jogodavelha extends JFrame implements ActionListener {

    private JPanel painel;
    private JPanel p_total;
    private JButton b[];
    private JMenuBar m_barra;
    private JMenu m_arq;
    private JMenu m_ajuda;
    private JMenuItem i_nj;
    private JMenuItem i_out;
    private JMenuItem i_above;
    private int selecionador = 0;

    public Jogodavelha() {
        super("Jogo da Velha <3");
        int i;

        m_barra = new JMenuBar();

        m_arq = new JMenu("Arquivo");
        m_ajuda = new JMenu("Ajuda");

        i_nj = new JMenuItem("Novo Jogo");
        i_nj.addActionListener(this);
        m_arq.add(i_nj);

        i_out = new JMenuItem("Sair");
        i_out.addActionListener(this);
        m_arq.add(i_out);

        i_above = new JMenuItem("Sobre");
        i_above.addActionListener(this);
        m_ajuda.add(i_above);

        p_total = new JPanel();
        painel = new JPanel();


        m_barra.add(m_arq);
        m_barra.add(m_ajuda);

        painel.add(m_barra);

        painel.setLayout(new GridLayout(3, 0));

        b = new JButton[9];
        for (i = 0; i < 9; i++) {
            b[i] = new JButton(" ");
            b[i].setFont(new java.awt.Font("Tahoma", 0, 36));
            b[i].setForeground(new java.awt.Color(255, 153, 255));
            b[i].setBackground(new java.awt.Color(255, 255, 255));
            painel.add(b[i]);
        }

        p_total.setLayout(new BorderLayout());

        p_total.add(m_barra, BorderLayout.NORTH);
        p_total.add(painel, BorderLayout.CENTER);

        for (i = 0; i < 9; i++) {
            b[i].addActionListener(this);
        }

        this.setContentPane(p_total);
        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public synchronized void actionPerformed(ActionEvent ae) {

        if (ae.getActionCommand().equals("Novo Jogo")){
            fClear();
        }else if (ae.getActionCommand().equals("Sobre")){
            showMessageDialog(null, "");
        }else if (ae.getActionCommand().equals("Sair")) {
            System.exit(0);
        }

        if (ae.getSource().equals(b[0])) {
            if (selecionador == 0) {
                if (b[0].getText().equals(" ")) {
                    b[0].setText("X");
                    selecionador = 1;
                }
            } else {
                if (b[0].getText().equals(" ")) {
                    b[0].setText("0");
                    selecionador = 0;
                }
            }
        } else if (ae.getSource().equals(b[1])) {
            if (selecionador == 0) {
                if (b[1].getText().equals(" ")) {
                    b[1].setText("X");
                    selecionador = 1;
                }
            } else {
                if (b[1].getText().equals(" ")) {
                    b[1].setText("0");
                    selecionador = 0;
                }
            }
        } else if (ae.getSource().equals(b[2])) {
            if (selecionador == 0) {
                if (b[2].getText().equals(" ")) {
                    b[2].setText("X");
                    selecionador = 1;
                }
            } else {
                if (b[2].getText().equals(" ")) {
                    b[2].setText("0");
                    selecionador = 0;
                }
            }
        } else if (ae.getSource().equals(b[3])) {
            if (selecionador == 0) {
                if (b[3].getText().equals(" ")) {
                    b[3].setText("X");
                    selecionador = 1;
                }
            } else {
                if (b[3].getText().equals(" ")) {
                    b[3].setText("0");
                    selecionador = 0;
                }
            }
        } else if (ae.getSource().equals(b[4])) {
            if (selecionador == 0) {
                if (b[4].getText().equals(" ")) {
                    b[4].setText("X");
                    selecionador = 1;
                }
            } else {
                if (b[4].getText().equals(" ")) {
                    b[4].setText("0");
                    selecionador = 0;
                }
            }
        } else if (ae.getSource().equals(b[5])) {
            if (selecionador == 0) {
                if (b[5].getText().equals(" ")) {
                    b[5].setText("X");
                    selecionador = 1;
                }
            } else {
                if (b[5].getText().equals(" ")) {
                    b[5].setText("0");
                    selecionador = 0;
                }
            }
        } else if (ae.getSource().equals(b[6])) {
            if (selecionador == 0) {
                if (b[6].getText().equals(" ")) {
                    b[6].setText("X");
                    selecionador = 1;
                }
            } else {
                if (b[6].getText().equals(" ")) {
                    b[6].setText("0");
                    selecionador = 0;
                }
            }
        } else if (ae.getSource().equals(b[7])) {
            if (selecionador == 0) {
                if (b[7].getText().equals(" ")) {
                    b[7].setText("X");
                    selecionador = 1;
                }
            } else {
                if (b[7].getText().equals(" ")) {
                    b[7].setText("0");
                    selecionador = 0;
                }
            }
        } else if (ae.getSource().equals(b[8])) {
            if (selecionador == 0) {
                if (b[8].getText().equals(" ")) {
                    b[8].setText("X");
                    selecionador = 1;
                }
            } else {
                if (b[8].getText().equals(" ")) {
                    b[8].setText("0");
                    selecionador = 0;
                }
            }
        }
        if(verificarVencedor()){
            fClear();
        }

    }

    private boolean verificarVencedor() {
        if(b[0].getText() == b[1].getText() && b[0].getText()== b[2].getText() && b[0].getText()!=" "){
            if(b[1].getText().equals("X")){
                showMessageDialog(null, "O jogador X ganhou!");
                return true;
            }else{
                showMessageDialog(null, "O jogador O ganhou!");
                return true;
            }
        }else if(b[3].getText() == (b[4].getText()) && b[3].getText() == b[5].getText() && b[3].getText()!=" "){
            if(b[4].getText().equals("X")){
                showMessageDialog(null, "O jogador X ganhou!");
                return true;
            }else{
                showMessageDialog(null, "O jogador O ganhou!");
                return true;
            }
        }else if(b[6].getText() == b[7].getText() && b[6].getText() == b[8].getText() && b[6].getText()!=" "){
            if(b[7].getText().equals("X")){
                showMessageDialog(null, "O jogador X ganhou!");
                return true;
            }else{
                showMessageDialog(null, "O jogador O ganhou!");
                return true;
            }
        }else if(b[0].getText() == b[3].getText() && b[0].getText() == b[6].getText() && b[0].getText()!=" "){
            if(b[3].getText().equals("X")){
                showMessageDialog(null, "O jogador X ganhou!");
                return true;
            }else{
                showMessageDialog(null, "O jogador O ganhou!");
                return true;
            }
        }else if(b[1].getText() == b[4].getText() && b[1].getText() == b[7].getText() && b[1].getText()!=" "){
            if(b[4].getText().equals("X")){
                showMessageDialog(null, "O jogador X ganhou!");
                return true;
            }else{
                showMessageDialog(null, "O jogador O ganhou!");
                return true;
            }
        }else if(b[2].getText() == b[5].getText() && b[2].getText() == b[8].getText() && b[2].getText()!=" "){
            if(b[5].getText().equals("X")){
                showMessageDialog(null, "O jogador X ganhou!");
                return true;
            }else{
                showMessageDialog(null, "O jogador O ganhou!");
                return true;
            }
        }else if(b[0].getText() == b[4].getText() && b[0].getText() == b[8].getText() && b[0].getText()!=" "){
            if(b[4].getText().equals("X")){
                showMessageDialog(null, "O jogador X ganhou!");
                return true;
            }else{
                showMessageDialog(null, "O jogador O ganhou!");
                return true;
            }
        }else if(b[2].getText() == b[4].getText() && b[2].getText() == b[6].getText() && b[2].getText()!=" "){
            if(b[4].getText().equals("X")){
                showMessageDialog(null, "O jogador X ganhou!");
                return true;
            }else{
                showMessageDialog(null, "O jogador O ganhou!");
                return true;
            }
        }else{
            if(completo()){
                showMessageDialog(null, "Deu velha!");
                return true;
            }
            return false;
        }
    }

    private void fClear() {
        for(JButton bot:b){
            bot.setText(" ");
        }
    }

    private boolean completo() {
        for(JButton bot:b){
            if(bot.getText()==" "){
                return false;
            }
        }
        return true;
    }

}
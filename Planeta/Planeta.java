import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showMessageDialog;

public class Planeta extends JFrame implements ActionListener {

    private JPanel painel;
    private JLabel pesoTerra;
    private JLabel selPlaneta;
    private JButton calcularPeso;
    private JTextField pesoKg;
    private ButtonGroup grupoBut;
    private JRadioButton mercurio;
    private JRadioButton marte;
    private JRadioButton saturno;
    private JRadioButton venus;
    private JRadioButton jupiter;
    private JRadioButton urano;
    public double gravidadeRelativa;

    public Planeta() {
        super("Planeta <3");
        painel = new JPanel();
        pesoTerra = new JLabel("Peso na Terra (kg):");
        selPlaneta = new JLabel("Selecione o Planeta:");
        calcularPeso = new JButton("Calcular peso");
        pesoKg = new JTextField();
        mercurio = new JRadioButton("Mercúrio");
        marte = new JRadioButton("Marte");
        saturno = new JRadioButton("Saturno");
        venus = new JRadioButton("Vênus");
        jupiter = new JRadioButton("Júpiter");
        urano = new JRadioButton("Urano");

        grupoBut = new ButtonGroup();
        grupoBut.add(mercurio);
        grupoBut.add(marte);
        grupoBut.add(saturno);
        grupoBut.add(venus);
        grupoBut.add(jupiter);
        grupoBut.add(urano);

        painel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);

        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        painel.add(pesoTerra, c);

        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        painel.add(pesoKg, c);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 1;
        painel.add(calcularPeso, c);

        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 2;
        painel.add(selPlaneta, c);

        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 3;
        mercurio.setSelected(true);
        painel.add(mercurio, c);

        c.gridx = 1;
        c.gridy = 3;
        painel.add(marte, c);
        c.gridx = 2;
        c.gridy = 3;
        painel.add(saturno, c);

        c.insets = new Insets(0, 5, 0, 0);

        c.gridx = 0;
        c.gridy = 4;
        painel.add(venus, c);
        c.gridx = 1;
        c.gridy = 4;
        painel.add(jupiter, c);
        c.gridx = 2;
        c.gridy = 4;
        painel.add(urano, c);

        calcularPeso.addActionListener(this);

        this.setContentPane(painel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(400, 250);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gravidadeRelativa = 0;
        JRadioButton radioBut = new JRadioButton();

        if (e.getSource().equals(calcularPeso) && pesoKg.getText().matches("[0-9]+")) {
            if (mercurio.isSelected()) {
                gravidadeRelativa = 0.37;
                radioBut = mercurio;
            } else if (marte.isSelected()) {
                gravidadeRelativa = 0.38;
                radioBut = marte;
            } else if (saturno.isSelected()) {
                gravidadeRelativa = 1.15;
                radioBut = saturno;
            } else if (venus.isSelected()) {
                gravidadeRelativa = 0.88;
                radioBut = venus;
            } else if (jupiter.isSelected()) {
                gravidadeRelativa = 2.64;
                radioBut = jupiter;
            } else if (urano.isSelected()) {
                gravidadeRelativa = 1.17;
                radioBut = urano;
            }

            double planeta = (Double.valueOf(pesoKg.getText())) * gravidadeRelativa;
            showMessageDialog(null, "O seu peso em " + radioBut.getText() + " é: " + planeta + " kg");
        }

    }
}

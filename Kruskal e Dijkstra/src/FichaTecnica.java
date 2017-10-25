
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * @author Daniel e Iuri
 */
public class FichaTecnica extends JPanel {

    private GridBagConstraints constraints;
    private JLabel name, author, email, complexity;
    private JTextArea description;

    public FichaTecnica() {
        super();
        JLabel l;

        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
        constraints.weightx = 1.0;
        constraints.weightx = 1.0;

        /* titulo da caixa*/
        l = new JLabel("Ficha Técnica");
        l.setFont(new Font(null, Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        add(l, constraints);
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.WEST;

        /* nome do algoritmo */
        l = new JLabel("Algoritmo:");
        l.setFont(new Font(null, Font.BOLD, 12));
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(l, constraints);

        name = new JLabel("");
        name.setFont(new Font(null, Font.PLAIN, 12));
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(name, constraints);

        /* complexidade do algoritmo */
        l = new JLabel("Complexid.:");
        l.setFont(new Font(null, Font.BOLD, 12));
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(l, constraints);

        complexity = new JLabel("");
        complexity.setFont(new Font(null, Font.PLAIN, 12));
        constraints.gridx = 1;
        constraints.gridy = 2;
        add(complexity, constraints);

        /* complexidade do algoritmo */
        l = new JLabel("Descrição:");
        l.setFont(new Font(null, Font.BOLD, 12));
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(l, constraints);

        description = new JTextArea("");
        description.setFont(new Font(null, Font.PLAIN, 12));
        description.setLineWrap(true);
        description.setBackground(this.getBackground());
        description.setEditable(false);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 1;
        constraints.gridy = 3;
        add(description, constraints);

        /* autor do algoritmo */
        l = new JLabel("Autor(es):");
        l.setFont(new Font(null, Font.BOLD, 12));
        constraints.gridx = 0;
        constraints.gridy = 4;
        add(l, constraints);

        author = new JLabel("");
        author.setFont(new Font(null, Font.PLAIN, 12));
        constraints.gridx = 1;
        constraints.gridy = 4;
        add(author, constraints);

        /* email do autor */
        l = new JLabel("E-mail(s):");
        l.setFont(new Font(null, Font.BOLD, 12));
        constraints.gridx = 0;
        constraints.gridy = 5;
        add(l, constraints);

        email = new JLabel("");
        email.setFont(new Font(null, Font.PLAIN, 12));
        constraints.gridx = 1;
        constraints.gridy = 5;
        add(email, constraints);
    }

    public void setAlgoritmo(Algoritmo a) {
        if (a == null) {
            this.setAuthor(new String(""));
            this.setEmail(new String(""));
            this.setComplexity(new String(""));
            this.setDescription(new String(""));
            this.setName(new String(""));
            this.repaint();
            return;

        }
        this.setAuthor(a.getAuthor());
        this.setEmail(a.getAuthorEmail());
        this.setComplexity(a.getAlgorithmComplexity());
        this.setDescription(a.getAlgorithmDescription());
        this.setName(a.getAlgorithmName());
        this.repaint();
    }

    public void setAuthor(String s) {
        author.setText(s);
    }

    public void setName(String s) {
        name.setText(s);
    }

    public void setEmail(String s) {
        email.setText(s);
    }

    public void setComplexity(String s) {
        complexity.setText(s);
    }

    public void setDescription(String s) {
        description.setText(s);
    }

}

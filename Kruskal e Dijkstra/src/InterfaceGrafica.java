import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.imageio.*;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.event.MouseInputListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class InterfaceGrafica implements MouseInputListener, ActionListener, GraphListener {

    private Graph2D g2d, g2doriginal;
    private Graph g;
    private JFrame f;
    private JMenu menu;
    private JToolBar toolbar;
    private JTextPane messageArea;
    private FichaTecnica algoritmoInfoArea;
    private ComponentInfoPanel componenteInfoArea;
    private JSplitPane painelEsquerdo;
    private JSplitPane painelDireito;
    private int contPassos;
    private Algoritmo algo;

    private Vector pesoArestas, corArestas, labelArestas, corNos;

    public InterfaceGrafica() {

        f = new JFrame("Grafinho");

        algo = null;
        g2d = new Graph2D();

        contPassos = 0;

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.setSize(800, 600);

        /* Menu */
        f.setJMenuBar(new JMenuBar());
        createMenus(f.getJMenuBar());

        /* ToolBar*/
        toolbar = new JToolBar();
        createToolbars();

        /*
         * AREA DE TEXTO ABAIXO DO GRAFO
         */
        /* cria textarea com ANTI-ALIASING*/
        messageArea = new JTextPane() {
            public void paintComponent(Graphics g) {
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                super.paintComponent(g);
            }
        };
        messageArea.setEditable(false);
        messageArea.setMinimumSize(new Dimension(550, 150));
        messageArea.setPreferredSize(messageArea.getMinimumSize());

        /*
         * GRAPH2D
         */
        g2d.setMinimumSize(new Dimension(550, 350));
        g2d.setPreferredSize(g2d.getMinimumSize());

        /*
         * Area de informacoes sobre o algoritimo 
         * 
         */
        algoritmoInfoArea = new FichaTecnica();
        algoritmoInfoArea.setMinimumSize(new Dimension(250, 150));
        algoritmoInfoArea.setPreferredSize(algoritmoInfoArea.getMinimumSize());

        /*
         * Area de informacoes sobre os nos/arestas
         * 
         */
        componenteInfoArea = new ComponentInfoPanel();
        componenteInfoArea.setMinimumSize(new Dimension(250, 350));
        componenteInfoArea.setPreferredSize(componenteInfoArea.getMinimumSize());

        /*
         * ADICIONA OS COMPONENTES AO FRAME
         */
        /* 2 Panel's principais:
         * 	Esquerda(max area) - SplitPane: 
         * 		g2d (max area | CIMA) 
         *      messageArea (ABAIXO) 
         *  Direita: 
         *      componenteInfoArea (max area |CIMA) 
         *      algoritmoInfoArea (ABAIXO)
         */
        /* Esquerda (Split) */
        painelEsquerdo = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        painelEsquerdo.setMinimumSize(new Dimension(550, 500));
        painelEsquerdo.setPreferredSize(painelEsquerdo.getMinimumSize());
        painelEsquerdo.setResizeWeight(1);
        painelEsquerdo.setOneTouchExpandable(true);

        /* Componentes da esquerda */
        painelEsquerdo.setTopComponent(g2d);
        JScrollPane scroll = new JScrollPane(messageArea);
        scroll.setMinimumSize(messageArea.getMinimumSize());
        painelEsquerdo.setBottomComponent(scroll);
        painelEsquerdo.setDividerLocation(0.8);

        /* Direita (Panel) */
        painelDireito = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        painelDireito.setMinimumSize(new Dimension(250, 500));
        painelDireito.setPreferredSize(painelDireito.getMinimumSize());

        /* Componentes da direita*/
        painelDireito.setTopComponent(componenteInfoArea);
        painelDireito.setBottomComponent(algoritmoInfoArea);
        painelEsquerdo.setDividerLocation(0.8);
        painelDireito.setResizeWeight(1);
        painelDireito.setOneTouchExpandable(true);

        /* 
         * COLOCAR TUDO NO FRAME
         * 
         */
        JSplitPane tudo = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        tudo.setOneTouchExpandable(true);
        tudo.setResizeWeight(1);
        tudo.setLeftComponent(painelEsquerdo);
        tudo.setRightComponent(painelDireito);
        f.getContentPane().setLayout(new BorderLayout());
        f.getContentPane().add(toolbar, BorderLayout.PAGE_START);
        f.getContentPane().add(tudo, BorderLayout.CENTER);

        /* 
         * LISTENERS
         */
        registerGraph2D();

        /* mostrar frame */
        f.setVisible(true);
    }

    public void setAlgoritmo(Algoritmo a) {
        algo = a;
        String[] att = a.getEdgeAttributes();

        HashMap m = new HashMap();
        for (int i = 0; att != null && i < att.length; i++) {
            m.put(att[i].toLowerCase(), new Double(a.getDefaultValueAttribute(att[i])));
        }
        g2d.setDefaultAtributes(m);
        g2d.setNodeTypes(a.getNodeTypes());

        Iterator i = g2d.edgesArray.iterator();
        while (i.hasNext()) {
            Edge2D e = (Edge2D) i.next();
            for (int j = 0; att != null && j < att.length; j++) {
                if (!e.hasAttribute(att[j])) {
                    e.setAttribute(att[j], a.getDefaultValueAttribute(att[j]));
                }
            }
            e.setVisibleAttributes(att);
            e.recreatePopUpItems();
        }

        i = g2d.nodesArray.iterator();
        while (i.hasNext()) {
            Node2D n = (Node2D) i.next();
            n.setNodeTypes(a.getNodeTypes());
            n.recreatePopUpItems();
        }

        g2d.setVisibleAttribute(a.getVisibleAttribute());
        g2d.repaint();

        algoritmoInfoArea.setAlgoritmo(a);
    }

    private void createMenus(JMenuBar m) {
        JMenuItem item;
        JMenu submenu;

        menu = new JMenu("Grafo");
        menu.setMnemonic(KeyEvent.VK_G);

        submenu = new JMenu("Novo");
        submenu.setMnemonic(KeyEvent.VK_G);
        submenu.addActionListener(this);

        item = new JMenuItem("Grafo Direcionado", KeyEvent.VK_A);
        item.addActionListener(this);
        submenu.add(item);

        item = new JMenuItem("Grafo Não-Direcionado", KeyEvent.VK_A);
        item.addActionListener(this);
        submenu.add(item);
        menu.add(submenu);

        item = new JMenuItem("Abrir", KeyEvent.VK_A);
        item.addActionListener(this);
        menu.add(item);

        item = new JMenuItem("Salvar", KeyEvent.VK_S);
        item.addActionListener(this);
        menu.add(item);

        item = new JMenuItem("Exportar Imagem", KeyEvent.VK_S);
        item.addActionListener(this);
        menu.add(item);

        menu.addSeparator();
        item = new JMenuItem("Sair", KeyEvent.VK_A);
        item.addActionListener(this);
        menu.add(item);
        m.add(menu);

        menu = new JMenu("Algoritmos");
        menu.setMnemonic(KeyEvent.VK_A);

        submenu = new JMenu("Árvore Geradora Mínima");
        submenu.setMnemonic(KeyEvent.VK_A);
        submenu.addActionListener(this);
        

        item = new JMenuItem("Algoritmo de Prim", KeyEvent.VK_P);
        item.addActionListener(this);
        submenu.add(item);
        menu.add(submenu);
        
        item = new JMenuItem("Algoritmo de Kruskal", KeyEvent.VK_K);
        item.addActionListener(this);
        submenu.add(item);
        menu.add(submenu);
       
   
        submenu = new JMenu("Caminho Mínimo");
        submenu.setMnemonic(KeyEvent.VK_A);
        submenu.addActionListener(this);

        
        item = new JMenuItem("Algoritmo de Dijkstra", KeyEvent.VK_K);
        item.addActionListener(this);
        submenu.add(item);
        menu.add(submenu);
        
        
        m.add(menu);

        menu = new JMenu("Execução");
        menu.setMnemonic(KeyEvent.VK_E);

        item = new JMenuItem("Executar", KeyEvent.VK_R);
        item.addActionListener(this);
        menu.add(item);

        item = new JMenuItem("Passo a Passo", KeyEvent.VK_P);
        item.addActionListener(this);
        menu.add(item);

        menu.addSeparator();
        item = new JMenuItem("Reset", KeyEvent.VK_T);
        item.addActionListener(this);
        menu.add(item);
        m.add(menu);
    }

    public void createToolbars() {
        JButton botao;
        botao = new JButton("Executar");
        botao.addActionListener(this);
        toolbar.add(botao);

        botao = new JButton("Passo a Passo");
        botao.addActionListener(this);
        toolbar.add(botao);

        botao = new JButton("Reset");
        botao.addActionListener(this);
        toolbar.add(botao);
    }

    public synchronized void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        System.out.println(comando);
        /* algoritmos */
        if (comando.equals("Algoritmo de Prim")) {
            setAlgoritmo(new Prim());
        } else if (comando.equals("Algoritmo de Kruskal")) {
            setAlgoritmo(new Kruskal());
        } else if (comando.equals("Algoritmo de Dijkstra")){
            setAlgoritmo(new Dijkstra());
        }
        /* ALTERAR: ADICIONAR SEU ALGORITMO COMO UM ELSE ACIMA */

        if (comando.equals("Executar")) {
            runAlgorithm();
        } else if (comando.equals("Passo a Passo")) {
            runNextStepAlgorithm();
        } else if (comando.equals("Reset")) {
            resetAlgorithm();
        } else if (comando.equals("Grafo Direcionado")) {
            newDirectedGraph();
        } else if (comando.equals("Grafo Não-Direcionado")) {
            newUndirectedGraph();
        } else if (comando.equals("Abrir")) {
            openGraph();
        } else if (comando.equals("Salvar")) {
            saveGraph();
        } else if (comando.equals("Exportar Imagem")) {
            /* exporta imagem para arquivo */
            BufferedImage i = new BufferedImage(g2d.getWidth(), g2d.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2d = i.createGraphics();
            g2d.paint(graphics2d);
            graphics2d.dispose();

            JFileChooser fc = new JFileChooser();
            int escolha = fc.showSaveDialog(f);
            if (escolha == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                try {
                    ImageIO.write(i, "jpg", file);
                } catch (Exception ex) {
                    System.err.println("Erro ao salvar!");
                }
            }
        } else if (comando.equals("Sair")) {
            /* Voce deseja salvar? */
            System.exit(0);
        }

    }

    /**
     * Roda o algoritmo selecionado
     */
    private void runAlgorithm() {
        if (algo == null) {
            this.setErrorEvent(new ErrorEvent(this, "Nenhum algoritmo selecionado"));
            return;
        }

        if (g == null) {
            g2d.makeCopy();
            g = g2d.generateGraph();
            g.addGraphListener(this);

            painelEsquerdo.removeMouseListener(g2d);
            painelEsquerdo.removeMouseMotionListener(g2d);
            g2d.unregisterMouse();

            algo.setGraph(g);
            if (algo.validateGraph() == false) {
                this.setErrorEvent(new ErrorEvent(this, "O grafo não é valido para o algoritmo selecionado"));
                return;
            }
            algo.execute();
        } else {
            this.setErrorEvent(new ErrorEvent(this, "O algoritmo foi finalizado ou já foi inicializado. Selecione \"Reset\" para iniciar novamente"));
        }
    }

    /**
     * Roda o próximo passo do algoritmo
     */
    private void runNextStepAlgorithm() {
        if (algo == null) {
            this.setErrorEvent(new ErrorEvent(this, "Nenhum algoritmo selecionado"));
            return;
        }

        if (contPassos == 0 && g == null) {
            g2d.makeCopy();
            g = g2d.generateGraph();
            g.addGraphListener(this);

            algo.setGraph(g);
            if (algo.validateGraph() == false) {
                this.setErrorEvent(new ErrorEvent(this, "O grafo não é valido para o algoritmo selecionado"));
                return;
            }
            algo.startStep();

            painelEsquerdo.removeMouseListener(g2d);
            painelEsquerdo.removeMouseMotionListener(g2d);
            g2d.unregisterMouse();
        }

        if (algo.hasNextStep()) {
            algo.nextStep();
        } else {
            this.setErrorEvent(new ErrorEvent(this, "O algoritmo foi finalizado. Selecione \"Reset\" para iniciar novamente"));
        }
        contPassos++;
    }

    private void resetAlgorithm() {
        if (g != null) {
            g = null;
            g2d.registerMouse();
            g2d.restoreCopy();
            painelEsquerdo.addMouseListener(g2d);
            painelEsquerdo.addMouseMotionListener(g2d);
            contPassos = 0;
        }
    }

    /**
     * Cria um novo grafo direcionado
     */
    private void newDirectedGraph() {

        unregisterGraph2D();

        g = null;
        g2d = new DirectedGraph2D();
        g2d.setMinimumSize(new Dimension(500, 400));
        g2d.setPreferredSize(g2d.getMinimumSize());

        algo = null;
        algoritmoInfoArea.setAlgoritmo(null);

        registerGraph2D();
    }

    /**
     * Cria um novo grafo nao direcionado
     */
    private void newUndirectedGraph() {

        unregisterGraph2D();

        g = null;
        g2d = new Graph2D();
        g2d.setMinimumSize(new Dimension(500, 400));
        g2d.setPreferredSize(g2d.getMinimumSize());

        algo = null;
        algoritmoInfoArea.setAlgoritmo(null);

        registerGraph2D();

    }

    private void unregisterGraph2D() {
        painelEsquerdo.remove(g2d);
        painelEsquerdo.removeMouseListener(g2d);
        painelEsquerdo.removeMouseMotionListener(g2d);
		//g2d.setEdge2DMouseInputListener(null);
        //g2d.setNode2DMouseInputListener(null);
        g2d.unregisterMouse();
    }

    private void registerGraph2D() {
        /* Listeners */
        g2d.setEdge2DMouseInputListener(componenteInfoArea);
        g2d.setNode2DMouseInputListener(componenteInfoArea);

        painelEsquerdo.setTopComponent(g2d);
        painelEsquerdo.addMouseListener(g2d);
        painelEsquerdo.addMouseMotionListener(g2d);
        painelEsquerdo.validate();

    }

    /**
     * Abre uma janela e salva um arquivo de um grafo
     */
    private void saveGraph() {
        JFileChooser fc = new JFileChooser();
        int escolha = fc.showSaveDialog(f);
        if (escolha == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            FileWriter fr = null;
            try {
                fr = new FileWriter(file.toString());
                saveToFile(fr);
            } catch (Exception ex) {
                System.out.println("InterfaceGrafica.openFile(): " + ex.toString());
            }
        }
    }

    /**
     * Abre uma janela e abre um arquivo de um grafo
     */
    private void openGraph() {
        JFileChooser fc = new JFileChooser();
        int escolha = fc.showOpenDialog(f);
        if (escolha == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            FileReader fr = null;
            try {
                fr = new FileReader(file.toString());
                readFromFile(fr);
                g = null;
            } catch (Exception ex) {
                System.out.println("InterfaceGrafica.openFile(): " + ex.toString());
            }
        }
    }

    public void saveToFile(FileWriter fr) {
        BufferedWriter buf = new BufferedWriter(fr);
        try {
            if (g2d instanceof DirectedGraph2D) {
                buf.write("DIR\n");
            } else {
                buf.write("NDIR\n");
            }

            buf.write(Integer.toString(g2d.getNumNodes()) + "\n");
            for (int i = 0; i < g2d.getNumNodes(); i++) {
                buf.write(g2d.getNode2D(i).getX() + " " + g2d.getNode2D(i).getY() + "\n");
            }

            buf.write(Integer.toString(g2d.getNumEdges()) + "\n");
            for (int i = 0; i < g2d.getNumEdges(); i++) {
                buf.write(g2d.getEdge2D(i).getFrom().getId() + " "
                        + g2d.getEdge2D(i).getTo().getId() + "\n");
                String[] att = algo.getEdgeAttributes();
                buf.write(att.length + "\n");
                for (int j = 0; j < att.length; j++) {
                    buf.write(att[0] + " " + g2d.getEdge2D(i).getAttribute(att[0]) + "\n");
                }
            }
            buf.close();
        } catch (IOException e) {
            System.out.println("InterfaceGrafica.readFromFile() : " + e.toString());
        }
    }

    public void readFromFile(FileReader fr) {
        BufferedReader buf = new BufferedReader(fr);
        try {
            /* remove o grafo antigo e cria o novo */
            unregisterGraph2D();

            /* le o tipo do grafo */
            String line = buf.readLine();

            if (line.equals("DIR")) {
                g2d = new DirectedGraph2D();
            } else {
                g2d = new Graph2D();
            }

            g2d.setEdge2DMouseInputListener(componenteInfoArea);
            g2d.setNode2DMouseInputListener(componenteInfoArea);

            /* le o número de nós */
            line = buf.readLine();
            int numNodes = Integer.parseInt(line);
            /* le os nós e suas respectivas posições */
            int i = 0, j;
            for (i = 0; i < numNodes; i++) {
                line = buf.readLine();
                StringTokenizer str = new StringTokenizer(line);
                /* pega coordenada x */
                String token = str.nextToken();
                int xNode = Integer.parseInt(token);

                /* pega coordenada y */
                token = str.nextToken();
                int yNode = Integer.parseInt(token);
                g2d.addNode2D(new Node2D(i, xNode, yNode));
            }

            /* le as arestas e suas respectivas posições */
            line = buf.readLine();
            int numEdges = Integer.parseInt(line);
            /* le as arestas */
            i = 0;
            for (i = 0; i < numEdges; i++) {
                line = buf.readLine();
                StringTokenizer str = new StringTokenizer(line);
                /* pega nó de origem */
                String token = str.nextToken();
                int fromNode = Integer.parseInt(token);

                /* pega nó de destino */
                token = str.nextToken();
                int toNode = Integer.parseInt(token);

                /* pega o nome dos atributos das arestas */
                if (g2d instanceof DirectedGraph2D) {
                    ((DirectedGraph2D) (g2d)).addDirectedEdge2D(new DirectedEdge2D(g2d.getNode2D(fromNode), g2d.getNode2D(toNode)));
                } else {
                    g2d.addEdge2D(new Edge2D(g2d.getNode2D(fromNode), g2d.getNode2D(toNode)));
                }

                int numAtts = Integer.parseInt(buf.readLine());
                for (int att = 0; att < numAtts; att++) {
                    line = buf.readLine();
                    str = new StringTokenizer(line);
                    String attName = str.nextToken();
                    double attValue = Double.parseDouble(str.nextToken());
                    g2d.getEdge2D(i).setAttribute(attName, attValue);
                }
            }

            registerGraph2D();
        } catch (IOException e) {
            System.out.println("InterfaceGrafica.readFromFile() : " + e.toString());
        }

    }

    public void setMessageEvent(MessageEvent e) {
        try {
            SimpleAttributeSet estilo = new SimpleAttributeSet();
            StyleConstants.setBold(estilo, true);
            StyleConstants.setFontFamily(estilo, "Lucida Sans");
            StyleConstants.setForeground(estilo, Color.BLUE);
            StyleConstants.setFontSize(estilo, 15);
            //StyleConstants.
            messageArea.getDocument().insertString(messageArea.getDocument().getLength(), e.getMessage() + "\n", estilo);
        } catch (Exception ex) {
            System.err.println("InterfaceGrafica.setMessageEvent: " + ex.toString());
        }
    }

    public void setErrorEvent(ErrorEvent e) {
        try {
            SimpleAttributeSet estilo = new SimpleAttributeSet();
            StyleConstants.setBold(estilo, true);
            StyleConstants.setFontFamily(estilo, "Lucida Sans");
            StyleConstants.setForeground(estilo, Color.RED);
            StyleConstants.setFontSize(estilo, 15);
            //StyleConstants.
            messageArea.getDocument().insertString(messageArea.getDocument().getLength(), e.getMessage() + "\n", estilo);
        } catch (Exception ex) {
            System.err.println("InterfaceGrafica.setMessageEvent: " + ex.toString());
        }
    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseClicked(MouseEvent arg0) {

    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() instanceof Edge2D) {
            Edge2D edge2d = (Edge2D) e.getSource();
            //messageArea.getText().
            try {
                SimpleAttributeSet estilo = new SimpleAttributeSet();
                StyleConstants.setBold(estilo, true);
                StyleConstants.setFontFamily(estilo, "Lucida Sans");
                StyleConstants.setForeground(estilo, Color.RED);
                StyleConstants.setFontSize(estilo, 15);
                //StyleConstants.
                //componenteInfoArea.getDocument().insertString(componenteInfoArea.getDocument().getLength(), edge2d.getColor() +"\n", estilo);
            } catch (Exception ex) {
                System.err.println("InterfaceGrafica.setMessageEvent: " + ex.toString());
            }
        }
    }

    public void mouseExited(MouseEvent arg0) {
        //componenteInfoArea.setStyledDocument(new DefaultStyledDocument());
    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed(MouseEvent arg0) {

    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
     */
    public void mouseDragged(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
     */
    public void mouseMoved(MouseEvent arg0) {

    }

}

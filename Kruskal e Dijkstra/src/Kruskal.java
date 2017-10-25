
import java.awt.Color;


public class Kruskal implements Algoritmo{
    private Graph g;
    private Edge[] edges;
    private Node[] nodes;
    private Node[][] conj;
    private int sts1;
  
    
    @Override
    public void setGraph(Graph g){
        this.g=g;
        
        edges=g.getEdges();
        nodes=g.getNodes();
    }

    /**
     * Executa todo o algoritmo de uma só vez
     */
    @Override
    public void execute(){
        for (startStep(); hasNextStep(); nextStep());
    }

    /**
     * Inicializa o modo passo a passo
     */
    @Override
    public void startStep(){
        conj=new Node[nodes.length][nodes.length];
        sts1=0;
        
        for(int i = 0; i < nodes.length; i++){
            for(int j = 0; j < nodes.length; j++){
                conj[i][j]=null;
            }
            conj[i][0]=nodes[i];
        }
        
    }

    /**
     * Retorna true caso exista um próximo passo no algoritmo
     *
     * @return true caso ainda exista algum passo a ser executado no algoritmo,
     * false caso contrário
     */
    @Override
    public boolean hasNextStep(){
        if(sts1 == 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Executa um passo do algoritmo
     */
    @Override
    public void nextStep(){
        Edge aux;
        Node comutador;
        int aux_de=-1, aux_para=-1, sts=0;
        
        for(int i=0; i<edges.length; i++){
            for(int j=i+1; j<edges.length; j++){
                if(edges[i].getAttribute("Peso")>edges[j].getAttribute("Peso")){
                    aux=edges[j];
                    edges[j]=edges[i];
                    edges[i]=aux;
                }
            }
        }
        
        for(int i = 0; i < edges.length; i++){
            for(int j = 0; j < nodes.length; j++){
                for(int k = 0; k < nodes.length; k++){
                    if(edges[i].getFrom()==conj[j][k]){
                        aux_de=j;
                        for(int m = 0; m < nodes.length; m++){
                            for(int n = 0; n < nodes.length; n++){
                                if(edges[i].getTo() == conj[m][n]){
                                    aux_para=m;
                                }
                            }
                        }
                    }
                }
            }
            if(aux_de != aux_para){
                edges[i].setColor(Color.PINK);
                for(int l = 0; l < nodes.length; l++){
                    if(conj[aux_para][l]!=null){
                        comutador = conj[aux_para][l];
                        conj[aux_para][l]=null;
                        for(int j = 0; j < nodes.length; j++){
                            if(conj[aux_de][j] == null && sts == 0){
                                sts=1;
                                conj[aux_de][j]=comutador;
                            }
                        }
                        sts=0;
                    }
                }
            }
        }

        sts1=1;
        
    }

    /**
     * Constata a validade do grafo. Caso o grafo não seja válido, a mensagem de
     * erro deve ser escrita utilizando o método writeMessage() do grafo.
     *
     * @return true caso o grafo seja válido para esse algoritmo e false em caso
     * contrário
     * @see Graph
     */
    @Override
    public boolean validateGraph(){
       if (g.getNumNodes() <= 0) {
            return false;
        }
        if (g instanceof DirectedGraph) {
            return false;
        }
        return true;
    }

    /**
     * Retorna as propriedades da aresta no algoritmo, que serão editáveis pelo
     * usuário e que aparecerão no "pop-up" menu na interface gráfica. A
     * propriedade "Peso", que é utilizada na maioria dos algoritmos, <b>não</b>
     * é adicionada por padrão.
     *
     * @return Vetor de strings, com os atributos que serão utilizados na
     * aresta.
     */
    @Override
    public String[] getEdgeAttributes(){
        String[] res = new String[1];
        res[0] = new String("Peso");
        return res;
    }

    /**
     * Retorna os tipos de nós existentes no algoritmo. O nó normal
     * <b>existe</b> por padrão.
     *
     * @return Vetor de strings, com os tipos de nós existentes no grafo para o
     * algoritmo. Retornar null caso não hajam outros tipos de nó.
     */
    @Override
    public String[] getNodeTypes(){
        return null;
    }

    /**
     * Retorna o valor padrão que será colocado no atributo passado como
     * parâmetro.
     *
     * @param attribute Atributo da aresta cujo valor será padrão.
     * @return O valor padrão para aquele atributo (valor que será atribuído
     * caso o usuário não escolha nenhum valor).
     */
    @Override
    public double getDefaultValueAttribute(String attribute){
       return 1.0; 
    }

    /**
     * Retorna o nome do atributo que será exibido ao usuário no desenho do
     * grafo (atributo que será exibido na aresta). Normalmente é "peso".
     *
     * @return Um dos elementos de getEdgeAttributes(), cujo valor será exibido
     * ao lado da aresta no desenho do grafo.
     */
    @Override
    public String getVisibleAttribute(){
        return new String("Peso");
    }

    /**
     * @return Nome do autor da implementação do algoritmo
     */
    @Override
    public String getAuthor(){
        return "Rayssa Mesquita";
    }

    /**
     * @return Email do autor da implementação
     */
    @Override
    public String getAuthorEmail(){
        return "rayssaccmesquita@gmail.com";
    }

    /**
     * @return Nome do algoritmo que está sendo implementado
     */
    @Override
    public String getAlgorithmName(){
        return "Kruskal";
    }

    /**
     * @return Complexidade do algoritmo que está sendo implementado
     */
    @Override
    public String getAlgorithmComplexity(){
        return "O (m Log n)";
    }

    /**
     * @return Descrição do algoritmo
     */
    @Override
    public String getAlgorithmDescription(){
        return "Algoritmo que encontra a árvore geradora mínima do grafo utilizando o algoritmo de Kruskal.";
    }
}

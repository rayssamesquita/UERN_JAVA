
import java.awt.Color;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dona Bella
 */
public class Dijkstra implements Algoritmo{
    
    private Graph g;
    private Edge[] edges;
    private Node[] nodes;
    private double[][] tabela;
    private Edge[][] tabela_arestas;
    private Edge[] vetor_arestas;
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
        tabela = new double[nodes.length][edges.length];
        tabela_arestas = new Edge[nodes.length][edges.length];
        vetor_arestas = new Edge[edges.length];
        
        sts1 = 0;
        
        for(int i = 0; i < nodes.length; i++){
            for(int j = 0; j < edges.length; j++){
                tabela[i][j]=-5;
            }
            tabela[0][i]=-1;
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
        Node fim;
        Node vertice_atual=nodes[0];
        Node vertice_anterior;
        Edge aresta;
        int cont=0, contv=0, sts=0;
        double acumulador=0, comparador=-1;
        
        fim = nodes[nodes.length-1];
        aresta = edges[0];
        
        do{
            vertice_anterior=vertice_atual;
            for(int i=0; i<edges.length; i++){
                if(edges[i].getFrom() == vertice_atual){
                   if(tabela[edges[i].getTo().getId()][cont] == -5){
                       for(int k = cont; k < edges.length; k++){
                           tabela[edges[i].getTo().getId()][k] = edges[i].getAttribute("Peso")+acumulador;
                           tabela_arestas[edges[i].getTo().getId()][k] = edges[i];
                       }
                   }else if(tabela[edges[i].getTo().getId()][cont] >= acumulador+edges[i].getAttribute("Peso")){
                       for(int k = cont; k < edges.length; k++){
                           tabela[edges[i].getTo().getId()][k] = edges[i].getAttribute("Peso")+acumulador;
                           tabela_arestas[edges[i].getTo().getId()][k] = edges[i];
                       }
                   }
                }
            }
            
      
            for(int i = 0; i< nodes.length; i++){
                if(comparador==-1){
                    if(tabela[i][cont] > -1){
                        comparador=tabela[i][cont];
                        vertice_atual=nodes[i];
                        aresta=tabela_arestas[i][cont];
                    }
                }else{
                    if(comparador > tabela[i][cont] && tabela[i][cont] > -1){
                        comparador=tabela[i][cont];
                        vertice_atual=nodes[i];
                        aresta=tabela_arestas[i][cont];
                    }
                }
            }
            
            for(int i = cont+1; i < edges.length; i++){
                tabela[vertice_atual.getId()][i]=-1;
            }
            
            if(sts==0){
                vetor_arestas[contv]=aresta;
                sts=1;
            }else{

                if(aresta.getFrom() == vetor_arestas[contv].getTo()){
                    contv++;
                    vetor_arestas[contv]=aresta;
                }else if(aresta.getFrom() == vetor_arestas[contv].getFrom()){
                    vetor_arestas[contv]=aresta;
                }else{
                    for(int m = contv; m >= 0; m--){
                        if(vetor_arestas[m].getTo()==aresta.getFrom()){
                            contv++;
                            vetor_arestas[contv]=aresta;
                            break;
                        }else if(aresta.getFrom() == vetor_arestas[m].getFrom()){
                            vetor_arestas[contv]=aresta;
                            break;
                        }else{
                            cont--;
                        }
                    }
                }
            }
            acumulador=comparador;
            comparador=-1;
            
            cont++;
            System.out.println(" "+vertice_atual.getId()+" "+fim.getId()+" "+nodes.length);
        }while(vertice_atual!=fim);
        
        for(int i = 0; i<=contv; i++){
            for(int j = 0; j < edges.length; j++){
                if(vetor_arestas[i] == edges[j]){
                    edges[j].setColor(Color.PINK);
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
        return "Dijkstra";
    }

    /**
     * @return Complexidade do algoritmo que está sendo implementado
     */
    @Override
    public String getAlgorithmComplexity(){
        return "O(|E| + |V| Log |V|)";
    }

    /**
     * @return Descrição do algoritmo
     */
    @Override
    public String getAlgorithmDescription(){
        return "Algoritmo que encontra caminho mínimo entre o vértice 0 e todos os outros do grafo utilizando o algoritmo de Dijkstra.";
    }
}
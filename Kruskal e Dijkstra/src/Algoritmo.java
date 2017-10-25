
/**
 * Interface que deve ser implementada pelos algoritmos a serem executados.
 * Possui basicamente as funções de controle do algoritmo e de seu modo de
 * execução passo-a-passo.
 *
 * @author Daniel Rocha e Iuri Gomes
 */
public interface Algoritmo {

    /**
     * Seta o grafo que vai ser utilizado pelo algoritmo
     *
     * @param g Grafo sob o qual o algoritmo será executado
     */
    public void setGraph(Graph g);

    /**
     * Executa todo o algoritmo de uma só vez
     */
    public void execute();

    /**
     * Inicializa o modo passo a passo
     */
    public void startStep();

    /**
     * Retorna true caso exista um próximo passo no algoritmo
     *
     * @return true caso ainda exista algum passo a ser executado no algoritmo,
     * false caso contrário
     */
    public boolean hasNextStep();

    /**
     * Executa um passo do algoritmo
     */
    public void nextStep();

    /**
     * Constata a validade do grafo. Caso o grafo não seja válido, a mensagem de
     * erro deve ser escrita utilizando o método writeMessage() do grafo.
     *
     * @return true caso o grafo seja válido para esse algoritmo e false em caso
     * contrário
     * @see Graph
     */
    public boolean validateGraph();

    /**
     * Retorna as propriedades da aresta no algoritmo, que serão editáveis pelo
     * usuário e que aparecerão no "pop-up" menu na interface gráfica. A
     * propriedade "Peso", que é utilizada na maioria dos algoritmos, <b>não</b>
     * é adicionada por padrão.
     *
     * @return Vetor de strings, com os atributos que serão utilizados na
     * aresta.
     */
    public String[] getEdgeAttributes();

    /**
     * Retorna os tipos de nós existentes no algoritmo. O nó normal
     * <b>existe</b> por padrão.
     *
     * @return Vetor de strings, com os tipos de nós existentes no grafo para o
     * algoritmo. Retornar null caso não hajam outros tipos de nó.
     */
    public String[] getNodeTypes();

    /**
     * Retorna o valor padrão que será colocado no atributo passado como
     * parâmetro.
     *
     * @param attribute Atributo da aresta cujo valor será padrão.
     * @return O valor padrão para aquele atributo (valor que será atribuído
     * caso o usuário não escolha nenhum valor).
     */
    public double getDefaultValueAttribute(String attribute);

    /**
     * Retorna o nome do atributo que será exibido ao usuário no desenho do
     * grafo (atributo que será exibido na aresta). Normalmente é "peso".
     *
     * @return Um dos elementos de getEdgeAttributes(), cujo valor será exibido
     * ao lado da aresta no desenho do grafo.
     */
    public String getVisibleAttribute();

    /**
     * @return Nome do autor da implementação do algoritmo
     */
    public String getAuthor();

    /**
     * @return Email do autor da implementação
     */
    public String getAuthorEmail();

    /**
     * @return Nome do algoritmo que está sendo implementado
     */
    public String getAlgorithmName();

    /**
     * @return Complexidade do algoritmo que está sendo implementado
     */
    public String getAlgorithmComplexity();

    /**
     * @return Descrição do algoritmo
     */
    public String getAlgorithmDescription();
}

package br.com.jogodavelha.servidor;

import java.util.ArrayList;

/**
 * Created by '-Maxsuel and Rayssa on 07/05/2016.
 */
public class ListaJogadores {
    private ArrayList<Jogador> jogadores = new ArrayList<>();

    public ListaJogadores() {

    }

    public void add(Jogador jogador) {
        jogadores.add(jogador);
    }

    public Jogador buscarPorId(int id) {
        for (Jogador j : getJogadores()) {
            if (j.getId() == id) {
                return j;
            }
        }
        return null;
    }

    public void removerPorID(int id) {
        System.out.println(id + "SAIU");
        jogadores.remove(buscarPorId(id));
    }

    public ArrayList<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(ArrayList<Jogador> jogadores) {
        this.jogadores = jogadores;
    }
}

package br.com.binary_tree.model;

public class NoArvore {
    int chave;
    NoArvore esquerda;
    NoArvore direita;

    public NoArvore(int chave) {
        this.chave = chave;
        this.esquerda = null;
        this.direita = null;
    }

}

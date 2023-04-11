package br.com.binary_tree.model;

import java.lang.Math;

public class ArvoreBinariaPesquisa {
    private NoArvore raiz;

    public ArvoreBinariaPesquisa() {
        this.raiz = null;
    }

    public void inserir(int chave) {
        this.raiz = inserirRecursivo(this.raiz, chave);
    }

    private NoArvore inserirRecursivo(NoArvore no, int chave) {
        if (no == null) {
            return new NoArvore(chave);
        }

        if (chave < no.chave) {
            no.esquerda = inserirRecursivo(no.esquerda, chave);
        } else if (chave > no.chave) {
            no.direita = inserirRecursivo(no.direita, chave);
        }

        return no;
    }

    public void remover(int chave) {
        this.raiz = removerRecursivo(this.raiz, chave);
    }

    private NoArvore removerRecursivo(NoArvore no, int chave) {
        if (no == null) {
            return null;
        }

        if (chave == no.chave) {
            if (no.esquerda == null) {
                return no.direita;
            } else if (no.direita == null) {
                return no.esquerda;
            } else {
                NoArvore sucessor = buscarMenor(no.direita);
                no.chave = sucessor.chave;
                no.direita = removerRecursivo(no.direita, sucessor.chave);
            }
        } else if (chave < no.chave) {
            no.esquerda = removerRecursivo(no.esquerda, chave);
        } else {
            no.direita = removerRecursivo(no.direita, chave);
        }

        return no;
    }

    private NoArvore buscarMenor(NoArvore no) {
        NoArvore atual = no;

        while (atual.esquerda != null) {
            atual = atual.esquerda;
        }

        return atual;
    }

    public NoArvore buscar(int chave) {
        return buscarRecursivo(this.raiz, chave);
    }

    private NoArvore buscarRecursivo(NoArvore no, int chave) {
        if (no == null || no.chave == chave) {
            return no;
        }

        if (chave < no.chave) {
            return buscarRecursivo(no.esquerda, chave);
        } else {
            return buscarRecursivo(no.direita, chave);
        }
    }

    public void mostrar() {
        int altura = altura(this.raiz);
        int largura = (int) Math.pow(2, altura) - 1;
        int[][] matriz = new int[altura][largura];
        preencherMatriz(matriz, this.raiz, 0, 0, largura - 1);

        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                if (matriz[i][j] == 0) {
                    System.out.print("\t");
                } else {
                    System.out.print(matriz[i][j] + "\t");
                }
            }
            System.out.println("\n");
        }
        System.out.println(
                "_________________________________________________________________________________________________________________");

    }

    private void preencherMatriz(int[][] matriz, NoArvore no, int linha, int coluna, int largura) {
        if (no == null) {
            return;
        }

        int posicao = coluna + (largura / 2);
        matriz[linha][posicao] = no.chave;

        preencherMatriz(matriz, no.esquerda, linha + 1, coluna, largura / 2);
        preencherMatriz(matriz, no.direita, linha + 1, posicao + 1, largura / 2);
    }

    private int altura(NoArvore no) {
        if (no == null) {
            return 0;
        } else {
            int alturaEsquerda = altura(no.esquerda);
            int alturaDireita = altura(no.direita);
            return 1 + Math.max(alturaEsquerda, alturaDireita);
        }
    }

}

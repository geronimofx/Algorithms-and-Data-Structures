package br.com.avl_tree.model;

import java.lang.Math;

import br.com.binary_tree.model.ArvoreBinariaPesquisa;
import br.com.binary_tree.model.NoArvore;

public class ArvoreAVL extends ArvoreBinariaPesquisa {

    @Override
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

        no = balancear(no);

        return no;
    }

    @Override
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

        no = balancear(no);

        return no;
    }

    private NoArvore balancear(NoArvore no) {
        int fb = fatorBalanceamento(no);

        if (fb < -1) {
            if (altura(no.direita.esquerda) > altura(no.direita.direita)) {
                no.direita = rotacaoDireita(no.direita);
            }
            no = rotacaoEsquerda(no);
        } else if (fb > 1) {
            if (altura(no.esquerda.direita) > altura(no.esquerda.esquerda)) {
                no.esquerda = rotacaoEsquerda(no.esquerda);
            }
            no = rotacaoDireita(no);
        }

        return no;
    }

    private int fatorBalanceamento(NoArvore no) {
        if (no == null) {
            return 0;
        }

        return altura(no.esquerda) - altura(no.direita);
    }
}
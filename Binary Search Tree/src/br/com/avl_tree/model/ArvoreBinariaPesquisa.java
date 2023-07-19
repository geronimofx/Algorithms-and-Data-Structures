public class ArvoreAVL extends ArvoreBinariaPesquisa {

    public ArvoreAVL() {
        super();
    }

    public void inserir(int chave) {
        this.raiz = inserirRecursivo(this.raiz, chave);
    }

    private NoAVL inserirRecursivo(NoAVL no, int chave) {
        if (no == null) {
            return new NoAVL(chave);
        }

        if (chave < no.chave) {
            no.esquerda = inserirRecursivo(no.esquerda, chave);
        } else if (chave > no.chave) {
            no.direita = inserirRecursivo(no.direita, chave);
        } else {
            // Chave já existe, não faz nada
            return no;
        }

        no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));

        int fatorBalanceamento = calcularFatorBalanceamento(no);

        // Caso LL
        if (fatorBalanceamento > 1 && chave < no.esquerda.chave) {
            return rotacaoDireita(no);
        }

        // Caso RR
        if (fatorBalanceamento < -1 && chave > no.direita.chave) {
            return rotacaoEsquerda(no);
        }

        // Caso LR
        if (fatorBalanceamento > 1 && chave > no.esquerda.chave) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        // Caso RL
        if (fatorBalanceamento < -1 && chave < no.direita.chave) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    public void remover(int chave) {
        this.raiz = removerRecursivo(this.raiz, chave);
    }

    private NoAVL removerRecursivo(NoAVL no, int chave) {
        if (no == null) {
            return null;
        }

        if (chave == no.chave) {
            if (no.esquerda == null) {
                return no.direita;
            } else if (no.direita == null) {
                return no.esquerda;
            } else {
                NoAVL sucessor = buscarMenor(no.direita);
                no.chave = sucessor.chave;
                no.direita = removerRecursivo(no.direita, sucessor.chave);
            }
        } else if (chave < no.chave) {
            no.esquerda = removerRecursivo(no.esquerda, chave);
        } else {
            no.direita = removerRecursivo(no.direita, chave);
        }

        if (no == null) {
            return null;
        }

        no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));

        int fatorBalanceamento = calcularFatorBalanceamento(no);

        // Caso LL
        if (fatorBalanceamento > 1 && calcularFatorBalanceamento(no.esquerda) >= 0) {
            return rotacaoDireita(no);
        }

        // Caso RR
        if (fatorBalanceamento < -1 && calcularFatorBalanceamento(no.direita) <= 0) {
            return rotacaoEsquerda(no);
        }

        // Caso LR
        if (fatorBalanceamento > 1 && calcularFatorBalanceamento(no.esquerda) < 0) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        // Caso RL
        if (fatorBalanceamento < -1 && calcularFatorBalanceamento(no.direita) > 0) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }
        return no;
    }

}

    private NoAVL buscarMenor(NoAVL no) {
        NoAVL atual = no;
        while (atual.esquerda != null) {
            atual = atual.esquerda;
        }
        return atual;
    }

    private int altura(NoAVL no) {
        if (no == null) {
            return 0;
        }
        return no.altura;
    }

    private int calcularFatorBalanceamento(NoAVL no) {
        if (no == null) {
            return 0;
        }
        return altura(no.esquerda) - altura(no.direita);
    }

    private NoAVL rotacaoDireita(NoAVL no) {
        NoAVL esquerda = no.esquerda;
        NoAVL direitaEsquerda = esquerda.direita;
        esquerda.direita = no;
        no.esquerda = direitaEsquerda;
        no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));
        esquerda.altura = 1 + Math.max(altura(esquerda.esquerda), altura(esquerda.direita));
        return esquerda;
    }

private NoAVL rotacaoEsquerda(NoAVL no) {
    NoAVL direita = no.direita;
    NoAVL esquerdaDireita = direita.esquerda;
    direita.esquerda = no;
    no.direita = esquerdaDireita;
    no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));
    direita.altura = 1 + Math.max(altura(direita.esquerda), altura(direita.direita));
    return direita;
}

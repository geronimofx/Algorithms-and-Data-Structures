package br.com.binary_tree.model;

public class Main {
    public static void main(String[] args) {
        ArvoreBinariaPesquisa arvore = new ArvoreBinariaPesquisa();

        // Teste de inserção
        arvore.inserir(10);
        arvore.inserir(5);
        arvore.inserir(15);
        arvore.inserir(2);
        arvore.inserir(8);
        arvore.inserir(22);

        // Exibição da árvore
        arvore.mostrar();

        // Teste de remoção
        arvore.remover(5);

        // Exibição da árvore
        arvore.mostrar();

        // Teste de inserção após remoção
        arvore.inserir(25);

        // Exibição da árvore
        arvore.mostrar();

        // Buscando um elemento
        NoArvore noEncontrado = arvore.buscar(22);
        if (noEncontrado != null) {
            System.out.println("O nó com chave 22 foi encontrado!");
        } else {
            System.out.println("O nó com chave 22 não foi encontrado!");
        }

    }
}

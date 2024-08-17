
// Main.java
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int[] data = FileUtil.readArrayFromFile("dados100_mil.txt");

        if (data == null) {
            System.out.println("Erro ao ler o arquivo de dados.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Inserir dados na árvore Rubro-Negra");
            System.out.println("2 - Inserir dados na árvore AVL");
            System.out.println("3 - Encerrar");
            System.out.print("Digite sua escolha: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    RedBlackTree rbTree = new RedBlackTree();

                    long startTimeRB = System.currentTimeMillis();
                    for (int num : data) {
                        rbTree.insert(num);
                    }
                    long endTimeRB = System.currentTimeMillis();
                    System.out.println("Tempo de inserção na árvore Rubro-Negra: " + (endTimeRB - startTimeRB) + " ms");

                    processRandomNumbers(rbTree, "Rubro-Negra");
                    break;

                case 2:
                    AVLTree avlTree = new AVLTree();

                    long startTimeAVL = System.currentTimeMillis();
                    for (int num : data) {
                        avlTree.insert(num);
                    }
                    long endTimeAVL = System.currentTimeMillis();
                    System.out.println("Tempo de inserção na árvore AVL: " + (endTimeAVL - startTimeAVL) + " ms");

                    processRandomNumbers(avlTree, "AVL");
                    break;

                case 3:
                    System.out.println("Encerrando...");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        } while (choice != 3);

        scanner.close();
    }

    private static void processRandomNumbers(RedBlackTree rbTree, String treeType) {
        Random random = new Random();
        int countFound = 0;

        long startTimeProcess = System.currentTimeMillis();
        for (int i = 0; i < 50000; i++) {
            int randNum = random.nextInt(19999) - 9999;
            if (randNum % 3 == 0) {
                rbTree.insert(randNum);
            } else if (randNum % 5 == 0) {
                rbTree.remove(randNum);
            } else {
                if (rbTree.search(randNum))
                    countFound++;
            }
        }
        long endTimeProcess = System.currentTimeMillis();

        System.out.println("Tempo de processamento de números aleatórios para árvore " + treeType + ": "
                + (endTimeProcess - startTimeProcess) + " ms");
        System.out.println("Números encontrados na árvore " + treeType + ": " + countFound);
    }

    private static void processRandomNumbers(AVLTree avlTree, String treeType) {
        Random random = new Random();
        int countFound = 0;

        long startTimeProcess = System.currentTimeMillis();
        for (int i = 0; i < 50000; i++) {
            int randNum = random.nextInt(19999) - 9999;
            if (randNum % 3 == 0) {
                avlTree.insert(randNum);
            } else if (randNum % 5 == 0) {
                avlTree.remove(randNum);
            } else {
                if (avlTree.search(randNum))
                    countFound++;
            }
        }
        long endTimeProcess = System.currentTimeMillis();

        System.out.println("Tempo de processamento de números aleatórios para árvore " + treeType + ": "
                + (endTimeProcess - startTimeProcess) + " ms");
        System.out.println("Números encontrados na árvore " + treeType + ": " + countFound);
    }
}

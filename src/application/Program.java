// application/Program.java
package application;

import game.Game;
import exceptions.GameException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Game game = null;

        UI.clearScreen();
        System.out.println("--- BEM-VINDO À BATALHA NAVAL! ---");
        System.out.println("Escolha o modo de jogo:");
        System.out.println("1. Jogador vs. Computador");
        System.out.println("2. Jogador vs. Jogador");
        System.out.print("Opção: ");
        int choice = -1;
        try {
            choice = sc.nextInt();
            sc.nextLine(); // Consome a quebra de linha
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Escolha 1 ou 2.");
            sc.nextLine(); // Consome a entrada inválida
            UI.pressEnterToContinue(sc);
            System.exit(0); // Sai do programa
        }


        int boardSize = 10; // Tamanho do tabuleiro 10x10

        if (choice == 1) {
            game = new Game(boardSize, GameMode.PLAYER_VS_COMPUTER);
        } else if (choice == 2) {
            game = new Game(boardSize, GameMode.PLAYER_VS_PLAYER);
        } else {
            System.out.println("Opção inválida. Reinicie o jogo.");
            UI.pressEnterToContinue(sc);
            System.exit(0);
        }

        if (game != null) {
            game.startGame();
        }
        sc.close();
    }
}

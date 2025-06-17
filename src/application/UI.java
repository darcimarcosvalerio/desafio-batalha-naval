// application/UI.java
package application;

import board.Board;
import board.Position;
import board.Square;
import ships.Ship;

import java.util.Scanner;

public class UI {

    // Códigos ANSI para cores no terminal
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";


    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void pressEnterToContinue(Scanner sc) {
        System.out.println("\nPressione ENTER para continuar...");
        sc.nextLine();
    }

    // Imprime o tabuleiro de um jogador para configuração (mostra seus próprios navios)
    public static void printBoardSetup(Board board, String playerName, String currentShipName, int currentShipLength) {
        System.out.println("--- Tabuleiro de " + playerName + " ---");
        System.out.print("  ");
        for (int c = 0; c < board.getSize(); c++) {
            System.out.print(c + " ");
        }
        System.out.println();

        for (int r = 0; r < board.getSize(); r++) {
            System.out.print(r + " ");
            for (int c = 0; c < board.getSize(); c++) {
                Square square = board.getSquare(new Position(r, c));
                if (square.hasShip()) {
                    System.out.print(ANSI_GREEN + square.getShip().getSymbol() + ANSI_RESET + " ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
        System.out.println("\nPosicione seu " + currentShipName + " (tamanho " + currentShipLength + ").");
    }

    // Imprime os dois tabuleiros durante o jogo
    public static void printGameBoards(Board playerBoard, Board enemyBoardView, String playerName) {
        System.out.println("--- Seu Tabuleiro (" + playerName + ") ---");
        printPlayerBoard(playerBoard);
        System.out.println("\n--- Tabuleiro do Oponente (Visão de " + playerName + ") ---");
        printEnemyBoard(enemyBoardView);
    }

    // Imprime o tabuleiro do jogador (mostra navios, acertos e erros)
    private static void printPlayerBoard(Board board) {
        System.out.print("  ");
        for (int c = 0; c < board.getSize(); c++) {
            System.out.print(c + " ");
        }
        System.out.println();

        for (int r = 0; r < board.getSize(); r++) {
            System.out.print(r + " ");
            for (int c = 0; c < board.getSize(); c++) {
                Square square = board.getSquare(new Position(r, c));
                if (square.isHit() && square.hasShip()) {
                    System.out.print(ANSI_RED + "X" + ANSI_RESET + " "); // Navio atingido
                } else if (square.isHit() && !square.hasShip()) {
                    System.out.print(ANSI_BLUE + "O" + ANSI_RESET + " "); // Água atingida
                } else if (square.hasShip()) {
                    System.out.print(ANSI_GREEN + square.getShip().getSymbol() + ANSI_RESET + " "); // Navio intacto
                } else {
                    System.out.print("- "); // Água intocada
                }
            }
            System.out.println();
        }
    }

    // Imprime a visão do tabuleiro do inimigo (não mostra os navios intactos do inimigo)
    private static void printEnemyBoard(Board board) {
        System.out.print("  ");
        for (int c = 0; c < board.getSize(); c++) {
            System.out.print(c + " ");
        }
        System.out.println();

        for (int r = 0; r < board.getSize(); r++) {
            System.out.print(r + " ");
            for (int c = 0; c < board.getSize(); c++) {
                Square square = board.getSquare(new Position(r, c));
                if (square.isHit() && square.hasShip()) {
                    System.out.print(ANSI_RED + "X" + ANSI_RESET + " "); // Acerto (atingiu um navio)
                } else if (square.isHit() && !square.hasShip()) {
                    System.out.print(ANSI_BLUE + "O" + ANSI_RESET + " "); // Erro (atingiu água)
                } else {
                    System.out.print("- "); // Posição não atacada (pode ter navio ou não)
                }
            }
            System.out.println();
        }
    }
}

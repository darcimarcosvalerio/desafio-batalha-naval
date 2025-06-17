// game/Game.java
package game;

import board.Board;
import board.Position;
import exceptions.GameException;
import ships.Ship;
import application.UI;
import application.GameMode;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private Player player1;
    private Player player2; // Pode ser um bot
    private int currentPlayerIndex; // 0 para player1, 1 para player2
    private GameMode gameMode;
    private Scanner sc;
    private Random random; // Para a IA do computador

    public Game(int boardSize, GameMode mode) {
        this.gameMode = mode;
        this.sc = new Scanner(System.in);
        this.random = new Random();
        player1 = new Player("Jogador 1", boardSize);
        if (gameMode == GameMode.PLAYER_VS_COMPUTER) {
            player2 = new Player("Computador", boardSize);
        } else { // PLAYER_VS_PLAYER
            player2 = new Player("Jogador 2", boardSize);
        }
        currentPlayerIndex = 0; // Jogador 1 começa
    }

    public void startGame() {
        UI.clearScreen();
        System.out.println("--- INÍCIO DA PARTIDA DE BATALHA NAVAL ---");
        System.out.println("Modo de Jogo: " + (gameMode == GameMode.PLAYER_VS_COMPUTER ? "Jogador vs. Computador" : "Jogador vs. Jogador"));

        // Posicionamento dos navios
        setupShips(player1);
        if (gameMode == GameMode.PLAYER_VS_PLAYER) {
            UI.clearScreen(); // Limpa a tela para o segundo jogador não ver o primeiro
            setupShips(player2);
        } else { // Player vs. Computer
            System.out.println("\n--- Posicionando os navios do Computador... ---");
            player2.getBoard().placeShipsRandomly(player2.getShipsToPlace());
            System.out.println("Navios do Computador posicionados. Preparar para a batalha!");
            UI.pressEnterToContinue(sc);
        }


        // Loop principal do jogo
        while (!player1.areAllShipsSunk() && !player2.areAllShipsSunk()) {
            UI.clearScreen();
            Player currentPlayer = getCurrentPlayer();
            Player opponentPlayer = getOpponentPlayer();

            UI.printGameBoards(currentPlayer.getBoard(), opponentPlayer.getEnemyBoardView(), currentPlayer.getName());
            System.out.println("\n--- Vez de " + currentPlayer.getName() + " ---");

            try {
                if (currentPlayer.getName().equals("Computador")) {
                    performComputerMove(opponentPlayer);
                } else {
                    performPlayerMove(currentPlayer, opponentPlayer, sc);
                }
            } catch (GameException e) {
                System.out.println("Erro: " + e.getMessage());
                UI.pressEnterToContinue(sc);
                continue; // Permanece no mesmo turno em caso de erro de input
            }

            // Verifica condição de vitória após o movimento
            if (opponentPlayer.areAllShipsSunk()) {
                UI.clearScreen();
                UI.printGameBoards(currentPlayer.getBoard(), opponentPlayer.getEnemyBoardView(), currentPlayer.getName());
                System.out.println("\n!!! " + currentPlayer.getName().toUpperCase() + " VENCEU A BATALHA NAVAL !!!");
                break;
            }

            // Passa o turno
            switchTurn();
            UI.pressEnterToContinue(sc);
        }

        System.out.println("\n--- FIM DE JOGO ---");
        sc.close();
    }

    private void setupShips(Player player) {
        Scanner scLocal = (player.getName().equals("Computador")) ? null : sc; // Computador não precisa de Scanner

        System.out.println("\n--- Posicionamento de Navios para " + player.getName() + " ---");
        for (Ship ship : player.getShipsToPlace()) {
            boolean placed = false;
            while (!placed) {
                UI.clearScreen();
                UI.printBoardSetup(player.getBoard(), player.getName(), ship.getName(), ship.getLength());

                if (player.getName().equals("Computador")) {
                    // Lógica para posicionamento aleatório do computador
                    int row = random.nextInt(player.getBoard().getSize());
                    int col = random.nextInt(player.getBoard().getSize());
                    boolean isHorizontal = random.nextBoolean();
                    placed = player.getBoard().placeShip(ship, new Position(row, col), isHorizontal);
                    if (!placed) {
                        // Se não conseguiu, tenta novamente no próximo loop
                        System.out.println("Computador tentando posicionar " + ship.getName() + "...");
                        try { Thread.sleep(500); } catch (InterruptedException e) {} // Pequena pausa
                    }
                } else {
                    System.out.print("Posição inicial (linha,coluna ex: 0,0) para " + ship.getName() + " (tamanho " + ship.getLength() + "): ");
                    String posStr = scLocal.nextLine();
                    System.out.print("Horizontal (h) ou Vertical (v)?: ");
                    String orientationStr = scLocal.nextLine().trim().toLowerCase();

                    try {
                        String[] parts = posStr.split(",");
                        int row = Integer.parseInt(parts[0].trim());
                        int col = Integer.parseInt(parts[1].trim());
                        Position startPos = new Position(row, col);
                        boolean isHorizontal = orientationStr.equals("h");

                        placed = player.getBoard().placeShip(ship, startPos, isHorizontal);
                        if (!placed) {
                            System.out.println("Não foi possível posicionar o navio aí. Tente novamente.");
                            UI.pressEnterToContinue(scLocal);
                        }
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        System.out.println("Formato de posição inválido. Use 'linha,coluna' (ex: 0,0).");
                        UI.pressEnterToContinue(scLocal);
                    } catch (GameException e) {
                        System.out.println("Erro: " + e.getMessage());
                        UI.pressEnterToContinue(scLocal);
                    }
                }
            }
        }
        if (!player.getName().equals("Computador")) {
             System.out.println("\n" + player.getName() + ", todos os seus navios foram posicionados!");
             UI.pressEnterToContinue(scLocal);
        }
    }


    private void performPlayerMove(Player currentPlayer, Player opponentPlayer, Scanner sc) {
        System.out.print("Digite a posição para atacar (linha,coluna ex: 0,0): ");
        String attackPosStr = sc.nextLine();

        try {
            String[] parts = attackPosStr.split(",");
            int row = Integer.parseInt(parts[0].trim());
            int col = Integer.parseInt(parts[1].trim());
            Position attackPos = new Position(row, col);

            boolean hit = opponentPlayer.getBoard().attack(attackPos);
            currentPlayer.getEnemyBoardView().getSquare(attackPos).setHit(true); // Marca na visão do jogador

            if (hit) {
                Ship hitShip = opponentPlayer.getBoard().getSquare(attackPos).getShip();
                System.out.println(currentPlayer.getName() + " ACERTOU um " + hitShip.getName() + " em " + attackPos + "!");
                if (hitShip.isSunk()) {
                    System.out.println("Você AFUNDOU o " + hitShip.getName() + " do " + opponentPlayer.getName() + "!");
                }
            } else {
                System.out.println(currentPlayer.getName() + " ERROU o tiro em " + attackPos + ".");
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new GameException("Formato de posição inválido. Use 'linha,coluna' (ex: 0,0).");
        }
    }

    private void performComputerMove(Player opponentPlayer) {
        System.out.println("Vez do Computador atacar...");
        Position attackPos;
        boolean attacked = false;
        while(!attacked) {
            int row = random.nextInt(opponentPlayer.getBoard().getSize());
            int col = random.nextInt(opponentPlayer.getBoard().getSize());
            attackPos = new Position(row, col);
            try {
                boolean hit = opponentPlayer.getBoard().attack(attackPos);
                player2.getEnemyBoardView().getSquare(attackPos).setHit(true); // Marca na visão do computador

                if (hit) {
                    Ship hitShip = opponentPlayer.getBoard().getSquare(attackPos).getShip();
                    System.out.println("Computador ACERTOU um " + hitShip.getName() + " em " + attackPos + "!");
                    if (hitShip.isSunk()) {
                        System.out.println("Computador AFUNDOU o " + hitShip.getName() + " do " + opponentPlayer.getName() + "!");
                    }
                } else {
                    System.out.println("Computador ERROU o tiro em " + attackPos + ".");
                }
                attacked = true;
            } catch (GameException e) {
                // Posição já atacada, tenta novamente
                System.out.println("Computador já atacou " + attackPos + ". Tentando outra...");
            }
        }
        try { Thread.sleep(2000); } catch (InterruptedException e) {} // Pequena pausa para o usuário ler
    }


    private Player getCurrentPlayer() {
        return (currentPlayerIndex == 0) ? player1 : player2;
    }

    private Player getOpponentPlayer() {
        return (currentPlayerIndex == 0) ? player2 : player1;
    }

    private void switchTurn() {
        currentPlayerIndex = 1 - currentPlayerIndex; // Alterna entre 0 e 1
    }
}

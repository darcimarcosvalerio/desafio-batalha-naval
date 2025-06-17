// game/Player.java
package game;

import board.Board;
import ships.Ship;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private Board board; // Tabuleiro do próprio jogador (onde ele posiciona navios)
    private Board enemyBoardView; // Visão do tabuleiro do inimigo (onde ele ataca)
    private List<Ship> shipsToPlace; // Navios que o jogador tem para posicionar

    public Player(String name, int boardSize) {
        this.name = name;
        this.board = new Board(boardSize);
        this.enemyBoardView = new Board(boardSize); // Visão "vazia" do tabuleiro do inimigo
        this.shipsToPlace = new ArrayList<>();
        // Adiciona os navios padrão
        addDefaultShips();
    }

    private void addDefaultShips() {
        shipsToPlace.add(new ships.Battleship());
        shipsToPlace.add(new ships.Cruiser());
        shipsToPlace.add(new ships.Cruiser()); // Exemplo: 2 Cruzadores
        shipsToPlace.add(new ships.Destroyer());
        shipsToPlace.add(new ships.Destroyer());
        shipsToPlace.add(new ships.Destroyer()); // Exemplo: 3 Destruidores
        shipsToPlace.add(new ships.Submarine());
        shipsToPlace.add(new ships.Submarine());
        shipsToPlace.add(new ships.Submarine());
        shipsToPlace.add(new ships.Submarine()); // Exemplo: 4 Submarinos
    }

    public String getName() {
        return name;
    }

    public Board getBoard() {
        return board;
    }

    public Board getEnemyBoardView() {
        return enemyBoardView;
    }

    public List<Ship> getShipsToPlace() {
        return shipsToPlace;
    }

    public boolean areAllShipsSunk() {
        return board.areAllShipsSunk();
    }
}

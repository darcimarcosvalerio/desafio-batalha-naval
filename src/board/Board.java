// board/Board.java
package board;

import exceptions.GameException;
import ships.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private int size;
    private Square[][] grid;
    private List<Ship> ships; // Lista de navios colocados no tabuleiro

    public Board(int size) {
        if (size <= 0) {
            throw new GameException("O tamanho do tabuleiro deve ser maior que zero.");
        }
        this.size = size;
        this.grid = new Square[size][size];
        this.ships = new ArrayList<>();
        initializeGrid();
    }

    private void initializeGrid() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new Square();
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Square getSquare(Position pos) {
        if (!isValidPosition(pos)) {
            throw new GameException("Posição inválida: " + pos);
        }
        return grid[pos.getRow()][pos.getColumn()];
    }

    public boolean isValidPosition(Position pos) {
        return pos.getRow() >= 0 && pos.getRow() < size &&
               pos.getColumn() >= 0 && pos.getColumn() < size;
    }

    public List<Ship> getShips() {
        return ships;
    }

    // Tenta colocar um navio no tabuleiro
    // Retorna true se conseguiu, false caso contrário
    public boolean placeShip(Ship ship, Position startPos, boolean isHorizontal) {
        // Valida se o navio cabe e se as posições estão livres
        List<Position> occupiedPositions = new ArrayList<>();
        int shipLength = ship.getLength();

        for (int i = 0; i < shipLength; i++) {
            int currentRow = isHorizontal ? startPos.getRow() : startPos.getRow() + i;
            int currentColumn = isHorizontal ? startPos.getColumn() + i : startPos.getColumn();
            Position currentPos = new Position(currentRow, currentColumn);

            if (!isValidPosition(currentPos) || getSquare(currentPos).hasShip()) {
                return false; // Fora do tabuleiro ou posição já ocupada
            }
            occupiedPositions.add(currentPos);
        }

        // Se tudo válido, coloca o navio
        for (Position pos : occupiedPositions) {
            getSquare(pos).placeShip(ship);
            ship.addOccupiedPosition(pos); // O navio registra as posições que ocupa
        }
        ships.add(ship);
        return true;
    }

    // Gera posições aleatórias para os navios
    public void placeShipsRandomly(List<Ship> shipsToPlace) {
        Random rand = new Random();
        for (Ship ship : shipsToPlace) {
            boolean placed = false;
            while (!placed) {
                int row = rand.nextInt(size);
                int col = rand.nextInt(size);
                boolean isHorizontal = rand.nextBoolean();
                placed = placeShip(ship, new Position(row, col), isHorizontal);
            }
        }
    }

    // Verifica se todos os navios de um jogador foram afundados
    public boolean areAllShipsSunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    // Tenta atacar uma posição
    // Retorna true se acertou um navio, false se errou
    public boolean attack(Position pos) {
        if (!isValidPosition(pos)) {
            throw new GameException("Posição de ataque inválida: " + pos);
        }
        Square targetSquare = getSquare(pos);

        if (targetSquare.isHit()) {
            throw new GameException("Posição " + pos + " já foi atacada!");
        }

        targetSquare.setHit(true); // Marca como atingida

        if (targetSquare.hasShip()) {
            targetSquare.getShip().hit(); // Marca o navio como atingido
            return true; // Acertou
        } else {
            return false; // Errou
        }
    }
}

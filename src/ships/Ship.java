// ships/Ship.java
package ships;

import board.Position;
import java.util.ArrayList;
import java.util.List;

public abstract class Ship {
    private String name;
    private int length;
    private int hits;
    private List<Position> occupiedPositions; // Posições que a parte do navio ocupa

    public Ship(String name, int length) {
        this.name = name;
        this.length = length;
        this.hits = 0;
        this.occupiedPositions = new ArrayList<>(length); // Garante a capacidade inicial
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public int getHits() {
        return hits;
    }

    public void hit() {
        this.hits++;
    }

    public boolean isSunk() {
        return hits >= length; // Navio afunda quando o número de acertos é igual ou maior que seu comprimento
    }

    public List<Position> getOccupiedPositions() {
        return occupiedPositions;
    }

    public void addOccupiedPosition(Position pos) {
        this.occupiedPositions.add(pos);
    }

    // Representação para exibição no tabuleiro (pode ser o primeiro caractere do nome)
    public abstract String getSymbol();
}

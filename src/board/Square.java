// board/Square.java
package board;

import ships.Ship;

public class Square {
    private boolean hasShip;
    private Ship ship; // Referência ao navio, se houver
    private boolean isHit; // Foi atingida?

    public Square() {
        this.hasShip = false;
        this.ship = null;
        this.isHit = false;
    }

    public boolean hasShip() {
        return hasShip;
    }

    public void placeShip(Ship ship) {
        this.hasShip = true;
        this.ship = ship;
    }

    public void removeShip() {
        this.hasShip = false;
        this.ship = null;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    public Ship getShip() {
        return ship;
    }
}

// ships/Battleship.java
package ships;

public class Battleship extends Ship {
    public Battleship() {
        super("Encouraçado", 4);
    }

    @Override
    public String getSymbol() {
        return "B";
    }
}

// ships/Submarine.java
package ships;

public class Submarine extends Ship {
    public Submarine() {
        super("Submarino", 1);
    }

    @Override
    public String getSymbol() {
        return "S";
    }
}

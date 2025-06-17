// ships/Cruiser.java
package ships;

public class Cruiser extends Ship {
    public Cruiser() {
        super("Cruzador", 3);
    }

    @Override
    public String getSymbol() {
        return "C";
    }
}

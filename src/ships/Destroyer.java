// ships/Destroyer.java
package ships;

public class Destroyer extends Ship {
    public Destroyer() {
        super("Destruidor", 2);
    }

    @Override
    public String getSymbol() {
        return "D";
    }
}

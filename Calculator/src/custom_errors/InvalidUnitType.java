package custom_errors;

// InvalidUnitType class definition
public class InvalidUnitType extends Exception {
    public InvalidUnitType(String unit) {
        super("Invalid unit type: " + unit);
    }
}
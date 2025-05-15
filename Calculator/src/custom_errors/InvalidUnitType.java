package custom_errors;

public class InvalidUnitType extends Exception { // For bytes invalid formats like unlisted ba
    public InvalidUnitType(String unit) {
        super("Invalid unit type: " + unit);
        System.out.println("Unhandled unit type: " + unit);
    }
}
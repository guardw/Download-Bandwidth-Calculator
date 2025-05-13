package Calculator_Classes;
import custom_errors.InvalidUnitType;

// conversion & info twin

public class BitsClass {

    public static final String[] UNITS = {"KB", "MB", "GB", "TB"};
    public static final String[] SPEED_UNITS = {"Kbp/s", "Mbp/s", "Gbp/s", "Tbp/s"};
    
    public static double convertMB(double value, String unit) throws InvalidUnitType {
        System.out.println("Converting " + value + " " + unit + " to bits...");

        unit = unit.toUpperCase();
        switch (unit) {
            case "KB":
                return value / 1024; 
            case "MB":
                return value; 
            case "GB":
                return value * 1024; 
            case "TB":
                return value * 1_048_576; 
            default:
                throw new InvalidUnitType("Invalid unit: " + unit);
        }
    }

    public static double convertMBPS(double value, String unit) throws InvalidUnitType {
        System.out.println("Converting " + value + " " + unit + " to Mbps...");
    
        unit = unit.toUpperCase();
    
        switch (unit) {
            case "KBP/S": 
                return value / 1000;
            case "MBP/S": 
                return value;
            case "GBP/S": 
                return value * 1000;
            case "TBP/S": 
                return value * 1_000_000;
            default:
                throw new InvalidUnitType("Invalid unit: " + unit);
        }
    }

}
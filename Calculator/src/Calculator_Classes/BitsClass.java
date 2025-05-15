package Calculator_Classes;
import custom_errors.InvalidUnitType;

// conversion & info twin

public class BitsClass {

    public static final String[] UNIT_TYPES = {"Binary", "Decimal"};
    public static final String[] UNITS   = {"KB", "MB", "GB", "TB",}; // KB - KiloBytes kb - kilobits

    public static final Double[] UNITS_BASE = {0.0009765625, 1.0, 1024.0, 1_048_576.0};
    public static final Double[] UNITS_BASE2 = {1.0, 1_000.0, 1_000_000.0, 1_000_000_000.0, 1_000_000_000_000.0}; // to decimal

    public static final String[] SPEED_UNITS = {"Kbit/s", "Mbit/s", "Gbit/s", "Tbit/s"}; // bits ni ha dili bytes 
    
    public static double convertMB(double value, String unit) throws InvalidUnitType { 
        System.out.println("Converting " + value + " " + unit + " to bits...");

        unit = unit.toUpperCase();
        switch (unit) {
            case "KB":
                return value * UNITS_BASE[0]; 
            case "MB":
                return value * UNITS_BASE[1]; 
            case "GB":
                return value * UNITS_BASE[2]; 
            case "TB":
                return value * UNITS_BASE[3]; 
            default:
                throw new InvalidUnitType("Invalid unit: " + unit);
        }
    }

    public static double convertMBPS(double value, String unit) throws InvalidUnitType {
        System.out.println("Converting " + value + " " + unit + " to Mbps...");
    
        unit = unit.toUpperCase();
    
        switch (unit) {
            case "KBIT/S": 
                return value / 1000;
            case "MBIT/S": 
                return value;
            case "GBIT/S": 
                return value * 1000;
            case "TBIT/S": 
                return value * 1_000_000;
            default:
                throw new InvalidUnitType("Invalid unit: " + unit);
        }
    }

    public static double convert(double value, String base, String into, String type) throws InvalidUnitType {
        base = base.toUpperCase();
        into = into.toUpperCase();

        int from_dex = -1;
        int to_dex = -1;

        for (int i = 0; i < UNITS.length; i++) {
            if (UNITS[i].equals(base)) {
                from_dex = i;
            }
            if (UNITS[i].equals(into)) {
                to_dex = i;
            }
        }

        if (from_dex == -1 || to_dex == -1) {
            throw new InvalidUnitType("Invalid unit: " + (from_dex == -1 ? base : into));
        }

        Double[] baseArray;
        if (type.equalsIgnoreCase("Decimal")) {
            baseArray = UNITS_BASE2;
        } else {
            baseArray = UNITS_BASE;
        }

        double valueInMB = value * baseArray[from_dex];
        return valueInMB / baseArray[to_dex];
    }

}
package Calculator_Classes;

//conversion stuff twin 

public class BitsClass {
    public static double convertToBits(double value, String unit) {
        switch (unit) {
            case "KB":
                return value * 8 * 1024; 
            case "MB":
                return value * 8 * 1024 * 1024; 
            case "GB":
                return value * 8 * 1024 * 1024 * 1024; 
            case "TB":
                return value * 8 * 1024 * 1024 * 1024 * 1024; 
            default:
                return value; 
        }
    }
}

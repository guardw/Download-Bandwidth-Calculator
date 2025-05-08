package Calculator_Classes;

//conversion stuff twin 

public class BitsClass {

    public static double convert(double value, String unit) throws InvalidUnitType {
        System.out.println("Converting " + value + " " + unit + " to bits.");

        switch (unit) {
            case "KB": 
                return value / 1024; 
            case "MB":
                return value; 
            case "GB":
                return value * 1024; 
            case "TB":
                return value * 1e-6; 
            default:
                throw new InvalidUnitType(unit);
        }

    }

}

class InvalidUnitType extends Exception{
    public InvalidUnitType(String x) {super(x + " Is an unknown bite format!");}
}
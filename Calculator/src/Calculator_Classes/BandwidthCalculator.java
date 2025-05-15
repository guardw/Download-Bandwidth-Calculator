package Calculator_Classes;

import Interfaces.Calculator;

public class BandwidthCalculator implements Calculator {
    @Override
    public double calculate(double fileSizeMb, double speedMbps) { // Get the estimated time.
        return (fileSizeMb * 8) / speedMbps;
    }
}

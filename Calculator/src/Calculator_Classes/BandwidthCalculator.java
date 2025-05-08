package Calculator_Classes;

import Interfaces.Calculator;

public class BandwidthCalculator implements Calculator {
    @Override
    public double calculate(double fileSizeMb, double speedMbps) {
        return (fileSizeMb * 8) / speedMbps;
    }
}

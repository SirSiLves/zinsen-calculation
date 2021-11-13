package me.ruosch.zinsen.domain;

import lombok.*;

import java.util.Random;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Zins {
    private Produkt produkt;
    private Laufzeit laufzeit;
    private double result;
    private int iterations;

    public Zins(Produkt produkt, Laufzeit laufzeit) {
        this.produkt = produkt;
        this.laufzeit = laufzeit;

        this.setIteration();
    }

    private void setIteration() {
        switch (laufzeit) {
            case INDEFINITE:
                iterations = 100;
                break;
            case ONE:
                iterations = 100000;
                break;
            case TWO:
                iterations = 200000;
                break;
            case THREE:
                iterations = 300000;
                break;
            case FOUR:
                iterations = 400000;
                break;
            case FIVE:
                iterations = 500000;
                break;
            case SIX:
                iterations = 600000;
                break;
            case SEVEN:
                iterations = 700000;
                break;
            case EIGHT:
                iterations = 800000;
                break;
            case NINE:
                iterations = 900000;
                break;
            case TEN:
                iterations = 1000000;
                break;
            default:
                iterations = 0;
        }
    }

    // Calculate PI with monte-carlo simulation
    public void calculate() {
        double laenge = 0;
        double zaehlerdrin = 0, zaehlerdraussen = 0;
        Random r = new Random();
        for (int i = 1; i <= iterations; i++) {
            laenge = Math.sqrt((Math.pow(r.nextDouble(), 2)) + (Math.pow(r.nextDouble(), 2)));
            if (laenge < 1) {
                zaehlerdrin++;
            } else {
                zaehlerdraussen++;
            }
        }

        result = (double) zaehlerdrin / (zaehlerdrin + zaehlerdraussen) * 4;
    }
}

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
        System.out.println(laufzeit);

        switch (laufzeit) {
            case INDEFINITE:
                iterations = 2;
                break;
            case ONE:
                iterations = 10;
                break;
            case TWO:
                iterations = 100;
                break;
            case THREE:
                iterations = 10000;
                break;
            case FOUR:
                iterations = 100000;
                break;
            case FIVE:
                iterations = 1000000;
                break;
            case SIX:
                iterations = 10000000;
                break;
            case SEVEN:
                iterations = 100000000;
                break;
            case EIGHT:
                iterations = 200000000;
                break;
            case NINE:
                iterations = 300000000;
                break;
            case TEN:
                iterations = 500000000;
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

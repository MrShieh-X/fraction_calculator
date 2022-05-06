package com.mrshiehx.fraction_calculator;

public class Fraction {
    public final boolean negative;
    public final int numerator;
    public final int denominator;

    /**
     * 分数
     *
     * @param numerator   分子
     * @param denominator 分母
     */
    public Fraction(boolean negative, int numerator, int denominator) {
        this.negative = negative;
        this.numerator = Math.abs(numerator);
        this.denominator = Math.abs(denominator);
    }

    public boolean isNegative() {
        return negative;
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public Fraction switchNegative() {
        return new Fraction(!negative, numerator, denominator);
    }

    public Fraction abs() {
        return new Fraction(false, numerator, denominator);
    }

    public Fraction reciprocal() {
        return new Fraction(negative, denominator, numerator);
    }

    public Fraction add(Fraction fraction, boolean reduction) {
        if (!negative && fraction.negative) {
            return minus(fraction.switchNegative(), reduction);
        } else if (negative && !fraction.negative) {
            return fraction.minus(this.switchNegative(), reduction);
        } else if (negative/*&& fraction.negative*/) {
            return switchNegative().add(fraction.switchNegative(), reduction).switchNegative();
        } else /*if(!negative&& !fraction.negative)*/ {
            int nn1 = numerator * fraction.getDenominator();
            int nn2 = denominator * fraction.getNumerator();
            int nd1a2 = denominator * fraction.getDenominator();
            Fraction fraction2 = new Fraction(false, nn1 + nn2, nd1a2);
            if (reduction) {
                return getSimplestFraction(fraction2);
            } else {
                return fraction2;
            }
        }
    }

    public Fraction minus(Fraction fraction, boolean reduction) {
        if (!negative && fraction.negative) {
            return add(fraction.switchNegative(), reduction);
        } else if (negative && !fraction.negative) {
            return this.switchNegative().add(fraction, reduction).switchNegative();
        } else if (negative/*&& fraction.negative*/) {
            return fraction.switchNegative().minus(this.switchNegative(), reduction);
        } else /*if(!negative&& !fraction.negative)*/ {
            fraction = fraction.abs();
            int nn1 = numerator * fraction.getDenominator();
            int nn2 = denominator * fraction.getNumerator();
            int nd1a2 = denominator * fraction.getDenominator();

            int i = nn1 - nn2;

            Fraction fraction2;

            if (i >= 0) {
                fraction2 = new Fraction(false, i, nd1a2);
            } else {
                fraction2 = new Fraction(true, -i, nd1a2);
            }
            if (reduction) {
                return getSimplestFraction(fraction2);
            } else {
                return fraction2;
            }
        }
    }

    public Fraction times(Fraction fraction, boolean reduction) {
        int nn = Math.abs(getNumerator() * fraction.getNumerator());
        int nd = Math.abs(getDenominator() * fraction.getDenominator());
        Fraction fraction2 = new Fraction(negative != fraction.negative, nn, nd);
        if (reduction) {
            return getSimplestFraction(fraction2);
        } else {
            return fraction2;
        }
    }

    public Fraction div(Fraction fraction, boolean reduction) {
        int nn = Math.abs(getNumerator() * fraction.getDenominator());
        int nd = Math.abs(getDenominator() * fraction.getNumerator());
        Fraction fraction2 = new Fraction(negative != fraction.negative, nn, nd);
        if (reduction) {
            return getSimplestFraction(fraction2);
        } else {
            return fraction2;
        }
    }


    //最简分数
    public static Fraction getSimplestFraction(Fraction fraction) {
        int cf = getCommonFactor(fraction);
        return new Fraction(fraction.isNegative(), Math.abs(fraction.getNumerator()) / cf, Math.abs(fraction.getDenominator()) / cf);
    }

    //公因数
    public static int getCommonFactor(Fraction fraction) {
        for (int x = fraction.getNumerator(); x > 1; x--) {
            if (fraction.getNumerator() % x == 0 && fraction.getDenominator() % x == 0) {
                return x;
            }
        }
        return 1;
    }
}
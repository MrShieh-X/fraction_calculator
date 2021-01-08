package com.mrshiehx.fraction_calculator;

public class Fraction {
    int numerator;
    int denominator;

    /**
     * 分数
     * @param numerator   分子
     * @param denominator 分母
     */
    public Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }
}

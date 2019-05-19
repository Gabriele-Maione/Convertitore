package com.example.convertitore;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MinMaxFilter implements InputFilter {

    private int mIntMin, mIntMax;
    private char tipo;

    public MinMaxFilter(int minValue, int maxValue) {
        this.mIntMin = minValue;
        this.mIntMax = maxValue;
    }

    public MinMaxFilter(String minValue, String maxValue, char tipo) {
        this.mIntMin = Integer.parseInt(minValue);
        this.mIntMax = Integer.parseInt(maxValue);
        this.tipo=tipo;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            int input=0;
            switch (tipo){
                case 'd':{
                    input = Integer.parseInt(dest.toString() + source.toString());
                }
                break;
                case 'b':{
                    input = Integer.parseInt(dest.toString() + source.toString(), 2);
                }
                break;
            }

            if (isInRange(mIntMin, mIntMax, input))
                return null;
        } catch (NumberFormatException nfe) { }
        return "";
    }

    private boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}
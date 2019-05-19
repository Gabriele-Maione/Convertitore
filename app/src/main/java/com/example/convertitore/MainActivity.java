package com.example.convertitore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private EditText decimale;
    private EditText binario;
    public  EditText esadecimale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        decimale = findViewById(R.id.editTextDecimale);
        binario = findViewById(R.id.editTextBinario);
        esadecimale = findViewById(R.id.editTextEsadecimale);
        decimale.setFilters(new InputFilter[]{ new MinMaxFilter("0", "2147483647",'d')});
        binario.setFilters(new InputFilter[]{ new MinMaxFilter("0", "2147483647",'b')});
        InputFilter inputFilter_moodMsg = new InputFilter() {
            private int mIntMin=0, mIntMax=2147483647;
            @Override
            public CharSequence filter(CharSequence source, int start, int end,Spanned dest, int dstart, int dend) {
                try{

                    int input = Integer.parseInt(dest.toString() + source.toString(), 16);
                    if (isInRange(mIntMin, mIntMax, input)){
                        return null;
                    }
                }
                catch (NumberFormatException nfe){}
                return "";
            }
            private boolean isInRange(int a, int b, int c) {
                return b > a ? c >= a && c <= b : c >= b && c <= a;
            }
        };
        esadecimale.setFilters(new InputFilter[] { inputFilter_moodMsg });

        decimale.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Convertitore.setDecimale(true);
                }
                else{
                    Convertitore.setDecimale(false);
                }
            }
        });
        binario.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Convertitore.setBinario(true);
                }
                else{
                    Convertitore.setBinario(false);
                }
            }
        });
        esadecimale.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Convertitore.setEsadecimale(true);
                }
                else{
                    Convertitore.setEsadecimale(false);
                }
            }
        });
        decimale.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(Convertitore.getDecimale()==true) {
                    if (s.length() == 0) {
                        binario.setText("");
                        esadecimale.setText("");
                    } else {

                        binario.setText(Integer.toBinaryString(Integer.parseInt(decimale.getText().toString())));
                        esadecimale.setText(Integer.toHexString(Integer.parseInt(decimale.getText().toString())).toUpperCase());
                    }
                }
            }
        });
        binario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(Convertitore.getBinario()==true){
                    if(s.length() == 0){
                        decimale.setText("");
                        esadecimale.setText("");
                    }
                    else{
                        decimale.setText(String.valueOf(Integer.parseInt(binario.getText().toString(), 2)));
                        esadecimale.setText(Integer.toHexString(Integer.parseInt(binario.getText().toString(), 2)).toUpperCase());
                    }
                }
            }
        });
        esadecimale.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(Convertitore.getEsadecimale()==true){
                    if(s.length() == 0){
                        decimale.setText("");
                        binario.setText("");
                    }
                    else{
                        decimale.setText(String.valueOf(Integer.parseInt(esadecimale.getText().toString(), 16)));
                        binario.setText(Integer.toBinaryString(Integer.parseInt(esadecimale.getText().toString(), 16)));
                    }
                }
            }
        });
    }
}

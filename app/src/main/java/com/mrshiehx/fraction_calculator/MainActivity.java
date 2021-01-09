package com.mrshiehx.fraction_calculator;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {
    EditText n1, d1, n2, d2, n3, d3;
    Button makes, copyN, copyD;
    RadioGroup symbols;
    RadioButton add, minus, times, div;
    CheckBox red;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        n1 = findViewById(R.id.numerator1);
        n2 = findViewById(R.id.numerator2);
        n3 = findViewById(R.id.numerator3);
        d1 = findViewById(R.id.denominator1);
        d2 = findViewById(R.id.denominator2);
        d3 = findViewById(R.id.denominator3);
        makes = findViewById(R.id.makes);
        copyN = findViewById(R.id.copy_n);
        copyD = findViewById(R.id.copy_d);
        symbols = findViewById(R.id.symbols);
        add = findViewById(R.id.add);
        minus = findViewById(R.id.minus);
        times = findViewById(R.id.times);
        div = findViewById(R.id.div);
        red = findViewById(R.id.reduction);
        copyN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(n3.getText())) {
                    copy(n3.getText());
                }
            }
        });
        copyD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(d3.getText())) {
                    copy(d3.getText());
                }
            }
        });
        makes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, String.valueOf(add.isChecked()) + minus.isChecked() + times.isChecked() + div.isChecked() + "", Toast.LENGTH_SHORT).show();
                if (!TextUtils.isEmpty(n1.getText())
                        && !TextUtils.isEmpty(n2.getText())
                        && !TextUtils.isEmpty(d1.getText())
                        && !TextUtils.isEmpty(d2.getText())) {
                    //Toast.makeText(MainActivity.this, "67", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(MainActivity.this, "747", Toast.LENGTH_SHORT).show();

                    /**
                     * n1  n2 = n3
                     * d1  d2 = d3
                     *
                     * 2   5
                     * 3   7
                     * 14
                     * 21
                     * n1*d2=nn1
                     * d1*n2=nn2
                     * d1*d2=nd1
                     * d1*d2=nd2
                     */

                    int nn1 = Integer.parseInt(n1.getText().toString()) * Integer.parseInt(d2.getText().toString());
                    int nn2 = Integer.parseInt(d1.getText().toString()) * Integer.parseInt(n2.getText().toString());
                    int nd1a2 = Integer.parseInt(d1.getText().toString()) * Integer.parseInt(d2.getText().toString());
                    if (add.isChecked()) {
                        Fraction fraction = new Fraction(nn1 + nn2, nd1a2);
                        if (red.isChecked()) {
                            Fraction fraction1 = getSF(fraction);
                            n3.setText(fraction1.getNumerator() + "");
                            d3.setText(fraction1.getDenominator() + "");
                        } else {
                            n3.setText(fraction.getNumerator() + "");
                            d3.setText(fraction.getDenominator() + "");
                        }
                    } else if (minus.isChecked()) {
                        Fraction fraction = new Fraction(nn1 - nn2, nd1a2);
                        if (red.isChecked()) {
                            Fraction fraction1 = getSF(fraction);
                            n3.setText(fraction1.getNumerator() + "");
                            d3.setText(fraction1.getDenominator() + "");
                        } else {
                            n3.setText(fraction.getNumerator() + "");
                            d3.setText(fraction.getDenominator() + "");
                        }
                    } else if (times.isChecked()) {
                        int nn = Integer.parseInt(n1.getText().toString()) * Integer.parseInt(n2.getText().toString());
                        int nd = Integer.parseInt(d1.getText().toString()) * Integer.parseInt(d2.getText().toString());
                        Fraction fraction = new Fraction(nn, nd);
                        if (red.isChecked()) {
                            Fraction fraction1 = getSF(fraction);
                            n3.setText(fraction1.getNumerator() + "");
                            d3.setText(fraction1.getDenominator() + "");
                        } else {
                            n3.setText(fraction.getNumerator() + "");
                            d3.setText(fraction.getDenominator() + "");
                        }

                    } else if (div.isChecked()) {
                        int a = Integer.parseInt(d2.getText().toString());
                        int b = Integer.parseInt(n2.getText().toString());
                        int nn = Integer.parseInt(n1.getText().toString()) * a;
                        int nd = Integer.parseInt(d1.getText().toString()) * b;
                        Fraction fraction = new Fraction(nn, nd);
                        if (red.isChecked()) {
                            Fraction fraction1 = getSF(fraction);
                            n3.setText(fraction1.getNumerator() + "");
                            d3.setText(fraction1.getDenominator() + "");
                        } else {
                            n3.setText(fraction.getNumerator() + "");
                            d3.setText(fraction.getDenominator() + "");
                        }
                    }
                }
            }
        });
    }

    Fraction getSF(Fraction fraction) {
        int cf = getCF(fraction);
        return new Fraction(fraction.getNumerator() / cf, fraction.getDenominator() / cf);
    }

    int getCF(Fraction fraction) {
        for (int x = fraction.getNumerator(); x > 1; x--) {
            if (fraction.getNumerator() % x == 0 && fraction.getDenominator() % x == 0) {
                return x;
            }
        }
        return 1;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void copy(CharSequence content) {
        ClipboardManager copy = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        copy.setText(content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, Menu.FIRST + 1, 1, getString(R.string.about));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == Menu.FIRST + 1) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.dialog_about_title)
                    .setMessage(R.string.dialog_about_message)
                    .setPositiveButton(R.string.dialog_about_visit_github_of_this_application, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            goToWebsite("https://github.com/MrShieh-X/fraction_calculator");
                        }
                    })
                    .setNegativeButton(R.string.dialog_about_visit_github_of_author, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            goToWebsite("https://github.com/MrShieh-X");
                        }
                    })
                    .setNeutralButton(R.string.dialog_about_visit_msxw, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            goToWebsite("https://mrshieh-x.github.io");
                        }
                    }).show();
        }
        return true;
    }


    void goToWebsite(String url) {
        Intent intent = new Intent();
        intent.setData(Uri.parse(url));
        intent.setAction(Intent.ACTION_VIEW);
        startActivity(intent);
    }
}
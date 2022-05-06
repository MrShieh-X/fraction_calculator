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
    EditText n1, d1, n2, d2, n3, d3, n4, d4, n5, d5, exponential;
    Button makes, copyN, copyD, makes2;
    RadioGroup symbols;
    RadioButton add, minus, times, div;
    CheckBox red, negative1, negative2, negative3, red2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        n1 = findViewById(R.id.numerator1);
        n2 = findViewById(R.id.numerator2);
        n3 = findViewById(R.id.numerator3);
        n4 = findViewById(R.id.numerator4);
        n5 = findViewById(R.id.numerator5);
        d1 = findViewById(R.id.denominator1);
        d2 = findViewById(R.id.denominator2);
        d3 = findViewById(R.id.denominator3);
        d4 = findViewById(R.id.denominator4);
        d5 = findViewById(R.id.denominator5);
        makes = findViewById(R.id.makes);
        makes2 = findViewById(R.id.makes2);
        copyN = findViewById(R.id.copy_n);
        copyD = findViewById(R.id.copy_d);
        symbols = findViewById(R.id.symbols);
        add = findViewById(R.id.add);
        minus = findViewById(R.id.minus);
        times = findViewById(R.id.times);
        div = findViewById(R.id.div);
        red = findViewById(R.id.reduction);
        red2 = findViewById(R.id.reduction2);

        negative1 = findViewById(R.id.negative1);
        negative2 = findViewById(R.id.negative2);
        negative3 = findViewById(R.id.negative3);

        exponential = findViewById(R.id.exponential);

        copyN.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(n3.getText())) {
                copy(n3.getText());
            }
        });
        copyD.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(d3.getText())) {
                copy(d3.getText());
            }
        });
        makes.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(n1.getText())
                    && !TextUtils.isEmpty(n2.getText())
                    && !TextUtils.isEmpty(d1.getText())
                    && !TextUtils.isEmpty(d2.getText())) {

                boolean negative1 = MainActivity.this.negative1.isChecked();
                boolean negative2 = MainActivity.this.negative2.isChecked();
                Fraction f1 = new Fraction(negative1, Integer.parseInt(n1.getText().toString()), Integer.parseInt(d1.getText().toString()));
                Fraction f2 = new Fraction(negative2, Integer.parseInt(n2.getText().toString()), Integer.parseInt(d2.getText().toString()));


                if (f1.getDenominator() == 0 || f2.getDenominator() == 0) {
                    Toast.makeText(MainActivity.this, R.string.denominator_zero, Toast.LENGTH_SHORT).show();
                    n3.setText(null);
                    d3.setText(null);
                } else {
                    Fraction result;
                    boolean red = MainActivity.this.red.isChecked();
                    if (add.isChecked()) {
                        result = f1.add(f2, red);
                        n3.setText((result.negative ? "-" : "") + result.getNumerator());
                        d3.setText(result.getDenominator() + "");
                    } else if (minus.isChecked()) {
                        result = f1.minus(f2, red);
                        n3.setText((result.negative ? "-" : "") + result.getNumerator());
                        d3.setText(result.getDenominator() + "");
                    } else if (times.isChecked()) {
                        result = f1.times(f2, red);
                        n3.setText((result.negative ? "-" : "") + result.getNumerator());
                        d3.setText(result.getDenominator() + "");
                    } else if (div.isChecked()) {
                        result = f1.div(f2, red);
                        n3.setText((result.negative ? "-" : "") + result.getNumerator());
                        d3.setText(result.getDenominator() + "");

                    }
                }
            }
        });
        makes2.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(n4.getText())
                    && !TextUtils.isEmpty(d4.getText())
                    && !TextUtils.isEmpty(exponential.getText())) {

                boolean negative = MainActivity.this.negative3.isChecked();
                Fraction f1 = new Fraction(negative, Integer.parseInt(n4.getText().toString()), Integer.parseInt(d4.getText().toString()));


                int exponential = Integer.parseInt(MainActivity.this.exponential.getText().toString());
                if (f1.getDenominator() == 0) {
                    Toast.makeText(MainActivity.this, R.string.denominator_zero, Toast.LENGTH_SHORT).show();
                    n5.setText(null);
                    d5.setText(null);
                } else {
                    if (exponential == 0) {
                        n5.setText("1");
                        d5.setText("1");
                    } else if (exponential == 1) {
                        n5.setText((negative ? "-" : "") + n4.getText().toString());
                        d5.setText(d4.getText());
                    } else if (exponential > 0) {
                        Fraction source = f1;
                        for (int i = 0; i < exponential - 1; i++) {
                            f1 = f1.times(source, MainActivity.this.red2.isChecked());
                        }
                        n5.setText((f1.negative ? "-" : "") + f1.numerator);
                        d5.setText(String.valueOf(f1.denominator));
                    } else {

                        Fraction source = f1;
                        for (int i = 0; i < ((-exponential) - 1); i++) {
                            f1 = f1.times(source, MainActivity.this.red2.isChecked());
                        }
                        f1 = f1.reciprocal();
                        n5.setText((f1.negative ? "-" : "") + f1.numerator);
                        d5.setText(String.valueOf(f1.denominator));
                    }

                }
            }
        });
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
                    .setPositiveButton(R.string.dialog_about_visit_github_of_this_application, (dialog, which) -> goToWebsite("https://github.com/MrShieh-X/fraction_calculator"))
                    .setNegativeButton(R.string.dialog_about_visit_github_of_author, (dialog, which) -> goToWebsite("https://github.com/MrShieh-X"))
                    .setNeutralButton(R.string.dialog_about_visit_msxw, (dialog, which) -> goToWebsite("https://mrshieh-x.github.io")).show();
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
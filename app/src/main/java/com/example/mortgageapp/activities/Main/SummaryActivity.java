package com.example.mortgageapp.activities.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.mortgageapp.R;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class SummaryActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    TextView announceTextView;
    TextView summaryCurrencyTextView;
    TextView totalAmountTextView;

    private Map<String, String> currencyMap = new HashMap<String, String>(){{
        put("Dollar", "$");
        put("Euro", "€");
        put("Pound", "£");
    }};

    private Map<String, Integer> paymentMap = new HashMap<String, Integer>(){{
        put("Weekly", 52);
        put("Biweekly", 26);
        put("Monthly", 12);
    }};

    private Map<String, String> paymentWordMap = new HashMap<String, String>(){{
        put("Weekly", "weekly");
        put("Biweekly", "biweekly");
        put("Monthly", "monthly");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        announceTextView = (TextView) findViewById(R.id.announce);
        summaryCurrencyTextView = (TextView) findViewById(R.id.summaryCurrency);
        totalAmountTextView = (TextView) findViewById(R.id.totalAmount);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int numberOfPayments = paymentMap.get(sharedPreferences.getString("Payment", "Monthly"));
        String curr = currencyMap.get(sharedPreferences.getString("Currency", "Dollar"));

        Bundle bundle = getIntent().getExtras();
        double mortgageAmount = bundle.getDouble("mortgage");
        double interestRate = bundle.getDouble("interest");
        double amortizationPeriod = bundle.getDouble("amortization");

        double r = (interestRate/100) / 12;
        double n = amortizationPeriod * numberOfPayments;
        double x = Math.pow(1 + r, n);
        double w = (r * x) / (x - 1);

        double mortgagePayment = mortgageAmount * w;
        DecimalFormat df = new DecimalFormat("####0.00");
        String mortgagePaymentDisplay = df.format(mortgagePayment);

        announceTextView.setText(String.format("According to the calculations, you will make %s payments of", paymentWordMap.get(sharedPreferences.getString("Payment", "Monthly"))));
        summaryCurrencyTextView.setText(curr);
        totalAmountTextView.setText(mortgagePaymentDisplay);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Summary");
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}

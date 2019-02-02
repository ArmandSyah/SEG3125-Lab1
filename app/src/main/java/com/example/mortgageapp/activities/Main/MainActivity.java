package com.example.mortgageapp.activities.Main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mortgageapp.R;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText mortgageAmountEditText;
    private EditText interestRateEditText;
    private EditText amortizationPeriodEditText;

    private TextView currSymbolTextView;

    private Map<String, String> currencyMap = new HashMap<String, String>(){{
        put("Dollar", "$");
        put("Euro", "€");
        put("Pound", "£");
    }};

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (sharedPreferences.getBoolean("firstTimeRun", true)) {

            //get the preferences editor
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("Currency", "Dollar");
            editor.putString("Payment", "Weekly");

            // avoid for next run
            editor.putBoolean("firstTimeRun", false);
            editor.commit();
        }

        Button calculateButton = findViewById(R.id.calculateButton);
        Button clearButton = findViewById(R.id.clearButton);

        mortgageAmountEditText =  findViewById(R.id.mortgageAmount);
        interestRateEditText =  findViewById(R.id.interestRate);
        amortizationPeriodEditText =  findViewById(R.id.amortizationPeriod);

        currSymbolTextView = findViewById(R.id.currSymbol);

        currSymbolTextView.setText(currencyMap.get(sharedPreferences.getString("Currency", "Dollar")));

        calculateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mortgageAmount = mortgageAmountEditText.getText().toString();
                        String interestRate = interestRateEditText.getText().toString();
                        String amortizationPeriod = amortizationPeriodEditText.getText().toString();
                        if (mortgageAmount.length() == 0 ||
                        interestRate.length() == 0 ||
                        amortizationPeriod.length() == 0
                    )
                {
                    if(mortgageAmount.length() == 0) mortgageAmountEditText.setError("Mortgage amount is required!");
                    if(interestRate.length() == 0) interestRateEditText.setError("Interest rate is required!");
                    if(amortizationPeriod.length() == 0) amortizationPeriodEditText.setError("Amortization period is required!");

                    Toast.makeText(getApplicationContext(), "Missing Fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(v.getContext(), SummaryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putDouble("mortgage", Double.parseDouble(mortgageAmount));
                bundle.putDouble("interest", Double.parseDouble(interestRate));
                bundle.putDouble("amortization", Double.parseDouble(amortizationPeriod));
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mortgageAmountEditText.getText().clear();
                interestRateEditText.getText().clear();
                amortizationPeriodEditText.getText().clear();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menuSettingsOption:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}

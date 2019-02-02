package com.example.mortgageapp.activities.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.mortgageapp.R;

public class SettingsActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    RadioGroup currencyRadioButtonGroup;
    RadioGroup paymentRadioButtonGroup;

    Button applyButton;

    String currency;
    String payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        currencyRadioButtonGroup = (RadioGroup) findViewById(R.id.currencies);
        paymentRadioButtonGroup = (RadioGroup) findViewById(R.id.payments);
        applyButton = (Button) findViewById(R.id.apply);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        currency = sharedPreferences.getString("Currency", "Dollar");
        payment = sharedPreferences.getString("Payment", "Weekly");

        switch (currency) {
            case "Dollar":
                currencyRadioButtonGroup.check(R.id.dollar);
                break;
            case "Euro":
                currencyRadioButtonGroup.check(R.id.euro);
                break;
            case "Pound":
                currencyRadioButtonGroup.check(R.id.pound);
                break;
        }

        switch (payment) {
            case "Weekly":
                paymentRadioButtonGroup.check(R.id.weekly);
                break;
            case "Biweekly":
                paymentRadioButtonGroup.check(R.id.biweekly);
                break;
            case "Monthly":
                paymentRadioButtonGroup.check(R.id.monthly);
                break;
        }

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                
                switch (currencyRadioButtonGroup.getCheckedRadioButtonId()){
                    case R.id.dollar:
                        editor.putString("Currency", "Dollar");
                        break;
                    case R.id.euro:
                        editor.putString("Currency", "Euro");
                        break;
                    case R.id.pound:
                        editor.putString("Currency", "Pound");
                        break;
                }

                switch (paymentRadioButtonGroup.getCheckedRadioButtonId()){
                    case R.id.weekly:
                        editor.putString("Payment", "Weekly");
                        break;
                    case R.id.biweekly:
                        editor.putString("Payment", "Biweekly");
                        break;
                    case R.id.monthly:
                        editor.putString("Payment", "Monthly");
                        break;
                }

                editor.commit();

                //Return to main screen
                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Settings");
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}

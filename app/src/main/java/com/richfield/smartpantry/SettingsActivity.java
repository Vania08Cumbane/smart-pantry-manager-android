package com.richfield.smartpantry;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREFS = "smart_pantry_prefs";
    private static final String KEY_EXPIRY = "expiry_alerts";
    private static final String KEY_UNITS = "units_metric";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        Switch switchExpiry = findViewById(R.id.switchExpiry);
        RadioGroup rgUnits = findViewById(R.id.rgUnits);
        RadioButton rbMetric = findViewById(R.id.rbMetric);
        RadioButton rbImperial = findViewById(R.id.rbImperial);

        switchExpiry.setChecked(prefs.getBoolean(KEY_EXPIRY, true));
        if (prefs.getBoolean(KEY_UNITS, true)) {
            rbMetric.setChecked(true);
        } else {
            rbImperial.setChecked(true);
        }

        switchExpiry.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean(KEY_EXPIRY, isChecked).apply();
            Toast.makeText(this, isChecked ? "Expiry alerts on" : "Expiry alerts off",
                    Toast.LENGTH_SHORT).show();
        });

        rgUnits.setOnCheckedChangeListener((group, checkedId) -> {
            boolean metric = checkedId == R.id.rbMetric;
            prefs.edit().putBoolean(KEY_UNITS, metric).apply();
            Toast.makeText(this, metric ? "Metric selected" : "Imperial selected",
                    Toast.LENGTH_SHORT).show();
        });

        // Optional bottom nav if layout had it – settings is simple, use up navigation
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

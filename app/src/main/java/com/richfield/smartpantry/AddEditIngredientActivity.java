package com.richfield.smartpantry;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class AddEditIngredientActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private TextInputEditText etName, etQuantity, etUnit, etExpiry;
    private long editId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        db = new DatabaseHelper(this);
        etName = findViewById(R.id.etName);
        etQuantity = findViewById(R.id.etQuantity);
        etUnit = findViewById(R.id.etUnit);
        etExpiry = findViewById(R.id.etExpiry);
        TextView tvTitle = findViewById(R.id.tvTitle);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnCancel = findViewById(R.id.btnCancel);

        if (getIntent().hasExtra("id")) {
            editId = getIntent().getLongExtra("id", -1);
            PantryItem item = db.getPantryItem(editId);
            if (item != null) {
                tvTitle.setText(R.string.edit_ingredient);
                etName.setText(item.getName());
                etQuantity.setText(String.valueOf(item.getQuantity()));
                etUnit.setText(item.getUnit());
                etExpiry.setText(item.getExpiry());
            }
        }

        btnSave.setOnClickListener(v -> save());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void save() {
        String name = etName.getText() != null ? etName.getText().toString().trim() : "";
        String qtyStr = etQuantity.getText() != null ? etQuantity.getText().toString().trim() : "";
        String unit = etUnit.getText() != null ? etUnit.getText().toString().trim() : "";
        String expiry = etExpiry.getText() != null ? etExpiry.getText().toString().trim() : "";

        if (name.isEmpty()) {
            etName.setError(getString(R.string.name_required));
            etName.requestFocus();
            return;
        }

        double qty;
        try {
            qty = Double.parseDouble(qtyStr);
            if (qty <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            etQuantity.setError(getString(R.string.qty_required));
            etQuantity.requestFocus();
            return;
        }

        if (editId >= 0) {
            db.updatePantryItem(editId, name, qty, unit, expiry);
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
        } else {
            db.addPantryItem(name, qty, unit, expiry);
            Toast.makeText(this, "Added to pantry", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}

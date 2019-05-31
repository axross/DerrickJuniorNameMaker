package com.example.derrick_junior_name_maker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class NameActivity extends AppCompatActivity {

    private TextView nameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        nameTV = findViewById(R.id.nameTextView);
        nameTV.setText(getIntent().getStringExtra(MainActivity.EXTRA_NAME));
    }
}

package com.example.ahmed.bodybuillding;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements Conector {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void MoveData(Data data) {

            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("data", data);
            startActivity(intent);

    }
}

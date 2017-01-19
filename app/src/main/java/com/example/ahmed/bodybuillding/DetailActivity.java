package com.example.ahmed.bodybuillding;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent=getIntent();
        Data data= (Data) intent.getSerializableExtra("data");
        DetailFragment detailFragment= (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragment2);
        detailFragment.getData(data);
    }
}

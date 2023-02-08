package com.deyeon209.transapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.deyeon209.transapp.adapter.TransAdapter;
import com.deyeon209.transapp.model.Trans;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TransAdapter adapter;
    ArrayList<Trans> transList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListActivity.this));

        transList= (ArrayList<Trans>) getIntent().getSerializableExtra("transList");

        adapter = new TransAdapter(ListActivity.this, transList);
        recyclerView.setAdapter(adapter);



    }
}
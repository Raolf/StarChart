package com.example.starchart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListView extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter rvAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private MutableLiveData<List<Star>> List;
    private ListViewmodel lvm;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        lvm = new ListViewmodel();
        recyclerView = (RecyclerView) findViewById(R.id.recList);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List = lvm.getStars();
        setList();

        lvm.getStars().observe(this, new Observer<java.util.List<Star>>() {
            @Override
            public void onChanged(java.util.List<Star> stars) {
                setList();
            }
        });

    }

    public void setList(){
        rvAdapter = new ListViewAdapter(List);
        recyclerView.setAdapter(rvAdapter);
    }
}

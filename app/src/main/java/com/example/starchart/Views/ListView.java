package com.example.starchart.Views;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.starchart.ApiResponse.Star;
import com.example.starchart.ListViewAdapter;
import com.example.starchart.ListViewmodel;
import com.example.starchart.R;

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

        lvm = new ListViewmodel(getApplication());
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

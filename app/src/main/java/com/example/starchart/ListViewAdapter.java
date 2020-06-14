package com.example.starchart;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.MyViewHolder> {
    private MutableLiveData<List<Star>> dataset;

    public ListViewAdapter(MutableLiveData<List<Star>> dataset){
        this.dataset = dataset;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;
        public MyViewHolder(TextView v){
            super(v);
            textView = v;
        }
    }

    public ListViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(dataset.getValue().indexOf(position));
    }

    @Override
    public int getItemCount() {
        if(dataset.getValue()==null){
            return 0;
        }else{
            return dataset.getValue().size();
        }
    }
}

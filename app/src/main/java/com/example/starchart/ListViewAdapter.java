package com.example.starchart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;
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

        public RelativeLayout relativeLayout;
        public TextView textView;
        public MyViewHolder(RelativeLayout v, ListViewAdapter listViewAdapter){
            super(v);
            textView = itemView.findViewById(R.id.Toxt);
            relativeLayout = v;
        }
    }

    public ListViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v, this);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(dataset.getValue().get(position).getName());
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

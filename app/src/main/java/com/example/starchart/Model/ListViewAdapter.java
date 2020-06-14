package com.example.starchart.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.starchart.Model.ApiResponse.Star;
import com.example.starchart.R;

import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ListViewHolder> {
    private MutableLiveData<List<Star>> dataset;

    public ListViewAdapter(MutableLiveData<List<Star>> dataset){
        this.dataset = dataset;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder{

        public RelativeLayout relativeLayout;
        public TextView textView;
        public int pageNR;
        public ListViewHolder(RelativeLayout v, ListViewAdapter listViewAdapter){
            super(v);
            textView = itemView.findViewById(R.id.Toxt);
            relativeLayout = v;
        }
    }

    public ListViewAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ListViewHolder vh = new ListViewHolder(v, this);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.textView.setText(dataset.getValue().get(position).getName());
        holder.pageNR = dataset.getValue().get(position).getPageId();
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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

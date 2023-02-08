package com.deyeon209.transapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deyeon209.transapp.R;
import com.deyeon209.transapp.model.Trans;

import java.util.ArrayList;

public class TransAdapter extends RecyclerView.Adapter<TransAdapter.ViewHolder>  {

    Context context;
    ArrayList<Trans> transList;

    public TransAdapter(Context context, ArrayList<Trans> transList) {
        this.context = context;
        this.transList = transList;
    }

    @NonNull
    @Override
    public TransAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);
        return new TransAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransAdapter.ViewHolder holder, int position) {
        Trans trans = transList.get(position);


        holder.txtInput.setText(trans.input+"");
        holder.txtOuput.setText(trans.output+"");
        holder.txtregion.setText(trans.region+"");
    }

    @Override
    public int getItemCount() {
        return transList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtInput;
        TextView txtOuput;

        TextView txtregion;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            txtInput = itemView.findViewById(R.id.txtInput);
            txtOuput = itemView.findViewById(R.id.txtOutput);
            txtregion =itemView.findViewById(R.id.txtRegion);



        }
    }

    }

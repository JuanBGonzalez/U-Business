package com.example.juanb.ubusiness.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.juanb.ubusiness.R;
import com.example.juanb.ubusiness.models.objects.Inquilinos;

import java.util.ArrayList;
import java.util.List;

public class InquilinosAdapter extends RecyclerView.Adapter<InquilinosAdapter.CustomViewHolder> {
    private LayoutInflater inflater;
    private List<Inquilinos> listData;

    private ItemClickCallback itemClickCallback;

    public InquilinosAdapter (List<Inquilinos> listData, Context c){
        inflater = LayoutInflater.from(c);
        this.listData = listData;
    }

    public interface ItemClickCallback {
        void onItemClick(int p);
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    @Override
    public InquilinosAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_inquilinos, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Inquilinos inqui = listData.get(position);
        holder.inquilinosContent.setText("#" + inqui.getCuarto() + " | "+  inqui.getNombre()+" "+inqui.getApellido()+" | Cedula: "+inqui.getCedula());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void setListData(ArrayList<Inquilinos> todoList) {
        this.listData.clear();
        this.listData.addAll(todoList);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        View container;
        TextView inquilinosContent;

        public CustomViewHolder(View itemView) {
            super(itemView);

            inquilinosContent = (TextView) itemView.findViewById(R.id.lbl_inquilinos_content);
            container = itemView.findViewById(R.id.cont_inquilinos_root);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickCallback.onItemClick(getAdapterPosition());
        }
    }
}

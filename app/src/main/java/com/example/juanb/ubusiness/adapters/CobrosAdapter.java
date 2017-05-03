package com.example.juanb.ubusiness.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.juanb.ubusiness.R;
import com.example.juanb.ubusiness.models.objects.Cobros;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juanb on 02/08/17.
 */

public class CobrosAdapter extends RecyclerView.Adapter<CobrosAdapter.CustomViewHolder>{
    private LayoutInflater inflater2;
    private List<Cobros> listData;

    private CobrosAdapter.ItemClickCallback itemClickCallback;

    public CobrosAdapter (List<Cobros> listData, Context c){
        inflater2 = LayoutInflater.from(c);
        this.listData = listData;
    }

    public interface ItemClickCallback {
        void onItemClick(int p);
    }

    public void setItemClickCallback(final CobrosAdapter.ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    @Override
    public CobrosAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater2.inflate(R.layout.item_cobro, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Cobros cobro = listData.get(position);
        holder.cobrosContent.setText(cobro.getNombre()+" "+cobro.getApellido() + " | B/."+cobro.getMonto()+ " | "+cobro.getTodoCreationDate());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void setListData(ArrayList<Cobros> cobro) {
        this.listData.clear();
        this.listData.addAll(cobro);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        View container;
        TextView cobrosContent;

        public CustomViewHolder(View itemView) {
            super(itemView);

            cobrosContent = (TextView) itemView.findViewById(R.id.lbl_inquilinos_content2);
            container = itemView.findViewById(R.id.cont_inquilinos_root2);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickCallback.onItemClick(getAdapterPosition());
        }
    }
}

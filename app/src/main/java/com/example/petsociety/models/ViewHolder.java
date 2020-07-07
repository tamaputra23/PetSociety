package com.example.petsociety.models;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.petsociety.R;

public class ViewHolder extends RecyclerView.ViewHolder  {
    View mView;
    Button btnAdd;
    TextView tvPetname,tvMenu, tvPrice,tvServicetype;
    public ViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        tvPetname = mView.findViewById(R.id.tv_petnameCo);
        tvPrice = mView.findViewById(R.id.tv_priceCo);
        tvServicetype = mView.findViewById(R.id.tv_servicetypeCo);
        tvMenu = mView.findViewById(R.id.tv_MenuCo);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });
    }
    public void bindToPost(GroomingCheckout order){
        tvPetname.setText(order.petname);
        tvPrice.setText("Rp " + order.price_est);
        tvServicetype.setText(order.tpservice);
        tvMenu.setText(order.menu);
    }
    private ViewHolder.ClickListener mClickListener;
    //interface to send callbacks
    public interface ClickListener{
        void onItemClick(View view, int position);
    }
    public void setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }

}

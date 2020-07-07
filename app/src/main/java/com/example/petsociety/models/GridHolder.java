package com.example.petsociety.models;
import android.content.Context;
import android.media.Image;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.petsociety.NeedsActivity;
import com.example.petsociety.R;
import com.squareup.picasso.Picasso;
public class GridHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View mView;
    public TextView total, mTitle, priceTv, mPricehd;
    public ImageButton ibPlus, ibMinus;
    public GridHolder(View itemView) {
        super(itemView);
        mView = itemView;
        total = mView.findViewById(R.id.tv_totalNd);
        priceTv = mView.findViewById(R.id.tv_needs);
        mPricehd = mView.findViewById(R.id.tv_privehd);
        ibMinus = mView.findViewById(R.id.ib_minus);
        ibPlus = mView.findViewById(R.id.ib_plus);
        mTitle = mView.findViewById(R.id.tv_title);
        ibPlus.setOnClickListener(this);
        ibMinus.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == ibPlus.getId()){
            int mTotal = Integer.parseInt(total.getText().toString());
            String vTitle = mTitle.getText().toString();
            mTotal = mTotal + 1;
            total.setText(String.valueOf(mTotal));
            int vPrice = Integer.parseInt(mPricehd.getText().toString());
            int vSum = mTotal*vPrice;
            NeedsActivity.modelArrayList.get(getAdapterPosition()).setTotal(mTotal);
            NeedsActivity.modelArrayList.get(getAdapterPosition()).setTitle(vTitle);
            NeedsActivity.modelArrayList.get(getAdapterPosition()).setPrice(vSum);
        }
        else if(v.getId() == ibMinus.getId()) {
            int vTotal = Integer.parseInt(total.getText().toString());
            vTotal = vTotal - 1;
            total.setText(String.valueOf(vTotal));
            String vTitle = mTitle.getText().toString();
            int vPrice = Integer.parseInt(mPricehd.getText().toString());
            int vSum = vTotal*vPrice;
            NeedsActivity.modelArrayList.get(getAdapterPosition()).setTotal(vTotal);
            NeedsActivity.modelArrayList.get(getAdapterPosition()).setTitle(vTitle);
            NeedsActivity.modelArrayList.get(getAdapterPosition()).setPrice(vSum);
        }
    }
    public void setDetails(Context ctx, String gambar, int price, int Total, String title){
        ImageView mImageIv = mView.findViewById(R.id.iv_needs);
        priceTv.setText("Rp " + String.valueOf(price));
        Picasso.get().load(gambar).into(mImageIv);
        mTitle.setText(title);
        mPricehd.setText(String.valueOf(price));

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

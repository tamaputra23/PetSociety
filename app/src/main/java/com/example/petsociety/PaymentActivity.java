package com.example.petsociety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.petsociety.models.GroomingCheckout;
import com.example.petsociety.models.NeedsCheckout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PaymentActivity extends BaseActivity {
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;
    DatabaseReference mDatabase;
    TextView tvPetname, tvPettype, tvService, tvPrice, tvDelivery, tvTotal, tvOrdercode, tvJumlahCo;
    TextView yourpet, type, priceestim, deliveryfee;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseDatabase.getReference();
        tvPetname = findViewById(R.id.tv_petnamepay);
        tvPettype = findViewById(R.id.tv_pettypepay);
        tvPrice = findViewById(R.id.tv_priceestpay);
        tvDelivery = findViewById(R.id.tv_deliverypay);
        tvTotal = findViewById(R.id.tv_totalpay);
        tvOrdercode = findViewById(R.id.tv_ordercode);
        tvJumlahCo = findViewById(R.id.tv_jumlahCo);
        type = findViewById(R.id.type);
        priceestim = findViewById(R.id.price_estim);
        deliveryfee = findViewById(R.id.delivery_fe);
        yourpet = findViewById(R.id.your_pet);

    }
    @Override
    protected void onStart() {
        super.onStart();
        final String userId = getUid();
        key = getIntent().getStringExtra("key");
        final String mMenu = getIntent().getStringExtra("menu");
        if (mMenu.equals("Grooming")){
            tvJumlahCo.setVisibility(View.INVISIBLE);
            tvPrice.setVisibility(View.VISIBLE);
            tvPettype.setVisibility(View.VISIBLE);
            priceestim.setVisibility(View.VISIBLE);
            deliveryfee.setVisibility(View.VISIBLE);
            type.setVisibility(View.VISIBLE);
            tvDelivery.setVisibility(View.VISIBLE);
            mDatabase.child("user-checkout").child(userId).child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get user value
                    GroomingCheckout checkout = dataSnapshot.getValue(GroomingCheckout.class);
                    String petname = checkout.petname;
                    String pettype = checkout.tpservice;
                    String price = checkout.price_est;
                    String delivery = checkout.delivery;
                    tvOrdercode.setText(key);
                    tvPetname.setText(petname);
                    tvPettype.setText(pettype);
                    tvPrice.setText(price);
                    tvDelivery.setText(delivery);
                    int int_priceest = Integer.parseInt(String.valueOf(price));
                    int int_delivery = Integer.parseInt(String.valueOf(delivery));
                    int total = int_priceest + int_delivery;
                    tvTotal.setText("Rp " + String.valueOf(total));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
        else if (mMenu.equals("Your Needs")){
            tvJumlahCo.setVisibility(View.VISIBLE);
            tvPrice.setVisibility(View.GONE);
            tvPettype.setVisibility(View.GONE);
            priceestim.setVisibility(View.GONE);
            deliveryfee.setVisibility(View.GONE);
            type.setVisibility(View.GONE);
            tvDelivery.setVisibility(View.GONE);
            mDatabase.child("user-checkout").child(userId).child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get user value
                    NeedsCheckout checkout = dataSnapshot.getValue(NeedsCheckout.class);
                    String petname = checkout.yourorder;
                    String mJumlah = checkout.jumlah;
                    String price = checkout.total;
                    String sum = checkout.price_est;
                    tvOrdercode.setText(key);
                    yourpet.setText(petname);
                    tvJumlahCo.setText(mJumlah);
                    tvPetname.setText(price);
                    tvTotal.setText("Rp " + sum);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
        else if (mMenu.equals("Boarding")){
            tvJumlahCo.setVisibility(View.INVISIBLE);
            tvPrice.setVisibility(View.VISIBLE);
            tvPettype.setVisibility(View.VISIBLE);
            priceestim.setVisibility(View.VISIBLE);
            deliveryfee.setVisibility(View.VISIBLE);
            type.setVisibility(View.VISIBLE);
            tvDelivery.setVisibility(View.VISIBLE);
            mDatabase.child("user-checkout").child(userId).child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get user value
                    GroomingCheckout checkout = dataSnapshot.getValue(GroomingCheckout.class);
                    String petname = checkout.petname;
                    String pettype = checkout.tpservice;
                    String price = checkout.price_est;
                    String delivery = checkout.delivery;
                    tvOrdercode.setText(key);
                    tvPetname.setText(petname);
                    tvPettype.setText(pettype);
                    tvPrice.setText(price);
                    tvDelivery.setText(delivery);
                    int int_priceest = Integer.parseInt(String.valueOf(price));
                    int int_delivery = Integer.parseInt(String.valueOf(delivery));
                    int total = int_priceest + int_delivery;
                    tvTotal.setText("Rp " + String.valueOf(total));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }
    public void back(View view){
        onBackPressed();
        finish();
    }
}

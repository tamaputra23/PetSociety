package com.example.petsociety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petsociety.models.GroomingCheckout;
import com.example.petsociety.models.GroomingOrder;
import com.example.petsociety.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ConfirmActivity extends BaseActivity
{
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;
    DatabaseReference mDatabase;
    TextView tvPetname, tvPettype, tvService, tvPrice, tvDelivery, tvTotal;
    String key, key1, mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseDatabase.getReference();
        tvPetname = findViewById(R.id.tv_petname);
        tvPettype = findViewById(R.id.tv_pettype);
        tvPrice = findViewById(R.id.tv_priceest);
        tvDelivery = findViewById(R.id.tv_delivery);
        tvTotal = findViewById(R.id.tv_total);

    }
    @Override
    protected void onStart() {
        super.onStart();
        final String userId = getUid();
        key1 = getIntent().getStringExtra("key");
        mDatabase.child("user-orders").child(userId).child(key1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get user value
                GroomingOrder order = dataSnapshot.getValue(GroomingOrder.class);
                String petname = order.petname;
                String pettype = order.tpservice;
                String price = order.price_est;
                String delivery = order.delivery;
                mMenu = order.menu;
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
    public void checkout(View view){
        final String TAG = "GroomingAcctivity";
        final String userId = getUid();
        key = mDatabase.child("user-checkout").push().getKey();
        final String mPetname = tvPetname.getText().toString();
        final String mServicetype = tvPettype.getText().toString();
        final String mPrice = tvPrice.getText().toString();
        final String mDeliveryfee = tvDelivery.getText().toString();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);
                            String name = user.firstname + " " + user.lastname;
                            writeNewPost(userId, name, mPetname, mServicetype, mPrice, mDeliveryfee, mMenu);
                        // Finish this Activity, back to the stream
                        //finish();
                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });
        key1 = getIntent().getStringExtra("key");
        String pMenu = "Grooming";
        DatabaseReference UserOrderRef = mDatabase.child("user-orders").child(userId).child(key1);
        UserOrderRef.setValue(null);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra("key",key);
        intent.putExtra("menu",pMenu);
        startActivity(intent);
        finish();
    }
    public void cencel(View view){
        final String userId = getUid();
        key1 = getIntent().getStringExtra("key");
        DatabaseReference UserOrderRef = mDatabase.child("user-orders").child(userId).child(key1);
        UserOrderRef.setValue(null);
        Intent intent = new Intent(this, GroomingActivity.class);
        startActivity(intent);
        finish();
    }
    private void writeNewPost(String userId, String name, String petname, String tpservice, String price_est, String delivery, String menu ) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        GroomingCheckout checkout = new GroomingCheckout(userId, name ,petname , tpservice, price_est, delivery, menu);
        Map<String, Object> postValues = checkout.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/user-checkout/" + userId + "/" + key, postValues);
        mDatabase.updateChildren(childUpdates);
    }
}

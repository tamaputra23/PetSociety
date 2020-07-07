package com.example.petsociety;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petsociety.models.BoardingOrder;
import com.example.petsociety.models.GroomingOrder;
import com.example.petsociety.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class BoardingActivity extends BaseActivity {
    RecyclerView yourorder;
    EditText petnameEdt;
    TextView priceTv;
    Spinner pettypeSp, groomingtypeSp;
    RadioGroup serviceRg;
    RadioButton pickupRb, deliveryRb;
    int priceEst;
    String pettype, petname, estprice, servicetype, key;

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;
    DatabaseReference mDatabase;
    SharedPreferences mSharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boarding);
        priceTv = findViewById(R.id.tv_priceHt);
        petnameEdt = findViewById(R.id.edt_NameHt);
        pettypeSp = findViewById(R.id.spPetTypeHt);
        groomingtypeSp = findViewById(R.id.spLongStay);
        serviceRg = findViewById(R.id.radio_groupHt);
        pickupRb = findViewById(R.id.rb_pickupht);
        deliveryRb = findViewById(R.id.rb_deliveryht);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseDatabase.getReference();

        ArrayAdapter petAdapter = new ArrayAdapter<String>(BoardingActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.pet_option));
        pettypeSp.setAdapter(petAdapter);
        ArrayAdapter groomAdapter = new ArrayAdapter<String>(BoardingActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.stay_long));
        groomingtypeSp.setAdapter(groomAdapter);

        pettypeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        pettype = "cat";
                        break;
                    case 1:
                        pettype = "dog";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        groomingtypeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if (pettype.equals("cat")){
                            priceEst = 25000;
                            estprice = String.valueOf(priceEst);
                            priceTv.setText("Rp " + estprice);
                            servicetype = "Cat - 1 Day Boarding";
                        }
                        if (pettype.equals("dog")){
                            priceEst = 25000;
                            estprice = String.valueOf(priceEst);
                            priceTv.setText("Rp " + estprice);
                            servicetype = "Dog - 1 Day Boarding";
                        }
                        break;
                    case 1:
                        if (pettype.equals("cat")){
                            priceEst = 50000;
                            estprice = String.valueOf(priceEst);
                            priceTv.setText("Rp " + estprice);
                            servicetype = "Cat - 2 Day Boarding";
                        }
                        if (pettype.equals("dog")){
                            priceEst = 50000;
                            estprice = String.valueOf(priceEst);
                            priceTv.setText("Rp " + estprice);
                            servicetype = "Dog - 2 Day Boarding";
                        }
                        break;
                    case 2:
                        if (pettype.equals("cat")){
                            priceEst = 70000;
                            estprice = String.valueOf(priceEst);
                            priceTv.setText("Rp " + estprice);
                            servicetype = "Cat - 3 Day Boarding";
                        }
                        if (pettype.equals("dog")){
                            priceEst = 70000;
                            estprice = String.valueOf(priceEst);
                            priceTv.setText("Rp " + estprice);
                            servicetype = "Dog - 3 Day Boarding";
                        }
                        break;
                    case 3:
                        if (pettype.equals("cat")){
                            priceEst = 5000;
                            estprice = String.valueOf(priceEst);
                            priceTv.setText("Rp " + estprice);
                            servicetype = "Cat - Extend 1 Day Boarding";
                        }
                        if (pettype.equals("dog")){
                            priceEst = 50000;
                            estprice = String.valueOf(priceEst);
                            priceTv.setText("Rp " + estprice);
                            servicetype = "Dog - Extend 1 Day Boarding";
                        }
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
    public  void confirm(View view){
        final String mPetname = petnameEdt.getText().toString();
        final String mPrice = estprice;
        final String mServicetype = servicetype;
        final String mDeliveryfee;
        final String mMenu = "Boarding";
        if (deliveryRb.isChecked()){
            mDeliveryfee = "15000";
        }
        else {
            mDeliveryfee = "0";
        }
        final String TAG = "BoardingAcctivity";
        final String userId = getUid();
        if(getUid() == null){
            Toast.makeText(BoardingActivity.this,
                    "Please Login before You order Our service",
                    Toast.LENGTH_SHORT).show();
        }
        key = mDatabase.child("user-orders").push().getKey();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        // [START_EXCLUDE]
                        if (user == null) {
                            // User is null, error out
                            Log.e(TAG, "User " + userId + " is unexpectedly null");
                            Toast.makeText(BoardingActivity.this,
                                    "Please Login before You order Our service",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent (getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            // Write new post
                            String name = user.firstname + " " + user.lastname;
                            writeNewPost(userId, name, mPetname, mServicetype, mPrice, mDeliveryfee, mMenu);
                        }

                        // Finish this Activity, back to the stream
                        //finish();
                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });
        Intent intent = new Intent(this, ConfirmActivity.class);
        intent.putExtra("key",key);
        startActivity(intent);
        finish();
    }
    public void back (View view){
        onBackPressed();
    }
    private void writeNewPost(String userId, String name, String petname, String tpservice, String price_est, String delivery, String menu ) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        //mDatabase.child("user-orders").push().getKey();
        BoardingOrder order = new BoardingOrder(userId, name ,petname , tpservice, price_est, delivery, menu);
        Map<String, Object> postValues = order.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/user-orders/" + userId + "/" + key, postValues);
        mDatabase.updateChildren(childUpdates);
    }
}

package com.example.petsociety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petsociety.models.GroomingCheckout;
import com.example.petsociety.models.NeedsCheckout;
import com.example.petsociety.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ConfirmNeedsActivity extends BaseActivity {
    private TextView tv, tvSum, tvJumlah, tvSumNd;
    int sum;
    String key;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_needs);
        tv = findViewById(R.id.your_order);
        tvSum = findViewById(R.id.tv_sum);
        tvJumlah = findViewById(R.id.tv_jumlah);
        tvSumNd = findViewById(R.id.tv_sumNd);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseDatabase.getReference();

        for (int i = 0; i < 8; i++) {
            if (NeedsActivity.modelArrayList.get(i).getTotal() != 0) {
                String text1 = tv.getText().toString();
                String text2 = tvJumlah.getText().toString();
                String text3 = tvSum.getText().toString();
                sum = sum + NeedsActivity.modelArrayList.get(i).getPrice();
                tvSumNd.setText(String.valueOf(sum));
                tv.setText(text1 + NeedsActivity.modelArrayList.get(i).getTitle()+ "\n");
                tvSum.setText(text3 + "Rp " +NeedsActivity.modelArrayList.get(i).getPrice()+ "\n");
                tvJumlah.setText(text2 + NeedsActivity.modelArrayList.get(i).getTotal()+ "\n");
            }
        }
    }
    public void cencel(View view){
        onBackPressed();
        finish();
    }
    public void checkout(View view){
        final String TAG = "ConfinrmNeedActivity";
        final String userId = getUid();
        key = mDatabase.child("user-checkout").push().getKey();
        final String mOrder = tv.getText().toString();
        final String mJumlah = tvJumlah.getText().toString();
        final String mSum = tvSum.getText().toString();
        final String mSumNd = tvSumNd.getText().toString();
        final String mMenu = "Your Needs";
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
                            Toast.makeText(ConfirmNeedsActivity.this,
                                    "Please Login before You order Our service",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                        else {
                            String name = user.firstname + " " + user.lastname;
                            writeNewPost(userId, name, mOrder, mJumlah, mSum, mSumNd, mMenu);
                            // Finish this Activity, back to the stream
                            //finish();
                            // [END_EXCLUDE]
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra("key",key);
        intent.putExtra("menu",mMenu);
        startActivity(intent);
        finish();
    }


    private void writeNewPost(String userId, String name, String yourorder, String jumlah, String total, String sum, String menu) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        NeedsCheckout checkout = new NeedsCheckout(userId, name ,yourorder , jumlah, total, sum, menu);
        Map<String, Object> postValues = checkout.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/user-checkout/" + userId + "/" + key, postValues);
        mDatabase.updateChildren(childUpdates);
    }
}

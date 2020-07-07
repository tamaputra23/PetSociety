package com.example.petsociety;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.petsociety.models.GroomingCheckout;
import com.example.petsociety.models.ViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class AboutActivity extends BaseActivity {
    RecyclerView recyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;
    DatabaseReference mDatabase;
    SharedPreferences mSharedPref; //for saving sort settings
    LinearLayoutManager mLayoutManager; //for sorting
    FirebaseRecyclerAdapter<GroomingCheckout , ViewHolder> mAdapter;
    TextView tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
         tvTotal = findViewById(R.id.you_can_com);
        mSharedPref = getSharedPreferences("SortSettings", MODE_PRIVATE);
        String mSorting = mSharedPref.getString("Sort", "newest"); //where if no settingsis selected newest will be default
        if (mSorting.equals("newest")) {
            mLayoutManager = new LinearLayoutManager(this);
            //this will load the items from bottom means newest first
            mLayoutManager.setReverseLayout(true);
            mLayoutManager.setStackFromEnd(true);
        } else if (mSorting.equals("oldest")) {
            mLayoutManager = new LinearLayoutManager(this);
            //this will load the items from bottom means oldest first
            mLayoutManager.setReverseLayout(false);
            mLayoutManager.setStackFromEnd(false);
        }
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseDatabase.getReference();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(false);
    }
    @Override
    protected void onStart() {
        super.onStart();
        final String userId = getUid();
        Query postsQuery = mDatabase.child("user-checkout").child(userId);
        mAdapter = new FirebaseRecyclerAdapter<GroomingCheckout, ViewHolder>(
                GroomingCheckout.class,
                R.layout.cardview,
                ViewHolder.class,
                postsQuery) {
            @Override
            protected void populateViewHolder(final ViewHolder viewHolder, final GroomingCheckout order, final int position) {
                viewHolder.bindToPost(order);

            }
            @Override
            public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
                ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        final DatabaseReference postRef = getRef(position);
                        String key = postRef.getKey();
                        TextView vMenu = view.findViewById(R.id.tv_MenuCo);
                        String mMenu =  vMenu.getText().toString();
                        Intent intent = new Intent(view.getContext(), PaymentActivity.class);
                        intent.putExtra("key",key);
                        intent.putExtra("menu",mMenu);
                        startActivity(intent);
                    }
                });
                return viewHolder;
            }


        };
        recyclerView.setAdapter(mAdapter);
        mDatabase.child("user-checkout").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int sum = 0;
                GroomingCheckout Order = dataSnapshot.getValue(GroomingCheckout.class);
                if (Order == null){
                    tvTotal.setText("0");
                }
                for (DataSnapshot snapm : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map <String, Object>) snapm.getValue();
                    Object price = map.get("price_est");
                    int total = Integer.parseInt(String.valueOf(price));
                    sum+= total;
                    tvTotal.setText("Rp "+ String.valueOf(sum) + ",-");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void back(View view){
        onBackPressed();
    }
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}

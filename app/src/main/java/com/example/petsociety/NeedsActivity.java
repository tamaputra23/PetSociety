package com.example.petsociety;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.petsociety.models.GridHolder;
import com.example.petsociety.models.ViewHolder;
import com.example.petsociety.models.model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NeedsActivity extends BaseActivity {
    RecyclerView recyclerView;
    DatabaseReference myRef;
    public static ArrayList<model> modelArrayList;
    SharedPreferences mSharedPref; //for saving sort settings
    FirebaseDatabase mFirebaseDatabase;
    LinearLayoutManager mLayoutManager;
    private String[] fruitlist=new String[]{"Apples", "Oranges", "Potatoes", "Tomatoes","Grapes","a","b","c"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_needs);
        myRef = FirebaseDatabase.getInstance().getReference().child("Data");
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new
                GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(false);
        modelArrayList = getModel();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<model, GridHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<model, GridHolder>(
                        model.class,
                        R.layout.gridview,
                        GridHolder.class,
                        myRef
                ) {
                    @Override
                    protected void populateViewHolder(final GridHolder viewHolder, model model, int position) {
                        viewHolder.setDetails(getApplicationContext(), model.getGambar(), model.getPrice(), model.getTotal(), model.getTitle());
                    }
                    };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    private ArrayList<model> getModel(){
        ArrayList<model> list = new ArrayList<>();
        for(int i = 0; i < 8; i++){

            model model = new model();
            model.setTotal(0);
            model.setPrice(0);
            model.setTitle(fruitlist[i]);
            list.add(model);
        }
        return list;
    }
    public void confirm(View view){
        Intent intent = new Intent(this, ConfirmNeedsActivity.class);
        startActivity(intent);
        finish();
    }
    public void back(View view){
        onBackPressed();
    }
}

package com.example.petsociety;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar();

    }
    public void grooming(View view){
        Intent grooming = new Intent(this, GroomingActivity.class);
        startActivity(grooming);

    }
    public void boarding(View view){
        Intent boarding = new Intent(this, BoardingActivity.class);
        startActivity(boarding);

    }
    public void needs(View view){
        Intent needs = new Intent(this, NeedsActivity.class);
        startActivity(needs);
    }
    public void about(View view){
        Intent about = new Intent(this, AboutActivity.class);
        startActivity(about);
    }
    public void signin(View view){
        Intent intent = new Intent (this, LoginActivity.class);
        startActivity(intent);
    }
    public void info(View view){
        ViewDialog alert = new ViewDialog();
        alert.showDialog();
    }
    public class ViewDialog extends BaseActivity {

        public void showDialog(){
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.about_popup);
            ImageView imgClose = dialog.findViewById(R.id.close_btn);
            imgClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        }
    }
}

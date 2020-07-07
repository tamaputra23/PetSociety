package com.example.petsociety;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.petsociety.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends BaseActivity implements View.OnClickListener  {
    private static final String TAG = "SignInActivity";

    private FirebaseDatabase m_Database;
    DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private EditText mEmailField;
    private EditText mPasswordField, edtLastname, edtFirstname, edtPhonenumber;
    private Button mSignInButton;
    private Button mSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        m_Database = FirebaseDatabase.getInstance();
        mDatabase = m_Database.getReference();
        mAuth = FirebaseAuth.getInstance();

        // Views
        mEmailField = findViewById(R.id.edt_email);
        mPasswordField = findViewById(R.id.edt_password);
        edtLastname = findViewById(R.id.edt_lastN);
        edtFirstname = findViewById(R.id.edt_firstN);
        edtPhonenumber = findViewById(R.id.edt_phone);
        mSignUpButton = findViewById(R.id.register);

        // Click listeners
        mSignUpButton.setOnClickListener(this);
    }
    @Override
    public void onStart() {
        super.onStart();

        // Check auth on Activity start
        if (mAuth.getCurrentUser() != null) {
            onAuthSuccess(mAuth.getCurrentUser());
        }

    }
    private void signUp() {
        Log.d(TAG, "signUp");
        if (!validateForm()) {
            return;
        }


        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
        if (password.length()<= 8){
            Toast.makeText(RegisterActivity.this, "Password is too short! it should be at least 8 characters",
                    Toast.LENGTH_SHORT).show();
        }

        else{
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());


                            if (task.isSuccessful()) {
                                onAuthSuccess(task.getResult().getUser());
                            } else {
                                Toast.makeText(RegisterActivity.this, "Sign Up Failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());
        String lastname = edtLastname.getText().toString();
        String firstname = edtFirstname.getText().toString();
        String phonenumber = edtPhonenumber.getText().toString();

        // Write new User
        writeNewUser(user.getUid(), username, user.getEmail(), lastname, firstname, phonenumber );

        // Go to MainActivity
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }
    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }
    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(mEmailField.getText().toString())) {
            mEmailField.setError("Required");
            result = false;
        } else {
            mEmailField.setError(null);
        }

        if (TextUtils.isEmpty(mPasswordField.getText().toString())) {
            mPasswordField.setError("Required");
            result = false;
        } else {
            mPasswordField.setError(null);
        }

        return result;
    }
    // [START basic_write]
    private void writeNewUser(String userId, String name, String email, String lastname, String firstname, String phonenumber) {
        User user = new User(name, email, lastname, firstname, phonenumber);
        mDatabase.child("users").child(userId).setValue(user);
    }
    // [END basic_write]

    private void signIn() {
        Log.d(TAG, "signIn");
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
                        hideProgressDialog();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(RegisterActivity.this, "Sign In Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.register) {
            signUp();
        }
    }



}

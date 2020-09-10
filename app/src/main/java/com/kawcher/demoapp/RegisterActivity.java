package com.kawcher.demoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText firstNameET, lastNameET, emailET, phoneET, passwordET;
    private Button registerBtn;
    private TextView loginTV;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        firstNameET = findViewById(R.id.firstNameET);
        lastNameET = findViewById(R.id.lastNameET);
        emailET = findViewById(R.id.emailET);
        phoneET = findViewById(R.id.phoneET);
        passwordET = findViewById(R.id.passwordET);

        registerBtn = findViewById(R.id.registerBtn);

        loginTV = findViewById(R.id.loginTV);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("please wait..");

        progressDialog.setCanceledOnTouchOutside(false);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inputData();
            }
        });


    }

    String firstName, lastName, email, phoneNumber, password;

    private void inputData() {

        firstName = firstNameET.getText().toString().trim();
        lastName = lastNameET.getText().toString().trim();
        email = emailET.getText().toString().trim();
        phoneNumber = phoneET.getText().toString().trim();
        password = passwordET.getText().toString().trim();


        if (TextUtils.isEmpty(firstName)) {

            Toast.makeText(this, "Enter first Name....", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(lastName)) {

            Toast.makeText(this, "Enter last Name....", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            Toast.makeText(this, "Invalid email Address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(phoneNumber)) {

            Toast.makeText(this, "Enter Phone Number....", Toast.LENGTH_SHORT).show();
            return;
        }


        if (password.length() < 6) {

            Toast.makeText(this, "password must be atleast 6 character long..", Toast.LENGTH_SHORT).show();
            return;
        }
        createAccount();

    }

    private void createAccount() {


        progressDialog.setMessage("Creating Account");
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //account create

                        saverFirebaseData();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //failed to create account

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void saverFirebaseData() {

        progressDialog.setMessage("Saving Account Info");

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("uid",""+firebaseAuth.getUid());
        hashMap.put("email",""+email);
        hashMap.put("firstName",""+firstName);
        hashMap.put("lastName",""+lastName);
        hashMap.put("phoneNumber",""+phoneNumber);
        hashMap.put("password",""+password);

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getUid()).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //database Updated

                        progressDialog.dismiss();

                        startActivity(new Intent(getApplicationContext(),ShowAlbumsNamesActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                //failed  updating database

                progressDialog.dismiss();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });
    }
}
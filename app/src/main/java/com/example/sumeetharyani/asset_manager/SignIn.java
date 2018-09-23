package com.example.sumeetharyani.asset_manager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class SignIn extends AppCompatActivity {

    private static final String TAG = "SignIn";
        private EditText inputEmail, inputPassword, fName, lName;
        private Button btnSignIn, btnSignUp, btnResetPassword;
        private ProgressBar progressBar;
        private FirebaseAuth auth;
        private Spinner spnSize;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private DatabaseReference mDatabaseRef;
    private String authority;
    String firstName;
    String lastName;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_in);

            auth = FirebaseAuth.getInstance();
            spnSize = findViewById(R.id.spnSize);
            fName = (EditText) findViewById(R.id.firstName);
            lName = (EditText)findViewById(R.id.lastName);
            btnSignIn = (Button) findViewById(R.id.sign_in_button);
            btnSignUp = (Button) findViewById(R.id.sign_up_button);
            inputEmail = (EditText) findViewById(R.id.email);
            inputPassword = (EditText) findViewById(R.id.password);
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
            mDatabaseRef = FirebaseDatabase.getInstance().getReference();

            btnResetPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(SignIn.this, ForgetPass.class));
                }
            });

            btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            final ArrayList<String> size=new ArrayList<>();
            size.add("Authority");
            size.add("Faculty");
            size.add("Purchase Officer");
            size.add("Store Manager");
            size.add("Principal");


            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1,size);
            spnSize.setAdapter(adapter);


            btnSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firstName = fName.getText().toString();
                    lastName = lName.getText().toString();
                    String email = inputEmail.getText().toString().trim();
                    String password = inputPassword.getText().toString().trim();

                    if (TextUtils.isEmpty(firstName)) {
                        Toast.makeText(getApplicationContext(), "Enter first name address!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(lastName)) {
                        Toast.makeText(getApplicationContext(), "Enter last name address!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (password.length() < 6) {
                        Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    int id= spnSize.getSelectedItemPosition();
                    authority = size.get(id);
                    if(spnSize.getSelectedItemPosition()==0){
                        Toast.makeText(getApplicationContext(), "Please Select!", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(SignIn.this, authority, Toast.LENGTH_LONG).show();

                    writeNewUser(auth.getUid(),firstName,lastName,authority);

                    progressBar.setVisibility(View.VISIBLE);
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(SignIn.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(SignIn.this, "Authentication failed." + task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        startActivity(new Intent(SignIn.this, MainActivity.class));
                                        finish();
                                    }
                                }
                            });

                }
            });
        }

        @Override
        protected void onResume() {
            super.onResume();
            progressBar.setVisibility(View.GONE);
        }

    private void writeNewUser(String userId, String name, String lastName, String authority) {
        Info user = new Info(name, lastName,authority);
        mDatabaseRef.child("users").child(mDatabaseRef.push().getKey()).setValue(user);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser account) {
        if (account != null) {
            Log.d(TAG, "updateUI: called");
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            Toast.makeText(this, "Sign In Successfull!", Toast.LENGTH_LONG).show();
            finish();
        }
    }


}


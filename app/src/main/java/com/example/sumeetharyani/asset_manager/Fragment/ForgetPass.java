package com.example.sumeetharyani.asset_manager.Fragment;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sumeetharyani.asset_manager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPass extends AppCompatActivity {

    EditText etForgetEmail;
    Button btnSend;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        etForgetEmail=(EditText)findViewById(R.id.etForgetEmail);
        btnSend=(Button)findViewById(R.id.btnSend);

        firebaseAuth= FirebaseAuth.getInstance();


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String e=etForgetEmail.getText().toString().trim();
                firebaseAuth.sendPasswordResetEmail(e).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ForgetPass.this, "Sent", Toast.LENGTH_SHORT).show();


                        }
                        else
                        {
                            Toast.makeText(ForgetPass.this, "Fail to send "+task.getException(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });



            }
        });


    }
}

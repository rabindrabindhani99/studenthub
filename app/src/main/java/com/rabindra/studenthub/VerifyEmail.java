package com.rabindra.studenthub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerifyEmail extends AppCompatActivity {
    TextView emailView;
    AppCompatButton verifyEmail;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);
        emailView = findViewById(R.id.email_view);
        verifyEmail = findViewById(R.id.verify_email);

        emailView.setText(user.getEmail());

        verifyEmail.setOnClickListener(v -> user.sendEmailVerification()
                .addOnCompleteListener(task -> {
                    Toast.makeText(VerifyEmail.this, "Check Your E-mail inbox or Spam to Verify yourself", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(VerifyEmail.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(VerifyEmail.this, "Error: Verification E-mail send failed. Try again", Toast.LENGTH_LONG).show();
                    verifyEmail.setText("Try Again");
                }));


    }
}
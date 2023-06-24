package com.rabindra.studenthub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText loginEmailEditText, loginPasswordEditText;
    private AppCompatButton loginButton;
    private TextView toggleSignupPage;
    private String login_email;
    private String login_pass;
    ProgressBar loginProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmailEditText = findViewById(R.id.login_email_edit_text);
        loginPasswordEditText = findViewById(R.id.login_password_edit_text);
        loginButton = findViewById(R.id.login_btn);
        toggleSignupPage = findViewById(R.id.toggle_signup_page);
        loginProgress=findViewById(R.id.login_progressBar);

        loginButton.setOnClickListener(v -> {
            ValidateEPfL validateEPfL = new ValidateEPfL();
            if (validateEPfL.isValidEp()) {
                loginProgress.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.INVISIBLE);
                loginUser(login_email, login_pass);
            }
        });

        toggleSignupPage.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
            finish();
        });
        //End of onCreate method
    }

    /*Start of external methods*/


    //Check for valid E-mail and password
    private class ValidateEPfL {
        boolean validEP = false;

        public boolean isValidEp() {
            login_email = loginEmailEditText.getText().toString();
            login_pass = loginPasswordEditText.getText().toString();
            if (login_email.isEmpty()) {
                loginEmailEditText.setError("Required");
            } else if (login_pass.isEmpty()) {
                loginPasswordEditText.setError("Required");
            } else {
                validEP = true;
            }
            return validEP;
        }
    }


    //Login user using Firebase Authentication
    private void loginUser(String login_email, String login_pass) {
        FirebaseAuth auth=FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(login_email, login_pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    final FirebaseUser user = auth.getCurrentUser();

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful() && user!=null && user.isEmailVerified())
                        {
                            Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else if (task.isSuccessful() && user != null && !(user.isEmailVerified())) {
                            Toast.makeText(LoginActivity.this, "Verify your E-mail address and try again", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, VerifyEmail.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Check E-mail and password, Try again", Toast.LENGTH_LONG).show();
                            loginButton.setVisibility(View.VISIBLE);
                            loginProgress.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LoginActivity.this, LoginOptions.class);
        startActivity(intent);
        finish();
    }
}
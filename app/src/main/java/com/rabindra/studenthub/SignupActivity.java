package com.rabindra.studenthub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    AppCompatButton signupButton;
    private TextInputEditText signupEmailEditText, signupPasswordEditText, confirmPasswordEditText;
    private TextView invalidEmailPass;
    private FirebaseAuth auth;
    private String signup_email;
    private String signup_pass;
    private String confirm_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signupEmailEditText = findViewById(R.id.signup_email_edit_text);
        signupPasswordEditText = findViewById(R.id.signup_password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);

        TextInputLayout signupEmailLayout = findViewById(R.id.signup_emailTIL);
        TextInputLayout signupPasswordLayout = findViewById(R.id.signup_passwordTIL);
        TextInputLayout confirmPasswordLayout = findViewById(R.id.confirm_passwordTIL);

        signupButton = findViewById(R.id.signUp_btn);
        TextView toggleLoginPage = findViewById(R.id.toggle_login_page);
        invalidEmailPass = findViewById(R.id.invalid_email_pass);
        auth = FirebaseAuth.getInstance();


        //Hide the InvalidEP Text view by Click on TI EditText
        signupEmailEditText.setOnFocusChangeListener((v, hasFocus) -> invalidEmailPass.setVisibility(View.INVISIBLE));
        signupPasswordEditText.setOnFocusChangeListener((v, hasFocus) -> invalidEmailPass.setVisibility(View.INVISIBLE));
        confirmPasswordEditText.setOnFocusChangeListener((v, hasFocus) -> invalidEmailPass.setVisibility(View.INVISIBLE));

        signupEmailLayout.setOnFocusChangeListener((v, hasFocus) -> invalidEmailPass.setVisibility(View.INVISIBLE));
        signupPasswordLayout.setOnFocusChangeListener((v, hasFocus) -> invalidEmailPass.setVisibility(View.INVISIBLE));
        confirmPasswordLayout.setOnFocusChangeListener((v, hasFocus) -> invalidEmailPass.setVisibility(View.INVISIBLE));


        //SignUp Button on Click listener
        signupButton.setOnClickListener(v -> {
            //Validate Details
            ValidationEP validationEP = new ValidationEP();
            if (validationEP.isValid()) {
                signupUser(signup_email, signup_pass);
            } else {
                invalidEmailPass.setVisibility(View.VISIBLE);
            }
        });




        /*----------------------------------------------------------------------------------------------*/
        toggleLoginPage.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
        /*----------------------------------------------------------------------------------------------*/

        //End of onCreate method..
    }

    //Start of external methods
    /*----------------------------------------------------------------------------------------------*/
    /*----------------------------------------------------------------------------------------------*/
    //Create or Sign up user using Firebase Authentication
    private void signupUser(String signup_email, String signup_pass) {
        auth.createUserWithEmailAndPassword(signup_email, signup_pass)
                .addOnCompleteListener(task ->
                {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignupActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignupActivity.this, VerifyEmail.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /*----------------------------------------------------------------------------------------------*/

    private class ValidationEP {
        private boolean isValid() {
            boolean isValidEP = false;
            //Extract Strings from Text Input EdiText
            signup_email = signupEmailEditText.getText().toString();
            signup_pass = signupPasswordEditText.getText().toString();
            confirm_pass = confirmPasswordEditText.getText().toString();


            if (signup_email.isEmpty()) {
                signupEmailEditText.setError("Required");
            } else if (signup_pass.isEmpty()) {
                signupPasswordEditText.setError("Required");
            } else if (confirm_pass.isEmpty()) {
                confirmPasswordEditText.setError("Required");
            } else if (!signup_pass.equals(confirm_pass)) {
                Toast.makeText(SignupActivity.this, "Password doesn't match\nReEnter confirm password", Toast.LENGTH_SHORT).show();
            } else {
                isValidEP = true;
            }
            return isValidEP;
        }
    }


    /*----------------------------------------------------------------------------------------------*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SignupActivity.this, LoginOptions.class);
        startActivity(intent);
        finish();
    }
    /*----------------------------------------------------------------------------------------------*/


}
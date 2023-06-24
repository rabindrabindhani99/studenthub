package com.rabindra.studenthub;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class LoginOptions extends AppCompatActivity {

    LinearLayout signInWithEmail, signInWithGoogle, getSignInWithPhone;
    TextView termsAndCondition;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseUser user=auth.getCurrentUser();
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    int RC_SIGN_IN=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_options);
        signInWithEmail = findViewById(R.id.signIn_with_email);
        signInWithGoogle = findViewById(R.id.signIn_with_google);
        getSignInWithPhone = findViewById(R.id.signIn_with_phone);
        termsAndCondition = findViewById(R.id.terms);


        if(user!=null)
        {
            user.reload();
        }
        if(user!=null && Objects.equals(user.getEmail(), ""))
        {
            startActivity(new Intent(LoginOptions.this, MainActivity.class));
            finish();
        }
        else if (user!= null && user.isEmailVerified()) {
            startActivity(new Intent(LoginOptions.this, MainActivity.class));
            this.finish();
        }
        else if(user!=null && !(user.isEmailVerified()))
        {
            startActivity(new Intent(LoginOptions.this,VerifyEmail.class));

        }


        /*==============================================================================================*/
        //Sign in with E-mail button pressed will take to login activity
        signInWithEmail.setOnClickListener(v -> {
            Intent intent = new Intent(LoginOptions.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
        /*==============================================================================================*/


        //For below terms and condition button
        termsAndCondition.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://mcastudenthub.blogspot.com/p/student-hub-terms-condition.html"));
            startActivity(intent);
        });
        /*==============================================================================================*/



        // Configure Google Sign-In options
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso
        gsc = GoogleSignIn.getClient(this, gso);


        signInWithGoogle.setOnClickListener(v -> {
            Intent signInIntent = gsc.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });



        getSignInWithPhone.setOnClickListener(v -> {
            Intent intent=new Intent(LoginOptions.this,PhoneLogin.class);
            startActivity(intent);
        });


    }

    /*==============================================External Methods=====================================*/
    private void toggleMainActivity() {
        finish();
        Intent intent =new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
    /*===================================================================================================*/



    //On Activity Result for SignIn
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(LoginOptions.this, "Google sign in failed: " + e.getStatusCode(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    //Google SignIn using Firebase authentication credentials
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = auth.getCurrentUser();
                        toggleMainActivity();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(LoginOptions.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /*====================================================================================================*/
    //On Back pressed
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(LoginOptions.this)
                .setTitle("Exit")
                .setMessage("Do you want to exit?")
                .setIcon(R.drawable.applogo)
                .setCancelable(false)
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> LoginOptions.super.onBackPressed())
                .create().show();
    }
    /*================================================================================================*/
}
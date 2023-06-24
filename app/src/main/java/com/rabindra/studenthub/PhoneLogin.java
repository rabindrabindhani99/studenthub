package com.rabindra.studenthub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class PhoneLogin extends AppCompatActivity {

    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private int timeLeftInSeconds=60;
    private TextView resendButton, notGotOtpTV, timerTextView;
    private TextInputEditText otp_TIET,phone_no_TIET;
    private TextInputLayout phone_entry,otpEntry;
    private AppCompatButton sendOtpBtn,verifyOtpBtn,changeNo;
    private String mobile_no, mob_no, authNo,mVerificationId;
    private CountryCodePicker ccp;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);

        //Initialization
        resendButton = findViewById(R.id.resend_otp);
        notGotOtpTV = findViewById(R.id.notGot_otpTV);
        timerTextView = findViewById(R.id.timerTextView);
        phone_no_TIET = findViewById(R.id.phoneET);
        otp_TIET = findViewById(R.id.otpET);
        otpEntry=findViewById(R.id.otp_entry);
        sendOtpBtn = findViewById(R.id.send_otp);
        verifyOtpBtn=findViewById(R.id.verify_otp);
        changeNo=findViewById(R.id.change_no);
        ccp = findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(phone_no_TIET);


        //Check if the number is empty and correct or not then to the further tasks
        sendOtpBtn.setOnClickListener(v -> {
            //changing the mobile no to 10 digit format
            authNo = ccp.getFullNumberWithPlus();
            initiateOtp();
        });


        verifyOtpBtn.setOnClickListener(v -> {
            String entered_otp=otp_TIET.getText().toString().trim().replaceAll("\\s+","");
            if(entered_otp.isEmpty())
            {
                otp_TIET.setError("Required");
            }
            else if(entered_otp.length()<6)
            {
                otp_TIET.setError("Check otp again");
            }
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, entered_otp);
            signInWithPhoneAuthCredential(credential);
        });

        changeNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone_no_TIET.setEnabled(true);
                notGotOtpTV.setVisibility(View.INVISIBLE);
                cancelCountdown();
                resendButton.setVisibility(View.INVISIBLE);
                otpEntry.setVisibility(View.INVISIBLE);
                verifyOtpBtn.setVisibility(View.INVISIBLE);
                changeNo.setVisibility(View.INVISIBLE);
                sendOtpBtn.setVisibility(View.VISIBLE);
            }
        });

        resendButton.setOnClickListener(v ->
        {
            resendOTP();
        });



    }

    private void initiateOtp() {

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token)
            {
                super.onCodeSent(verificationId,token);
                startCountdown();
                otpEntry.setVisibility(View.VISIBLE);
                sendOtpBtn.setVisibility(View.INVISIBLE);
                verifyOtpBtn.setVisibility(View.VISIBLE);
                changeNo.setVisibility(View.VISIBLE);
                mVerificationId = verificationId;
                mResendToken = token;
                phone_no_TIET.setEnabled(false);
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                signInWithPhoneAuthCredential(credential);
                cancelCountdown();
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e)
            {
                Toast.makeText(PhoneLogin.this, "Error:"+e.getMessage()+",\nTry again...", Toast.LENGTH_SHORT).show();
                otpEntry.setVisibility(View.INVISIBLE);
                verifyOtpBtn.setVisibility(View.INVISIBLE);
                cancelCountdown();

            }
        };

        PhoneAuthProvider.getInstance().verifyPhoneNumber
                (
                        authNo,
                        60,
                        TimeUnit.SECONDS,
                        PhoneLogin.this,
                        mCallbacks
                );
    }

    /*=========================================================================================================*/
    //External Methods

    /*------------------------------------------------Timer Methods--------------------------------------------*/

    private void startCountdown() {
        timeLeftInSeconds=60;
        countDownTimer = new CountDownTimer(timeLeftInSeconds * 1000L, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInSeconds = (int) millisUntilFinished / 1000;
                updateResendButton();
                updateTimerTextView();
            }

            @Override
            public void onFinish() {
                timeLeftInSeconds = 0;
                timerRunning = false;
                updateResendButton();
                updateTimerTextView();
                timerTextView.setVisibility(View.INVISIBLE);

            }
        }.start();

        timerRunning = true;
        timerTextView.setVisibility(View.VISIBLE);

    }

    private void cancelCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            timerRunning = false;
            updateResendButton();
            notGotOtpTV.setVisibility(View.INVISIBLE);
            timerTextView.setVisibility(View.INVISIBLE);
            resendButton.setVisibility(View.INVISIBLE);
            updateTimerTextView();
        }
    }

    private void updateResendButton()
    {
        if (!(timeLeftInSeconds > 0 && timerRunning)) {
            resendButton.setVisibility(View.VISIBLE);
            notGotOtpTV.setVisibility(View.INVISIBLE);
        } else {
            resendButton.setVisibility(View.INVISIBLE);
            notGotOtpTV.setVisibility(View.VISIBLE);
        }
    }
    private void updateTimerTextView() {
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d", timeLeftInSeconds);
        timerTextView.setText(getString(R.string.time_left, timeLeftFormatted));
    }
    /*============================================================================================================*/
    //Phone Sign method
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, Transfer to Main Activity
                        Intent intent=new Intent(PhoneLogin.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        // Sign in failed, display a message and update the UI
                        Toast.makeText(this, "Error: "+task.getException()+",\nTry again...", Toast.LENGTH_SHORT).show();
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            Toast.makeText(this, "Error: Invalid Verification Code", Toast.LENGTH_SHORT).show();
                            otp_TIET.setText("");
                            otp_TIET.setError("Enter Correct code");
                        }
                    }
                });
    }


    /*==================================================================================================================*/

    private void resendOTP() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                authNo,
                60,
                TimeUnit.SECONDS,
                PhoneLogin.this,
                mCallbacks,
                mResendToken
        );
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(PhoneLogin.this, LoginOptions.class);
        startActivity(intent);
        finish();
    }
}
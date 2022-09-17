package com.ringolatechapps.pgrooms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class OTP extends AppCompatActivity {

    EditText inputCode_1, inputCode_2, inputCode_3, inputCode_4, inputCode_5, inputCode_6;
    String verificationId;
    TextView mobileNumber, resendOTP;
    Button verifyButton;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        mobileNumber = findViewById(R.id.mobileNumberTextView_id);
        resendOTP = findViewById(R.id.resendOTPAgainTextView_id);
        inputCode_1 = findViewById(R.id.OTP_1_EditText_id);
        inputCode_2 = findViewById(R.id.OTP_2_EditText_id);
        inputCode_3 = findViewById(R.id.OTP_3_EditText_id);
        inputCode_4 = findViewById(R.id.OTP_4_EditText_id);
        inputCode_5 = findViewById(R.id.OTP_5_EditText_id);
        inputCode_6 = findViewById(R.id.OTP_6_EditText_id);
        progressBar = findViewById(R.id.verifyOTPProgressBar_id);
        verifyButton = findViewById(R.id.verifyButton_id);


//        setting the mobile number in screen
        mobileNumber.setText(String.format("+91-%s", getIntent().getStringExtra("mobile")));

//        setting up the inputs for OTP
//        means it moves to the next input when something is typed in the previous input
        setup_OTP_Input();

//        the verification id is very important aspect and it is coming from precious activity
        verificationId = getIntent().getStringExtra("verfication");

//        verify the otp
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyOTP();
            }
        });

//        resend the otp
        resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOTP();
            }
        });

    }

    private void setup_OTP_Input() {
//        the cursor simply moves to the next input when anything is typed
        inputCode_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputCode_2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputCode_3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputCode_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputCode_4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputCode_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputCode_5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputCode_5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputCode_6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void verifyOTP() {
        if (!inputCode_1.getText().toString().trim().isEmpty()
                && !inputCode_2.getText().toString().trim().isEmpty()
                && !inputCode_3.getText().toString().trim().isEmpty()
                && !inputCode_4.getText().toString().trim().isEmpty()
                && !inputCode_5.getText().toString().trim().isEmpty()
                && !inputCode_6.getText().toString().trim().isEmpty()) {

            String code = inputCode_1.getText().toString() +
                    inputCode_2.getText().toString() +
                    inputCode_3.getText().toString() +
                    inputCode_4.getText().toString() +
                    inputCode_5.getText().toString() +
                    inputCode_6.getText().toString();

            if (verificationId != null) {
                progressBar.setVisibility(View.VISIBLE);
                verifyButton.setVisibility(View.INVISIBLE);
                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                        verificationId, code
                );
                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                verifyButton.setVisibility(View.VISIBLE);
                                if (task.isSuccessful()) {
//                                    on successfull verification we have to add user id in firestore database if not exists,
//                                    we have to go to the main page
//                                    and clear all the previous activities
                                    EntryInFirestoreDatabaseIfNotExist();


                                } else {
                                    Toast.makeText(OTP.this, "Enter the Correct otp", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        } else {
            Toast.makeText(OTP.this, "Please enter the otp", Toast.LENGTH_SHORT).show();
        }
    }

    private void resendOTP() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + getIntent().getStringExtra("mobile"),
                60,
                TimeUnit.SECONDS,
                OTP.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                        Toast.makeText(OTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
//                    when code is sent again
                    public void onCodeSent(@NonNull String newVerification, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        verificationId = newVerification;
                        Toast.makeText(OTP.this, "OTP sent again", Toast.LENGTH_SHORT).show();
                    }
                }

        );
    }

    private void EntryInFirestoreDatabaseIfNotExist() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("USERS").document(firebaseUser.getUid()).set(new HashMap<>(), SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                goToDashboard();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(OTP.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goToDashboard() {
        Intent mainPage = new Intent(OTP.this, Dashboard.class);
        mainPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainPage);

    }
}
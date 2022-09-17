package com.ringolatechapps.pgrooms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class getPhoneNumber extends AppCompatActivity {

    EditText mobileNumber;
    Button getOTPButton;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_phone_number);

        mobileNumber = findViewById(R.id.mobileNumberEditText_id);
        getOTPButton = findViewById(R.id.getOTPButton_id);
        progressBar = findViewById(R.id.progressBar_id);

//        listener when get otp button is called
        getOTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOTPToThisNumber();
            }
        });
    }

    private void sendOTPToThisNumber() {
        if (!mobileNumber.getText().toString().trim().isEmpty()) {      // here conditions are checked if the number is not written properly
            if ((mobileNumber.getText().toString().trim()).length() == 10) {


                progressBar.setVisibility(View.VISIBLE);
                getOTPButton.setVisibility(View.INVISIBLE);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + mobileNumber.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        getPhoneNumber.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            //                            these are callbacks and will run according to the action happened
                            @Override
//                            when verification is completed
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                progressBar.setVisibility(View.GONE);
                                getOTPButton.setVisibility(View.VISIBLE);

                            }

                            @Override
//                            when verification is failed
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progressBar.setVisibility(View.GONE);
                                getOTPButton.setVisibility(View.VISIBLE);
                                Toast.makeText(getPhoneNumber.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
//                            when the code is successfully sent to the users phone number
                            public void onCodeSent(@NonNull String verficationid, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                progressBar.setVisibility(View.GONE);
                                getOTPButton.setVisibility(View.VISIBLE);
//                                when the code will be sent , we will head to a new activity for code verification
                                Intent intent = new Intent(getApplicationContext(), OTP.class);
                                intent.putExtra("mobile", mobileNumber.getText().toString());
                                intent.putExtra("verfication", verficationid);
                                startActivity(intent);
                            }
                        }

                );


            } else {
                Toast.makeText(getPhoneNumber.this, "Please enter correct number", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getPhoneNumber.this, "Enter Mobile number", Toast.LENGTH_SHORT).show();
        }
    }
}
package com.ringolatechapps.pgrooms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class uploadRoom extends AppCompatActivity {

    EditText name, cost, seater;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_room);

        name = findViewById(R.id.pgName_id);
        cost = findViewById(R.id.pgCost_id);
        seater = findViewById(R.id.pgSeater_id);
        add = findViewById(R.id.add_id);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRoomToFirebase();
            }
        });
    }

    private void addRoomToFirebase() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        Room customRoom = new Room(name.getText().toString(), cost.getText().toString(), seater.getText().toString());
        firebaseFirestore.collection("USERS").document(firebaseUser.getUid()).set(customRoom).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                firebaseFirestore.collection("ROOMS").document(firebaseUser.getUid()).set(customRoom).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(uploadRoom.this, "done", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
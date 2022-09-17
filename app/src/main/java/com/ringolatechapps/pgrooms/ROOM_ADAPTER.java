package com.ringolatechapps.pgrooms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ROOM_ADAPTER extends ArrayAdapter<Room> {

    public ROOM_ADAPTER(Context context, ArrayList<Room> rooms) {
        super(context, 0, rooms);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listitemView = convertView;


        if (listitemView == null) {

            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.room_layout_row, parent, false);


        }

        Room room = getItem(position);

//        TextView name = listitemView.findViewById(R.id.name);
//        TextView cost = listitemView.findViewById(R.id.cost);
//        TextView seater = listitemView.findViewById(R.id.seater);
//
//        name.setText(room.getPgName());
//        cost.setText(room.getCostPerMonth());
//        seater.setText(room.getSeater());

        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getContext(),RoomInfo.class));
            }
        });


        return listitemView;
    }


}

package com.first.ridima.kamserver;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Srirang on 1/21/2018.
 */

public class Homepage extends AppCompatActivity {

   TextView welcome;
    TextView users;
    String list_of_users;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        welcome= (TextView) findViewById(R.id.welcome);
        users=(TextView) findViewById(R.id.users);
        //  name= (TextView) findViewById(R.id.nametv);
       // name.setText("Helllooooo!");
        list_of_users="";
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference rf1=database.getReference();//ROOT NODE
        DatabaseReference rf2=rf1.child("Names_Of_Users"); //Target node

       rf2.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override

           public void onDataChange(DataSnapshot dataSnapshot) {
               for(DataSnapshot ds:dataSnapshot.getChildren()) {
                   list_of_users += String.valueOf(ds.getValue())+"\n";
               }
               users.setText(list_of_users);


           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });


    }
}

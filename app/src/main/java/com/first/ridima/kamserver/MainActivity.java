
package com.first.ridima.kamserver;

        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.text.TextUtils;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

        import static android.content.ContentValues.TAG;

/**
 * Created by Ridima on 19-01-2018.
 */


public class MainActivity extends AppCompatActivity {

    private TextView tvregister;
    private EditText edittextname;
    private EditText edittextemail;

    private TextView tv2;
    private  EditText edittextPassword;
    private Button buttonregi;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog=new ProgressDialog(this);
        tvregister = (TextView)findViewById(R.id.tvregister);
        edittextname = (EditText)findViewById(R.id.edittextname);
        edittextemail = (EditText)findViewById(R.id.edittextemail);
        tv2 = (TextView) findViewById(R.id.tv2);
        edittextPassword = (EditText)findViewById(R.id.edittextPassword);
        buttonregi = (Button)findViewById(R.id.buttonregi);


        firebaseAuth=FirebaseAuth.getInstance();   //For Firebase Authentication

        buttonregi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();                //Register new user method
            }
        });


    }       //END OF onCreate()





    private void registerUser(){

        //Getting values from text-fields
        final String email = edittextemail.getText().toString().trim();
        String password = edittextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(), "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)   //Pre-built method for Sign up/REGISTER
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){


                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Registered Successfully", Toast.LENGTH_SHORT).show();



                            addUserData();

                            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Could not Regitser..Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void addUserData() {      //Add data to database
     String name=edittextname.getText().toString().trim();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference();  //Starting at ROOT NODE
        DatabaseReference myRef2=myRef.child("Names_Of_Users");

        myRef2.child(name).setValue(name); //Set Key-Value pair

    }


    public void onAlreadyRegistered(View view)
    {
        Toast.makeText(getApplicationContext(),"Already registered user",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);


    }

}



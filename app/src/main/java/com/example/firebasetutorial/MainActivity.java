package com.example.firebasetutorial;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button runCodeButton,readDataButton;
    private EditText inputEditText;
    private EditText inputEditTextAge;
    private TextView outputText;
    private static final String TAG="my tag";

    //database class objects:
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

   // private ValueEventListener mListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        mDatabase = FirebaseDatabase.getInstance();

        mRef = mDatabase.getReference("users");
        //the getReference function has various variations. this variation returns the reference of
        // the firebase root node
        //this replaces the distinct data into one single node.
        // mRef = mDatabase.getReference();

        /*mListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String message = dataSnapshot.getValue(String.class);
                outputText.setText(message);
                Log.d(TAG, "on data change : name : "+message );
                Toast.makeText(MainActivity.this, "data read successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        //now we will not call the read data object, we will use this line to get real time read updates.
        //this listener is attached to the child node of mref:
        mRef.child("user1").addValueEventListener(mListener);
        */

        runCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = inputEditText.getText().toString();
                int age;

                    age = Integer.parseInt(inputEditTextAge.getText().toString());

                    /*here we call two seperate APIs for updatimg the name and age, thus,
                    * first the name will be updated and then the age wil be updated
                    * Each call of setValue will call the API once, thus the values updation will be such.*/



                    //setting age
                    mRef.child("user1").child("age").setValue(age).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MainActivity.this, "Toast inside on success listener..DATA INSERTED..",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "on failure"+e.getMessage());
                            Toast.makeText(MainActivity.this, "Toast inside on failure listener..EXCEPTION..",Toast.LENGTH_SHORT).show();
                        }
                    });




                //users node now consists a key value pair
                //seting name
                mRef.child("user1").child("name").setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Toast inside on success listener..DATA INSERTED..",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "on failure"+e.getMessage());
                        Toast.makeText(MainActivity.this, "Toast inside on failure listener..EXCEPTION..",Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });


        readDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRef.child("user1").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map<String,Object> data= (Map<String, Object>) dataSnapshot.getValue();

                        Log.d(TAG,"on data changed : name :"+data.get("name"));
                        Log.d(TAG,"on data changed : age :"+data.get("age"));

                        Toast.makeText(MainActivity.this, "data read successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //this part is for error handling, we r not writting anything here.
                    }
                });
            }
        });




    }

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        //here we will see that there is only option to remove child event listener and value event listener.
        mRef.child("user1").removeEventListener(mListener);
    }*/

    private void initViews() {
        runCodeButton = this.findViewById(R.id.run_code_button);
        readDataButton = this.findViewById(R.id.read_data_button);
        inputEditText = this.findViewById(R.id.edit_text);
        inputEditTextAge = this.findViewById(R.id.edit_text_age);
        outputText = this.findViewById(R.id.text_view);
    }
}

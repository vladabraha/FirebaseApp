package cz.uhk.fim.brahavl1.firebaseapp;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    TextView textView;

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textView = findViewById(R.id.textView);

        database = FirebaseDatabase.getInstance();

//        DatabaseReference myRef = database.getReference("settings"); //tady je to ten klic v databazi, podle toho se to taha z databaze a hned zobrazi, pripadne das klic messages
        DatabaseReference myRef = database.getReference("messages"); //tady je to ten klic v databazi, podle toho se to taha z databaze a hned zobrazi, pripadne das klic messages
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                dataSnapshot.getKey(); //prijde settings
                String result = "";
                for(DataSnapshot oneSettings : dataSnapshot.getChildren()) {
                    result += oneSettings.getKey() + ":" + oneSettings.getValue() + "\n";

                }
                textView.setText(result);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //todo nacitat vsechnz zpravy z db
    }

    public void onSendMessageClick(View v){

        EditText editTextMessage = findViewById(R.id.editTextMessage);

        String message = editTextMessage.getText().toString();

        editTextMessage.setText("");

        DatabaseReference myRef = database.getReference("messages/prvni-zprava");

        myRef.setValue(message);


        //todo odeslat na server do databate
    }
}

package cz.uhk.fim.brahavl1.firebaseapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance(); //singleton - vzdycky se dostaneme na instanci

        if(mAuth.getCurrentUser() != null){ //tohle přeskočí rovnou do daný aktivity, pokud už je člověk registrovanej, pozor na zpět se to vrátí zpátky
            startActivity(new Intent(this, HomeActivity.class));
        }
    }

    public void signIn(View v){ //nazev button metody
        EditText editTextEmail = findViewById(R.id.editLogin);
        EditText editTextPassword = findViewById(R.id.editPassword);

        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override //asynchroni task na sever
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Toast.makeText(MainActivity.this, "UID " + task.getResult().getUser().getUid(), Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(MainActivity.this, "Chyba " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}

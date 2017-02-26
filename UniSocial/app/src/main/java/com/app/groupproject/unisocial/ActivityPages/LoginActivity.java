package com.app.groupproject.unisocial.ActivityPages;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.groupproject.unisocial.R;
import com.app.groupproject.unisocial.RegisterData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener fAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText email = (EditText) findViewById(R.id.emailentered_id);
        EditText passw = (EditText) findViewById(R.id.passwordentered_id);

        String emailStr = email.getText().toString();
        String passwStr = passw.getText().toString();


        Button signin = (Button) findViewById(R.id.sign_in_button_id);
        Button register = (Button) findViewById(R.id.register_button_id);


        fAuth.signInWithEmailAndPassword(emailStr,passwStr).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(start);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(LoginActivity.this,EventListActivity.class);
                startActivity(start);
            }
        });




















    }
}

package com.alejandromartinezremis.airquailitygijon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alejandromartinezremis.airquailitygijon.BuildConfig;
import com.alejandromartinezremis.airquailitygijon.R;
import com.alejandromartinezremis.airquailitygijon.db.AppDatabase;
import com.alejandromartinezremis.airquailitygijon.utils.Utils;

public class LoginActivity extends AppCompatActivity {
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.db = Utils.createDb(this);
        if(BuildConfig.DEBUG){
            ((LinearLayout)findViewById(R.id.layoutDebugButtons)).setVisibility(View.VISIBLE);
        }
    }

    public void onClick(View v){
        String username = ((EditText)findViewById(R.id.editTextUser)).getText().toString();
        String password = ((EditText)findViewById(R.id.editTextPassword)).getText().toString();
        String passwordFromDb = db.userDao().getPassword(username);

        if(password.equals(passwordFromDb))
            startActivity(new Intent(this, MainActivity.class));
        else{
            ((EditText)findViewById(R.id.editTextPassword)).setText("");
            ((TextView)findViewById(R.id.textViewLoginError)).setVisibility(View.VISIBLE);
        }
    }

    public void onClickDebugAdd(View v){
        String username = ((EditText)findViewById(R.id.editTextUser)).getText().toString();
        String password = ((EditText)findViewById(R.id.editTextPassword)).getText().toString();
        try{
            db.userDao().insertAll(Utils.createUser(username, password));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void onClickDebugRemoveUser(View v){
        String username = ((EditText)findViewById(R.id.editTextUser)).getText().toString();
        try{
            db.userDao().deleteUser(username);
        }catch(Exception e){
        e.printStackTrace();
        }
    }
    public void onClickDebugRemoveAllUsers(View v){
        try{
            db.userDao().deleteAllUsers();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
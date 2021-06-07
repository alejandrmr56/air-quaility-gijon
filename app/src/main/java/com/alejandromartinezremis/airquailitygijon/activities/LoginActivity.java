package com.alejandromartinezremis.airquailitygijon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.alejandromartinezremis.airquailitygijon.BuildConfig;
import com.alejandromartinezremis.airquailitygijon.R;
import com.alejandromartinezremis.airquailitygijon.db.AppDatabase;
import com.alejandromartinezremis.airquailitygijon.utils.Utils;

/**
 * Activity that displays the login screen
 */
public class LoginActivity extends AppCompatActivity {
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.db = Utils.createDb(this);
        if(BuildConfig.DEBUG){
            findViewById(R.id.layoutDebugButtons).setVisibility(View.VISIBLE);
        }
    }

    /**
     * Handles the click of the login button
     * @param v the login button
     */
    public void onClick(View v){
        String username = ((EditText)findViewById(R.id.editTextUser)).getText().toString();
        String password = ((EditText)findViewById(R.id.editTextPassword)).getText().toString();
        String passwordFromDb = db.userDao().getPassword(username);

        if(password.equals(passwordFromDb))
            startActivity(new Intent(this, MainActivity.class));
        else{
            ((EditText)findViewById(R.id.editTextPassword)).setText("");
            findViewById(R.id.textViewLoginError).setVisibility(View.VISIBLE);
        }
    }

    /**
     * Handles the click on the add user button
     * @param v the add user button
     */
    public void onClickDebugAdd(View v){
        String username = ((EditText)findViewById(R.id.editTextUser)).getText().toString();
        String password = ((EditText)findViewById(R.id.editTextPassword)).getText().toString();
        try{
            db.userDao().insertAll(Utils.createUser(username, password));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Handles the click on the remove user button
     * @param v the remove user button
     */
    public void onClickDebugRemoveUser(View v){
        String username = ((EditText)findViewById(R.id.editTextUser)).getText().toString();
        try{
            db.userDao().deleteUser(username);
        }catch(Exception e){
        e.printStackTrace();
        }
    }

    /**
     * Handles the click on the remove all users button
     * @param v the remove all users button
     */
    public void onClickDebugRemoveAllUsers(View v){
        try{
            db.userDao().deleteAllUsers();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
package com.alejandromartinezremis.airquailitygijon;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onImageClick(View w){
        Intent intent = new Intent(this, StationActivity.class);
        startActivity(intent);
    }


    /* Menu-related code below this line */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                updateData();
                break;
            case R.id.menu_info:
                displayInfoAlertDialog(this);
                break;
            case R.id.menu_about:
                displayAboutAlertDialog(this);
                break;
        }
        return true;
    }


    private void updateData(){
        throw new UnsupportedOperationException("Not implemented yet"); //TODO: Implement this
    }

    private void displayInfoAlertDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_AirQuailityGijon_Dialog);
        builder.setTitle(R.string.menu_info_alert_dialog_title)
                .setMessage(R.string.menu_info_alert_dialog_message)
                .setPositiveButton(R.string.ok, null)
                .create().show();
    }

    private void displayAboutAlertDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_AirQuailityGijon_Dialog);
        builder.setTitle(R.string.menu_about_alert_dialog_title)
                .setMessage(R.string.menu_about_alert_dialog_message)
                .setPositiveButton(R.string.ok, null)
                .create().show();
    }
    /* End of menu-related code */
}
package com.example.amitbaria.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class AdminHomeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        getAdminData();



    }

    public void getAdminData()
    {
        Intent intent = getIntent();
      /*  String message = intent.getStringExtra("username");
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        */

          try {
              String CompleteJSONUserResponseData = intent.getStringExtra("CompleteJSONUserResponseData");

              JSONObject object = new JSONObject(CompleteJSONUserResponseData);
              //String userName = object.getString("userName");
              TextView username = (TextView) findViewById(R.id.adminName);

              username.setText("Hi ," + object.getString("userName"));

            }catch(JSONException e)
          {

              Log.d(" JSON...Exception e......" , e.getMessage());
          }




    }


    @Override
    public void onBackPressed() {

      // for alert dialog....
      setCloseAllActivities();

    }


   public void  setCloseAllActivities()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(AdminHomeActivity.this);

        // Set a title for alert dialog
        builder.setTitle("Exit Warning");

        // Ask the final question
        builder.setMessage("Are you sure to Exit?");

        // Set the alert dialog yes button click listener
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

              // It will finish all Stack activities
                finishAffinity();
            }
        });

        // Set the alert dialog no button click listener
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when No button clicked

            }
        });

        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

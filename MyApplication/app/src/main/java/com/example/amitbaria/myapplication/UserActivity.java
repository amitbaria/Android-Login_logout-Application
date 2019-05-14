package com.example.amitbaria.myapplication;

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


public class UserActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getUserData();

    }

      public void getUserData()
      {
            try {
                Intent intent = getIntent();

                   //  Read user ProfileData............from Server JSOnString......
                String CompleteJSONUserResponseData = intent.getStringExtra("CompleteJSONUserResponseData");

                JSONObject object = new JSONObject(CompleteJSONUserResponseData);
                   //String userName = object.getString("userName");
                   TextView username=(TextView)findViewById(R.id.username);

                         username.setText("Hi ,"+object.getString("userName"));




            }catch(JSONException e)
            {
                 Log.d("JSON Exception.......", e.getMessage());

            }




          //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

      }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
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

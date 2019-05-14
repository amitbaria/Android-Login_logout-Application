package com.example.amitbaria.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpConnection;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import java.util.*;


public class CreateAccountActivity extends ActionBarActivity {

    private final String RegistrationURL="http://10.0.2.2:8080/newUserRegistration";

    private  EditText email=null;
    private   EditText username=null;
    private  EditText password=null;
    private  EditText confirmPassword=null;
    private   EditText mobile=null;

    private String securityQuestion=null;

    private EditText  securityAnswerEdit=null;
    private String securityAnswer=null;

    ProgressDialog  progressDialog=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        username=  (EditText)findViewById(R.id.usrusr);
        email =  (EditText)findViewById(R.id.email);
        mobile=  (EditText)findViewById(R.id.mobile);
        password=  (EditText)findViewById(R.id.pswrdd);
        confirmPassword=  (EditText)findViewById(R.id.confirmpassword);
        securityAnswerEdit=(EditText)findViewById(R.id.SecurityAnswer);

        // temporary
          username.setText("amit");
          email.setText("baria.amit@yahoo.com");
          mobile.setText("3344555");

          password.setText("abc");
          confirmPassword.setText("abc");

        securityAnswerEdit.setText("This is my Security Answer");

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener


        // careate Security Question Drop down.......
          createSecurityQuestion(spinner);

        // event performed on Security Spinner

        spinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {

           @Override
           public void onItemSelected(AdapterView <?> parent, View view, int position, long id)
           {

                 Log.d("postition....",String.valueOf(position));

                switch(position)
                {
                    case 0:
                         securityQuestion  ="nothing";
                        break;
                    case 1:
                        securityQuestion  =parent.getItemAtPosition(position).toString();
                        break;

                    case 2:securityQuestion  =parent.getItemAtPosition(position).toString();
                        break;
                    case 3:securityQuestion  =parent.getItemAtPosition(position).toString();
                        break;
                    case 4:securityQuestion  =parent.getItemAtPosition(position).toString();
                        break;

                    case 5:securityQuestion  =parent.getItemAtPosition(position).toString();
                        break;
                    case 6:securityQuestion  =parent.getItemAtPosition(position).toString();
                        break;

                }

               Log.d("securityQuestion",securityQuestion);


           }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }

        });


           TextView lin=(TextView)findViewById(R.id.lin);

             lin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new HTTPAsyncTask().execute(RegistrationURL);
            }

        });


    }

  public void  createSecurityQuestion(Spinner spinner)
    {
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Security Question");
        categories.add("What is Your nick name");
        categories.add("What is your Birth home town");
        categories.add("What is the Name of your Father");
        categories.add("What is the name of your Mother");

        categories.add("What is your date of Birth(MM-DD-YYYY)");
        categories.add("What is your Favourite Colour");

        // Creating adapter for spinner
       // ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.custom_textview_to_spinner, categories);

        // Drop down layout style - customized
       // dataAdapter.setDropDownViewResource(R.layout.custom_textview_to_spinner);

        // attaching data adapter to spinner
       // spinner.setAdapter(dataAdapter);



//
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.custom_textview_to_spinner,categories){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.custom_textview_to_spinner);
        spinner.setAdapter(spinnerArrayAdapter);




    }


//  public void   performedOnSignupButton(View w) {
//
//      //  http://172.16.10.219:8080/newUserRegistration email=  (EditText)findViewById(R.id.email);
//
//      String usernameTxt = username.getText().toString();
//      String emailTxt = email.getText().toString();
//      String mobileTxt = mobile.getText().toString();
//      String passwordTxt = password.getText().toString();
//      String confirmpasswordTxt = password.getText().toString();
//
//
//      if ((passwordTxt != "" && confirmpasswordTxt != "") && (passwordTxt.equals(confirmpasswordTxt))) {
//
//
//
//          Toast.makeText(getApplicationContext(), "Request to be sent to Server", Toast.LENGTH_SHORT).show();
//         // new MyAsyncTasks().execute(RegistrationURL);
//
//
//
//      } else {
//          Toast.makeText(getApplicationContext(), "Password and Confirm Password must be same", Toast.LENGTH_SHORT).show();
//
//      }
//
//  }


    private class HTTPAsyncTask extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experiance
            progressDialog = new ProgressDialog(CreateAccountActivity.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }


        @Override
        protected String doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                try {
                    return HttpPost(urls[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return "Error!";
                }
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            Log.d("Result..................",result);
            progressDialog.cancel();

            gotoAcknowledgeUI();


        }
    }//  AsynTask class close

    private String HttpPost(String myUrl) throws IOException, JSONException {
        String result = "";

        URL url = new URL(myUrl);

        // 1. create HttpURLConnection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        // 2. build JSON object
        JSONObject jsonObject = buidJsonObject();

        // 3. add JSON content to POST request body
        setPostRequestContent(conn, jsonObject);

        // 4. make POST request to the given URL
        conn.connect();

        // 5. return response message
        return conn.getResponseMessage()+"";

    }

    private JSONObject buidJsonObject() throws JSONException {



        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("userName", username.getText().toString());
        jsonObject.accumulate("userEmail",  email.getText().toString());
        jsonObject.accumulate("userPassword",   password.getText().toString());


        jsonObject.accumulate("securityQuestion",  securityQuestion);



        jsonObject.accumulate("securityAnswer",  securityAnswerEdit.getText().toString());
        jsonObject.accumulate("userPhoneno",  mobile.getText().toString());
        jsonObject.accumulate("isAdmin",  "no");
        jsonObject.accumulate("enabled",  0);



          try {
              String input=new Date().toString();
              SimpleDateFormat parser = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
              Date date = parser.parse(input);
              SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
              String formattedDate = formatter.format(date);
              jsonObject.accumulate("datetime",formattedDate);

          }catch(Exception e)
          {
              Log.d("Exception in date.......",e.getMessage());
              Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
          }

             return jsonObject;
    }


    private void setPostRequestContent(HttpURLConnection conn,
                                       JSONObject jsonObject) throws IOException {

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(jsonObject.toString());
       // Log.i(MainActivity.class.toString(), jsonObject.toString());
        writer.flush();
        writer.close();
        os.close();
    }

   public void  gotoAcknowledgeUI()
   {
          setContentView(R.layout.success_registionacknowledgement);
          TextView ack=   (TextView)   findViewById(R.id.acknowledge);


          ack.setText("Plz. check your Registered Email id,  For activation Login...");







   }
   public void  performedOnLoginButton(View w)
   {
       startActivity(new Intent(this,MainActivity.class));


   }

    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_create_account, menu);
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

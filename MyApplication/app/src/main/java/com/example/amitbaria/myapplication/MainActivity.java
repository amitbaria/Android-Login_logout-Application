package com.example.amitbaria.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends ActionBarActivity {
  private  ProgressDialog progressDialog;
   private TextView loginButton;
   private EditText pswrdd;
    private EditText      usrusr;

    private final String LOGINURL="http://10.0.2.2:8080/loginUser";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


         loginButton =(TextView)findViewById(R.id.lin);
          pswrdd=(EditText)findViewById(R.id.pswrdd);
          usrusr= (EditText)findViewById(R.id.usrusr);
    }


     public void performedOnLoginButton(View w)
     {
          boolean flag=false;

         Intent intent=null;
          if( usrusr.getText().toString()!="" && pswrdd.getText().toString()!="")
          {

                 new HTTPAsyncTask().execute(LOGINURL);
//              Toast.makeText(getApplicationContext(),"Go to user Page",Toast.LENGTH_SHORT).show();

          }
       /*  else if(usrusr.getText().toString().equals("admin") && pswrdd.getText().toString().equals("1234"))
          {
              // Redirect to admin Home Activity

              intent = new Intent(this, AdminHomeActivity.class);
              Toast.makeText(getApplicationContext(),"Go to Admin Page",Toast.LENGTH_SHORT).show();
              flag=true;
          }*/
         else
          {
              Toast.makeText(getApplicationContext(),"Plz. try again",Toast.LENGTH_SHORT).show();

          }
       /*  if(flag==true)
         {
             intent.putExtra("username",usrusr.getText().toString());
             startActivity(intent) ;
         }*/
     }


    //  for create Account action
    public void createAccountLink(View w)
    {
          Intent  intent = new Intent(this, CreateAccountActivity.class);
          startActivity(intent) ;

    }


    private class HTTPAsyncTask extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experiance
            progressDialog = new ProgressDialog(MainActivity.this);
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

            Log.d("Result..................", result);
            progressDialog.cancel();



            if(result.equals("Not verified"))
            {
                Toast.makeText(getApplicationContext(),"Invalid User Or Inactive User",Toast.LENGTH_SHORT).show();

            }
            else {
                     // either user is Simple User or Admin
                try {
                    JSONObject jsonResponse = new JSONObject(result);
                          //  check json isAdmin Key
                       if(jsonResponse.getString("isAdmin").equals("yes"))
                       {

                           goAdminPage(result);
                       }
                    else
                       {
                           gotoUserPage(result);
                       }



                }catch(JSONException e)
                {
                     Log.d("JSON Exeption ................",e.getMessage());

                }





                 }
        }
    }//  AsynTask class close

   public void gotoUserPage(String response)
   {
         Intent  intent = new Intent(this, UserActivity.class);
         intent.putExtra("CompleteJSONUserResponseData",response);

          startActivity(intent) ;

   }

    public void goAdminPage(String response)
    {
        Intent  intent = new Intent(this, AdminHomeActivity.class);
        intent.putExtra("CompleteJSONUserResponseData",response);

        startActivity(intent) ;


    }

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
        //Toast.makeText(getApplicationContext(),conn.getResponseMessage(),Toast.LENGTH_SHORT).show();


        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        StringBuilder sb = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }

        Log.d("Result.......",sb.toString());
        return sb.toString();


    }

    private JSONObject buidJsonObject() throws JSONException {



        JSONObject jsonObject = new JSONObject();


        jsonObject.accumulate("userid", usrusr.getText().toString());
       // jsonObject.accumulate("userEmail",  email.getText().toString());
          jsonObject.accumulate("userPassword",   pswrdd.getText().toString());
      //  jsonObject.accumulate("securityQuestion",  "under Construction");
       // jsonObject.accumulate("securityAnswer",  "Under Construction");
      //  jsonObject.accumulate("userPhoneno",  mobile.getText().toString());
      //  jsonObject.accumulate("isAdmin",  "no");
       // jsonObject.accumulate("enabled",  0);

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




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

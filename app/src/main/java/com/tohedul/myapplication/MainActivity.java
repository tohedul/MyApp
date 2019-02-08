package com.tohedul.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ImageView imageView;
    EditText etName,etEmail,etPhone,etAddress;
    Spinner spinner;
    Button regButton,btnAllUsers;
    String profilePic="";
    String gender="";
    String apiUrl;
    String dataUrl="http://syntecinternapi.azurewebsites.net/api/user/getusers";
    String response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Camera button click
        Button btnCamera = (Button)findViewById(R.id.btnCamera);
        imageView = (ImageView)findViewById(R.id.imageView);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });

        //EditTexts
        etName=(EditText)findViewById(R.id.editTextName);
        etEmail= (EditText)findViewById(R.id.editTextEmail);
        etPhone= (EditText)findViewById(R.id.editTextPhone);
        etAddress= (EditText)findViewById(R.id.editTextAddress);

        //Gender dropdown
        spinner= findViewById(R.id.spinnerGender);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //Reg button
        regButton=findViewById(R.id.btnSubmit);

        //regButton Listener
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=etName.getText()+"";
                String email=etEmail.getText()+"";
                String address=etAddress.getText()+"";
                String phone=etPhone.getText()+"";
                apiUrl="http://syntecinternapi.azurewebsites.net/api/user/CreateNew";
                final JSONObject data=new JSONObject();
                try {
                    data.put("Name", name);
                    data.put("Email",email);
                    data.put("Phone",phone);
                    data.put("Address", address);
                    data.put("Gender", gender);
                    data.put("ProfilePic",profilePic);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                new sendData().execute();

                Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_LONG).show();

            }
        });

        //View all Users button
        btnAllUsers= findViewById(R.id.btnAllUsers);
        btnAllUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new usersData().execute();
                if(response!=null){
                    Intent intent=new Intent(MainActivity.this, UserViewActivity.class);
                    intent.putExtra("users", response);
                    startActivity(intent);
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        profilePic = Base64.encodeToString(byteArray, Base64.DEFAULT); //profile pic to Base64 format
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        gender=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    public class sendData extends AsyncTask<String, String, String>{



        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url = new URL(apiUrl);
                HttpURLConnection con=(HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.connect();


            } catch (MalformedURLException  e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }
    }
    public class usersData extends AsyncTask<String, String, String>{

        @Override
        protected void onPostExecute(String s) {
            response=s;


        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url = new URL(dataUrl);
                HttpURLConnection con=(HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();

                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(con.getInputStream()));
                response=bufferedReader.readLine();

            } catch (MalformedURLException  e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }
    }
}

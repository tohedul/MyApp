package com.tohedul.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class UserViewActivity extends AppCompatActivity {
    Button register;
    TextView dataView;
    String userData;
    JSONObject jsonObject;
    JSONArray jsonArray;
    UserAdapter userAdapter;
    ListView listView;
    String name,email,address,phone,gender,profilePic;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);

        userAdapter=new UserAdapter(this,R.layout.row_layout);
        listView=findViewById(R.id.listview);
        listView.setAdapter(userAdapter);

        userData=getIntent().getExtras().getString("users");

        try {
            jsonObject=new JSONObject(userData);
            jsonArray=jsonObject.getJSONArray("Data");
            int count=0;

            while(count<jsonArray.length()){
                JSONObject jObject=jsonArray.getJSONObject(count);
                name=jObject.getString("Name");
                email=jObject.getString("Email");
                phone=jObject.getString("Phone");
                address=jObject.getString("Address");
                gender=jObject.getString("Gender");
                profilePic=jObject.getString("ProfilePic");
                id=Integer.parseInt(jObject.getString("ID"));

                User user=new User(id,name,email,address,phone);
                userAdapter.add(user);
                count++;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        register=findViewById(R.id.btnRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}

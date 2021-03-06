package com.example.mandatoryassignment;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {
    MyRecyclerViewAdapter adapter;
    static ArrayList<String>teachers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // data to populate the RecyclerView with
        teachers = new ArrayList<>();
        Serverconnector();


        // set up the RecyclerView
        Log.d("onCreate->","method2");


        RecyclerView recyclerView = findViewById(R.id.rvAnimals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, teachers);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);



    }

    private void Serverconnector() {
        servercall servercall = new servercall();

        try {
            JSONArray courseslist=new JSONArray(servercall.execute().get());
            for (int i = 0; i <courseslist.length() ; i++) {
                JSONObject courses = courseslist.getJSONObject(i);
                teachers.add(courses.get("courseName").toString());
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, Course1.class);
        intent.putExtra("teacher", teachers.get(position));
        startActivity(intent);


    }




    public static class servercall extends AsyncTask<String, String, String> {
        ArrayList<String> coursesList= new ArrayList<>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {

            String reponse=null;
            String webapiadress = "http://10.149.88.167:8888/all";
            URL url;
            try {
                url = new URL(webapiadress);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
                reponse=bf.readLine();


                Log.d(" dointeachers size->","method");
            } catch (Exception e) {
                e.printStackTrace();


            }

            return reponse;
        }


    }
}












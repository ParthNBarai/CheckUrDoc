package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Confirmed_appointments extends AppCompatActivity {


    RecyclerView recyclerView;
    HomeScreen_Adapter adapter;
    List<HomeScreen_Data> dataList;
    private HomeScreen_Adapter.RecyclerViewClickListener listener;
    ArrayList<String> patientUsername;
    ArrayList<String> patientSickness;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmed_appointments);

        recyclerView = findViewById(R.id.recview);
        pd = new ProgressDialog(this);
        dataList = new ArrayList<>();
        patientUsername = new ArrayList<>();
        patientSickness = new ArrayList<>();


        extractData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        setOnClickListener();
        adapter = new HomeScreen_Adapter(this,dataList,listener);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(Confirmed_appointments.this,DividerItemDecoration.VERTICAL));

    }

    private void setOnClickListener() {

        listener = new HomeScreen_Adapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
//                int index = position;
//                Log.d("index" , Integer.toString(index));
//                String name = dataList.get(position).getName();
//                String patientUsername = getArguments().getString("username");
                Log.d("fdocname", patientUsername.get(position));
                Bundle extras = getIntent().getExtras();
                String DocUName = extras.getString("docUserName");
                Log.d("docUser",DocUName);
                Intent intent = new Intent(Confirmed_appointments.this, apppointment_history.class);
                Bundle bundle = new Bundle();
                bundle.putString("docUName",DocUName);
                bundle.putString("patientUserName", patientUsername.get(position));
//                bundle.putString("patientSickness", patientSickness.get(position));

                intent.putExtras(bundle);
                startActivity(intent);
            }

        };
    }

    private void extractData() {

        Bundle extras = getIntent().getExtras();
        String DocForName = extras.getString("docUserName");

        Log.d("docUser",DocForName);

        RequestQueue requestQueue;


        String url = "https://checkurdoc.herokuapp.com/appointment/"+ DocForName+"/xyz";

        requestQueue = Volley.newRequestQueue(this);




        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("response",response.toString());
                pd.dismiss();
                try {

                    for(int i=0;i<response.length();i++){
                        JSONObject dataobj = response.getJSONObject(i);

                        String var = dataobj.getString("appointment_status").toString();
                        int var1 = Integer.parseInt(var);

                        Log.d("app_status",Integer.toString(var1));

                        if(var1 == 1) {
                            HomeScreen_Data data = new HomeScreen_Data();

                            patientUsername.add(dataobj.getString("username").toString());
//                        patientSickness.add(dataobj.getString("sickness").toString());

                            data.setName(dataobj.getString("patient").toString());
                            data.setAddress(dataobj.getString("sickness").toString());
                            dataList.add(data);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(Confirmed_appointments.this));
                adapter = new HomeScreen_Adapter(Confirmed_appointments.this,dataList,listener);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Log.d("error", "onError: "+ error.getMessage());
            }
        });

        requestQueue.add(jsonArrayRequest);

    }
}
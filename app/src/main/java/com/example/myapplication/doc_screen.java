package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;

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

public class doc_screen extends AppCompatActivity {

    Button btn;
    EditText numTxt;
    String sNUm;
    ImageButton history;
    Toolbar toolbar;

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
        setContentView(R.layout.activity_doc_screen);

        recyclerView = findViewById(R.id.recview);
        pd = new ProgressDialog(this);
        dataList = new ArrayList<>();
        patientUsername = new ArrayList<>();
        patientSickness = new ArrayList<>();
//        menu = findViewById(R.id.menu);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
        history = findViewById(R.id.historyButton);





        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle extras = getIntent().getExtras();
                String username = extras.getString("DocLoginName");
                Intent intent = new Intent(doc_screen.this,Confirmed_appointments.class);
                Bundle bundle = new Bundle();
                bundle.putString("docUserName", username);
//                bundle.putString("patientUserName", patientUsername.get());

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        

        extractData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        setOnClickListener();
        adapter = new HomeScreen_Adapter(this,dataList,listener);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(doc_screen.this,DividerItemDecoration.VERTICAL));
    }





    private void setOnClickListener() {
        listener = new HomeScreen_Adapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
//                int index = position;
//                Log.d("index" , Integer.toString(index));
//                String name = dataList.get(position).getName();
//                String patientUsername = getArguments().getString("username");
                Log.d("fdocname",patientUsername.get(position));
                Bundle extras = getIntent().getExtras();
                String DocName = extras.getString("DocLoginName");
                Log.d("docUser",DocName);
                Intent intent = new Intent(doc_screen.this,Doc_confirm.class);
                Bundle bundle = new Bundle();
                bundle.putString("docUserName", DocName);
                bundle.putString("patientUserName", patientUsername.get(position));
//                bundle.putString("patientSickness", patientSickness.get(position));

                intent.putExtras(bundle);
                startActivity(intent);
            }
        };
    }

    private void extractData() {
        pd.setMessage("Please Wait.");
        pd.show();

        Bundle extras = getIntent().getExtras();
        String DocLName = extras.getString("DocLoginName");

        Log.d("docName",DocLName);
        RequestQueue requestQueue;


        String url = "https://checkurdoc.herokuapp.com/appointment/"+ DocLName+"/xyz";

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

                        HomeScreen_Data data = new HomeScreen_Data();

                        if(var1 == 0) {



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

                recyclerView.setLayoutManager(new LinearLayoutManager(doc_screen.this));
                adapter = new HomeScreen_Adapter(doc_screen.this,dataList,listener);
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
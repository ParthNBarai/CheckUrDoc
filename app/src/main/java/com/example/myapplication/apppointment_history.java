package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class apppointment_history extends AppCompatActivity {

    Spinner spinnermeds,spinnerbrand,spinnerdose;
    Button prescribe;
    ArrayList<String> meds = new ArrayList<>();
    ArrayList<String> brand = new ArrayList<>();
    ArrayList<String> dose = new ArrayList<>();
    ArrayAdapter<String> medsAdapter;
    ArrayAdapter<String> brandAdapter;
    ArrayAdapter<String> doseAdapter;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apppointment_history);
        pd = new ProgressDialog(this);
        spinnermeds = findViewById(R.id.spinnermeds);
        spinnerbrand = findViewById(R.id.spinnerbrand);
        spinnerdose = findViewById(R.id.spinnerdose);
        prescribe = findViewById(R.id.prescribe);

        Bundle bundle = getIntent().getExtras();
        String docUsername = bundle.getString("docUName");

        Log.d("docUserc",docUsername);

        getmedsdata();
        getbranddata();
        getdosedata();


        prescribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData();
            }
        });








    }

    private void postData() {

        pd.setMessage("Please Wait.");
        pd.show();
        Bundle bundle = getIntent().getExtras();
        String patUsername = bundle.getString("patientUserName");
        String docUsername = bundle.getString("docUName");

        Log.d("docUserc",docUsername);

        String url = "https://checkurdoc.herokuapp.com/prescreption/-";

        RequestQueue requestQueue;

        requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                pd.dismiss();

                Log.d("bookapp",response.toString());
                Toast.makeText(apppointment_history.this, "Prescribed Successfully!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(apppointment_history.this, Confirmed_appointments.class);

                Bundle extras = new Bundle();
                extras.putString("docUserName",docUsername);
                intent.putExtras(extras);
                    startActivity(intent);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Log.d("error", "onError: "+ error.getMessage());
                Toast.makeText(apppointment_history.this, "Please try again!", Toast.LENGTH_SHORT).show();
            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",patUsername);
                params.put("name",spinnermeds.getSelectedItem().toString());
                params.put("brand",spinnerbrand.getSelectedItem().toString());
                params.put("quantity",spinnerdose.getSelectedItem().toString());
                params.put("duration","Twice a day!");
                params.put("doctor",docUsername);
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }





    private void getdosedata() {

        pd.setMessage("Please Wait.");
        pd.show();

        RequestQueue requestQueue ;
        requestQueue =Volley.newRequestQueue(apppointment_history.this);
        String url = "https://checkurdoc.herokuapp.com/medicine";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                pd.dismiss();
                Log.d("meds",response.toString());
                try {

                    for(int i=0;i<response.length();i++){
                        JSONObject dataobj = response.getJSONObject(i);


                        String medsdose = dataobj.getString("quantity");


                        dose.add(medsdose);

                        doseAdapter = new ArrayAdapter<>(apppointment_history.this, android.R.layout.simple_spinner_item,dose);
                        doseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerdose.setAdapter(doseAdapter);



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                pd.dismiss();

            }
        });

        requestQueue.add(jsonArrayRequest);


    }

    private void getbranddata() {

        pd.setMessage("Please Wait.");
        pd.show();

        RequestQueue requestQueue ;
        requestQueue =Volley.newRequestQueue(apppointment_history.this);
        String url = "https://checkurdoc.herokuapp.com/medicine";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                pd.dismiss();

                Log.d("meds",response.toString());
                try {

                    for(int i=0;i<response.length();i++){
                        JSONObject dataobj = response.getJSONObject(i);


                        String medsbrand = dataobj.getString("brand");



                        brand.add(medsbrand);


                        brandAdapter= new ArrayAdapter<>(apppointment_history.this, android.R.layout.simple_spinner_item,brand);
                        brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerbrand.setAdapter(brandAdapter);



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
            }
        });

        requestQueue.add(jsonArrayRequest);


    }

    private void getmedsdata() {

        pd.setMessage("Please Wait.");
        pd.show();

        RequestQueue requestQueue ;
        requestQueue =Volley.newRequestQueue(apppointment_history.this);
        String url = "https://checkurdoc.herokuapp.com/medicine";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                pd.dismiss();

                Log.d("meds",response.toString());
                try {

                    for(int i=0;i<response.length();i++){
                        JSONObject dataobj = response.getJSONObject(i);

                        String medsName = dataobj.getString("name");

                        meds.add(medsName);


                        medsAdapter = new ArrayAdapter<>(apppointment_history.this, android.R.layout.simple_spinner_item,meds);
                        medsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnermeds.setAdapter(medsAdapter);



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
            }
        });

        requestQueue.add(jsonArrayRequest);

    }
}
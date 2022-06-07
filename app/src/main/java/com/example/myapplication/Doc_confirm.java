package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Doc_confirm extends AppCompatActivity {
    private TextView name,address,age,msg;
    private Button book;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_confirm);

        name = findViewById(R.id.Name);
        address = findViewById(R.id.ClinicAddress);
        age = findViewById(R.id.Age);
        book = findViewById(R.id.bookAppointment);
        msg = findViewById(R.id.WishMsg);
        pd = new ProgressDialog(this);

        getData();

        Bundle bundle = getIntent().getExtras();

        String PatientUserName = bundle.getString("patientUserName");
        String docusername = bundle.getString("docUserName");



        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Doc_confirm.this , date_time.class);
                Bundle bundle = new Bundle();
                bundle.putString("patientUsername",PatientUserName);
                bundle.putString("docUsername",docusername);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void getData() {

        Bundle extras = getIntent().getExtras();
        String docusername = extras.getString("docUserName");

        Log.d("docName",docusername);

        String url = "https://checkurdoc.herokuapp.com/appointment/"+docusername+"/"+docusername;

        RequestQueue requestQueue;

        requestQueue = Volley.newRequestQueue(this);




        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("response",response.toString());
                pd.dismiss();
                Log.d("response",response.toString());
                try {
                    for(int i=0;i<response.length();i++){
                        JSONObject dataobj = response.getJSONObject(i);

                        name.setText(dataobj.getString("patient"));
                        address.setText(dataobj.getString("sickness"));
                        age.setText(dataobj.getString("appointment_date"));


                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


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
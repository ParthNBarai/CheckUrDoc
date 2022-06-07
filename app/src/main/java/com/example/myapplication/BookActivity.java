package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class BookActivity extends AppCompatActivity {
    private TextView name,address,age,msg,wish;
    private EditText sickness;
    private Button book;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        sickness = findViewById(R.id.sickness);
        name = findViewById(R.id.Name);
        address = findViewById(R.id.ClinicAddress);
        age = findViewById(R.id.Age);
        book = findViewById(R.id.bookAppointment);
        msg = findViewById(R.id.WishMsg);
        wish = findViewById(R.id.Hello);
        pd = new ProgressDialog(this);




        Bundle bundle = getIntent().getExtras();
        String PatientName = bundle.getString("PatientName");
        String docUsername = bundle.getString("name");
        String PatientUserName = bundle.getString("PatientUserName");
        Log.d("Pn",PatientUserName);
        wish.setText("Hello, "+ PatientName);
        getData();
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sick = sickness.getText().toString();
                bookAppointement(PatientName,PatientUserName,sick,docUsername);
            }
        });



    }

    private void bookAppointement(String patientName, String patientUserName, String Sick,String docUsername) {


        String url = "https://checkurdoc.herokuapp.com/patient/"+patientUserName;

        RequestQueue requestQueue;

        requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                    Log.d("bookapp",response.toString());
                    Toast.makeText(BookActivity.this, "Appointment Successfull!", Toast.LENGTH_SHORT).show();

//                    Intent intent = new Intent(LoginScreen.this, main_homeScreen.class);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Log.d("error", "onError: "+ error.getMessage());
                Toast.makeText(BookActivity.this, "User not found! Kindly register", Toast.LENGTH_SHORT).show();
            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("patient",patientName);
                params.put("username",patientUserName);
                params.put("sickness",Sick);
                params.put("doctor",docUsername);
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

    private void getData() {
        pd.setMessage("Please Wait.");
        pd.show();
        Bundle bundle = getIntent().getExtras();
        String docUsername = bundle.getString("name");
        Log.d("docName",docUsername);

        RequestQueue requestQueue;
        String url = "https://checkurdoc.herokuapp.com/user/0/"+docUsername;

        requestQueue = Volley.newRequestQueue(BookActivity.this);




        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pd.dismiss();
                Log.d("response",response.toString());
                try {
                    name.setText(response.getString("name"));
                    address.setText(response.getString("address"));
                    age.setText(response.getString("age"));

                    Log.d("book",response.toString());



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

        requestQueue.add(jsonObjectRequest);
    }
}
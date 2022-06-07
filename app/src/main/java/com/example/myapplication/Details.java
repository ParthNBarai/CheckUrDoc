package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Fragments.HomeFragment;

import java.util.HashMap;
import java.util.Map;

public class Details extends AppCompatActivity {
        private EditText addline1,addline2,city,state,pin;
        private Button save;
        private TextView wish;

        ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        addline1 = findViewById(R.id.addline1);
        addline2 = findViewById(R.id.addline2);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        pin = findViewById(R.id.pin);
        save = findViewById(R.id.save);
        wish = findViewById(R.id.wish);

        pd = new ProgressDialog(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = addline1.getText().toString() + addline2.getText().toString();

                String cityName = city.getText().toString();
                String stateName = state.getText().toString();
                String pincode  = pin.getText().toString();
                submitData(address,cityName,stateName,pincode);

            }
        });
    }

    private void submitData(String address, String cityName, String stateName, String pincode) {
        pd.setMessage("Please Wait.");
        pd.show();
        Bundle extras = getIntent().getExtras();
        String stringVariableName = extras.getString("StringVariableName");
        int intVariableName = Integer.parseInt(stringVariableName);
        String name = extras.getString("Name");
        String email= extras.getString("email");
        String pass = extras.getString("pass");
        String mobile = extras.getString("mobile");
        String age = extras.getString("age");

        Log.d("myapp", email);



        String url = "https://checkurdoc.herokuapp.com/signup/"+intVariableName;

        RequestQueue requestQueue;

        requestQueue = Volley.newRequestQueue(Details.this);


        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("myapp", response.toString());

                        Toast.makeText(Details.this, "Success", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                        Intent intent = new Intent(Details.this, LoginScreen.class);
                        Bundle extra = new Bundle();
                        extra.putString("StringVariableName",intVariableName + "");
                        intent.putExtras(extra);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Details.this, "unsuccessfull", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                Log.d("myapp", "Some error");
            }
        })


        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("username",email);
                params.put("age" ,age);
                params.put("mobile",mobile);
                params.put("password",pass);
                params.put("address", address);
                params.put("city",cityName);
                params.put("state",stateName);
                params.put("pin_code" ,pincode);


                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    }

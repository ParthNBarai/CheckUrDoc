package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText text1,text2,text3,text4,text5;
    private TextView view1;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        text1=findViewById(R.id.name);
        text2=findViewById(R.id.email);
        text3=findViewById(R.id.password);
        text4=findViewById(R.id.mobile);
        text5=findViewById(R.id.age);

        view1=findViewById(R.id.view);

        btn=findViewById(R.id.button);

//        RequestQueue requestQueue;
//
//        requestQueue = Volley.newRequestQueue(MainActivity.this);
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
//                "https://127.0.0.1:5000/signup/1", null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    Log.d("myapp", "succesfull response is " + response.getString("name"));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("myapp", "unsuccesfull");
//            }
//        });
//
//        requestQueue.add(jsonObjectRequest);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = text1.getText().toString();
                String email = text2.getText().toString();
                String pass = text3.getText().toString();
                String mobile = text4.getText().toString();
                String age = text5.getText().toString();

                Bundle extras = getIntent().getExtras();
                String stringVariableName = extras.getString("StringVariableName");
                int intVariableName = Integer.parseInt(stringVariableName);

                Intent intent = new Intent(MainActivity.this, Details.class);
                Bundle extra = new Bundle();
                extra.putString("StringVariableName",intVariableName + "");
                extra.putString("Name",name);
                extra.putString("email",email);
                extra.putString("pass",pass);
                extra.putString("mobile",mobile);
                extra.putString("age",age);
                intent.putExtras(extra);
                startActivity(intent);
            }
        });
//
//
//    }
//
//    private void submitData(String name, String email, String pass, String mobile, String age) {
//        Bundle extras = getIntent().getExtras();
//        String stringVariableName = extras.getString("StringVariableName");
//        int intVariableName = Integer.parseInt(stringVariableName);
//
//        String url = "https://checkurdoc.herokuapp.com/signup/"+intVariableName;
//
//        RequestQueue requestQueue;
//
//        requestQueue = Volley.newRequestQueue(MainActivity.this);
//
//
//        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url ,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("myapp", response.toString());
//                        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(MainActivity.this, Details.class);
//                        Bundle extras = new Bundle();
//                        extras.putString("StringVariableName",intVariableName + "");
//                        extras.putString("Name",name);
//                        extras.putString("email",name);
//                        extras.putString("pass",name);
//                        extras.putString("mobile",name);
//                        extras.putString("age",name);
//                        intent.putExtras(extras);
//                        startActivity(intent);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
//                Log.d("myapp", "Some error");
//            }
//        })
//
//
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("name", name);
//                params.put("username",email);
//                params.put("password",pass);
//                params.put("mobile",mobile);
//                params.put("age" ,age);
//
//
//                return params;
//            }
//        };
//        requestQueue.add(stringRequest);
    }
}
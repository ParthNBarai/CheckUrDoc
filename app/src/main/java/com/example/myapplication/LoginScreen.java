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
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Fragments.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginScreen extends AppCompatActivity {
    private TextView login,register;
    private Button btnlogin;
    private EditText email,password,name;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        login = findViewById(R.id.Login);
        register = findViewById(R.id.register);
        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        btnlogin = findViewById(R.id.btnLogin);
        pd = new ProgressDialog(this);
        name = findViewById(R.id.loginName);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                String stringVariableName = extras.getString("StringVariableName");
                int prompt = Integer.parseInt(stringVariableName);
                Log.d("newPrompt",Integer.toString(prompt));
                Intent intent = new Intent(LoginScreen.this,MainActivity.class);
                Bundle extra = new Bundle();
                extra.putString("StringVariableName", prompt + "");
                intent.putExtras(extra);
                startActivity(intent);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailId = email.getText().toString();
                String pass = password.getText().toString();

                onSubmit(emailId,pass);
            }
        });
    }

    private void onSubmit(String emailId, String pass) {
        pd.setMessage("Please Wait.");
        pd.show();
        String pname = name.getText().toString();
        Bundle extras = getIntent().getExtras();
        String stringVariableName = extras.getString("StringVariableName");
        int intVariableName = Integer.parseInt(stringVariableName);

        String url = "https://checkurdoc.herokuapp.com/login/"+intVariableName;

        RequestQueue requestQueue;

        requestQueue = Volley.newRequestQueue(LoginScreen.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                pd.dismiss();
                String statusCode = response.toString().substring(13,16);
                int status = Integer.parseInt(statusCode);
                Log.d("ferror", statusCode);
                if(status == 200) {
                    Toast.makeText(LoginScreen.this, "Sucessfull Login!", Toast.LENGTH_SHORT).show();
                        if(intVariableName == 1) {
                            Intent intent = new Intent(LoginScreen.this, main_homeScreen.class);

                            Log.d("username", emailId);
                            Log.d("Pn", pname);
                            Log.d("New", Integer.toString(intVariableName));
                            Bundle extras = new Bundle();
                            extras.putString("username", emailId + "");
                            extras.putString("PatientName", pname + "");

                            intent.putExtras(extras);
                            startActivity(intent);
                        }
                        else if(intVariableName == 0){
                            Intent intent = new Intent(LoginScreen.this, doc_screen.class);

                            Log.d("username", emailId);
                            Bundle extras = new Bundle();
                            extras.putString("DocLoginName", emailId);
                            extras.putString("PatientName", pname);

                            intent.putExtras(extras);
                            startActivity(intent);
                        }

                }else{
                    Toast.makeText(LoginScreen.this, "User not found! Kindly register", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Log.d("error", "onError: "+ error.getMessage());
                Toast.makeText(LoginScreen.this, "User not found! Kindly register", Toast.LENGTH_SHORT).show();
            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",emailId);
                params.put("password",pass);
                return params;
            }
        };

        requestQueue.add(stringRequest);


    }
}
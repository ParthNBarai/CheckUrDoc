package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class date_time extends AppCompatActivity {

    private static Button date, time,confirm;
    private static TextView set_date, set_time;
    private static final int Date_id = 0;
    private static final int Time_id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time);

        date = (Button) findViewById(R.id.selectdate);
        time = (Button) findViewById(R.id.selecttime);
        set_date = (TextView) findViewById(R.id.set_date);
        set_time = (TextView) findViewById(R.id.set_time);
        confirm = findViewById(R.id.confirm);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(Date_id);
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(Time_id);
            }
        });



        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("timec",set_time.getText().toString());
                Log.d("datec",set_date.getText().toString());
                postData();
            }
        });
    }

    private void postData() {

        Bundle extras = getIntent().getExtras();
        String docusername = extras.getString("docUsername");
        String patientusername = extras.getString("patientUsername");
        Log.d("patientu",patientusername);
        Log.d("docU",docusername);


        String url = "https://checkurdoc.herokuapp.com/appointment/"+patientusername+"/"+docusername;



        RequestQueue requestQueue;

        requestQueue = Volley.newRequestQueue(date_time.this);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("myapp", response.toString());

                        Toast.makeText(date_time.this, "Your have successfully accepted the appointment!!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(date_time.this,doc_screen.class);

                        Bundle extras = new Bundle();
                        extras.putString("DocLoginName",docusername);
                        intent.putExtras(extras);

                        startActivity(intent);
//                        pd.dismiss();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(date_time.this, "unsuccessfull", Toast.LENGTH_SHORT).show();
//                pd.dismiss();
                Log.d("myapp", "Some error");
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("time",set_time.getText().toString());
                params.put("date", set_date.getText().toString());
                params.put("number" ,"9820758390");

                return params;
            }
        };



        requestQueue.add(stringRequest);
    }



    protected Dialog onCreateDialog(int id) {

        // Get the calander
        Calendar c = Calendar.getInstance();

        // From calander get the year, month, day, hour, minute
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        switch (id) {
            case Date_id:

                // Open the datepicker dialog
                return new DatePickerDialog(date_time.this, date_listener, year,
                        month, day);
            case Time_id:

                // Open the timepicker dialog
                return new TimePickerDialog(date_time.this, time_listener, hour,
                        minute, false);

        }
        return null;
    }

    // Date picker dialog
    DatePickerDialog.OnDateSetListener date_listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // store the data in one string and set it to text
            String date1 = String.valueOf(day) + "/" + String.valueOf(month+1)
                    + "/" + String.valueOf(year);
            set_date.setText(date1);


        }
    };
    TimePickerDialog.OnTimeSetListener time_listener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            // store the data in one string and set it to text
            String time1 = String.valueOf(hour) + ":" + String.valueOf(minute);
            set_time.setText(time1);

        }
    };
    }

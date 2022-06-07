package com.example.myapplication.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.HomeScreen_Adapter;
import com.example.myapplication.HomeScreen_Data;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AccountFragment extends Fragment {

    private TextView user_Name,user_username,user_phone,user_address;

    public static AccountFragment getInstance(){
        AccountFragment accountFragment = new AccountFragment();
        return  accountFragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this

       View view = inflater.inflate(R.layout.fragment_account, container, false);
       user_Name = view.findViewById(R.id.UserName);

       user_username = view.findViewById(R.id.User_userName);
       user_phone = view.findViewById(R.id.User_mob);
       user_address = view.findViewById(R.id.User_Address);

       getDetails();

       return view;

    }

    private void getDetails() {

        String patUsername = getArguments().getString("username");
        RequestQueue requestQueue;

        String url = "https://checkurdoc.herokuapp.com/user/1/"+patUsername;

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());




        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("response",response.toString());
                try {
                    user_Name.setText(response.getString("name"));
                    user_username.setText(response.getString("username"));
                    user_address.setText(response.getString("address"));
                    user_phone.setText(response.getString("mobile"));

                    Log.d("book",response.toString());



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("error", "onError: "+ error.getMessage());
            }
        });

        requestQueue.add(jsonObjectRequest);


    }
}
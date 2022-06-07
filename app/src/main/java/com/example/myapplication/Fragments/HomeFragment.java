package com.example.myapplication.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.BookActivity;
import com.example.myapplication.HomeScreen_Adapter;
import com.example.myapplication.HomeScreen_Data;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
        RecyclerView recyclerView;
        HomeScreen_Adapter adapter;
        List<HomeScreen_Data> dataList;
        private HomeScreen_Adapter.RecyclerViewClickListener listener;
        ArrayList<String> docUsername;
        ProgressDialog pd;

        public static HomeFragment getInstance(){
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recview);
        pd = new ProgressDialog(getActivity());
        dataList = new ArrayList<>();
        docUsername = new ArrayList<>();

        extractData();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        setOnClickListener();
        adapter = new HomeScreen_Adapter(getActivity().getApplicationContext(),dataList,listener);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

        return view;
    }

    private void setOnClickListener() {
        listener = new HomeScreen_Adapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
//                int index = position;
//                Log.d("index" , Integer.toString(index));
//                String name = dataList.get(position).getName();
                String patientUsername = getArguments().getString("username");
                Log.d("fdocname",docUsername.get(position));
                Intent intent = new Intent(getActivity(),BookActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", docUsername.get(position));
                bundle.putString("PatientName",getArguments().getString("PatientName"));
                bundle.putString("PatientUserName",patientUsername);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        };
    }

    private void extractData() {

//        int status = getArguments().getInt("position");
        pd.setMessage("Please Wait.");
        pd.show();
        String username = getArguments().getString("username");

        RequestQueue requestQueue;

//        String username = getArguments().getString("username");
//        Log.d("fusername",username);
//        Log.d("fstatus",Integer.toString(status));
        String url = "https://checkurdoc.herokuapp.com/search/"+username;

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());




        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("response",response.toString());
                pd.dismiss();
                try {

                    for(int i=0;i<response.length();i++){
                        JSONObject dataobj = response.getJSONObject(i);

                        HomeScreen_Data data = new HomeScreen_Data();

                        docUsername.add(dataobj.getString("username").toString());
                        data.setName(dataobj.getString("name").toString());
                        data.setAddress(dataobj.getString("Address").toString());
//                        Log.d("fusername",username);
                        dataList.add(data);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                adapter = new HomeScreen_Adapter(getActivity().getApplicationContext(),dataList,listener);
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
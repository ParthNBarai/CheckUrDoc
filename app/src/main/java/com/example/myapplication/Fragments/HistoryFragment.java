package com.example.myapplication.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.android.volley.toolbox.Volley;
import com.example.myapplication.HomeScreen_Adapter;
import com.example.myapplication.HomeScreen_Data;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    ProgressDialog pd;
    RecyclerView recyclerView;
    HomeScreen_Adapter adapter;
    List<HomeScreen_Data> dataList;

    public static HistoryFragment getInstance(){
        HistoryFragment historyFragment = new HistoryFragment();
        return historyFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = view.findViewById(R.id.recview);
        dataList = new ArrayList<>();
        pd = new ProgressDialog(getActivity());
        getData();

        return view;
    }

    private void getData() {
        String patUsername = getArguments().getString("username");
        Log.d("pat",patUsername);
        RequestQueue requestQueue;

        String url = "https://checkurdoc.herokuapp.com/history/"+patUsername;

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());




        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("response",response.toString());
                pd.dismiss();
                try {
                    Log.d("history",response.toString());
                    for(int i=0;i<response.length();i++){
                        JSONObject dataobj = response.getJSONObject(i);

                        HomeScreen_Data data = new HomeScreen_Data();

//                        docUsername.add(dataobj.getString("username").toString());
                        data.setName(dataobj.getString("doctor").toString());
                        data.setAddress(dataobj.getString("appointment_date").toString());
//                        Log.d("fusername",username);
                        dataList.add(data);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                adapter = new HomeScreen_Adapter(getActivity().getApplicationContext(),dataList,null);
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
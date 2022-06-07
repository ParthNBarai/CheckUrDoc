package com.example.myapplication.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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


public class SearchFragment extends Fragment {
    RecyclerView recyclerView;
    HomeScreen_Adapter adapter;
    List<HomeScreen_Data> dataList;
    ArrayList<String> docUsername;
    private HomeScreen_Adapter.RecyclerViewClickListener listener;
    EditText pin;
    ProgressDialog pd;
    ImageButton searchButton;
    String p3;



    public static SearchFragment getInstance(){
        SearchFragment searchFragment = new SearchFragment();
        return searchFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.recview);
        searchButton = view.findViewById(R.id.searchButton);
        pin = view.findViewById(R.id.PinCode);
        pd = new ProgressDialog(getActivity());


        dataList = new ArrayList<>();
        docUsername = new ArrayList<>();
//        getData(pinCode);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        setOnClickListener();
        adapter = new HomeScreen_Adapter(getActivity().getApplicationContext(),dataList,listener);
        recyclerView.setAdapter(adapter);




        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pinCode = pin.getText().toString();
                adapter.clearData();
                adapter.notifyDataSetChanged();
                getData(pinCode);


            }
        });

        return view;
    }

    private void setOnClickListener() {
        listener = new HomeScreen_Adapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
//                int index = position;
//                Log.d("index" , Integer.toString(index));
                String name = dataList.get(position).getName();
                String patientUsername = getArguments().getString("username");

                Intent intent = new Intent(getActivity(), BookActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", docUsername.get(position));
                bundle.putString("PatientName",getArguments().getString("PatientName"));
                bundle.putString("PatientUserName",patientUsername);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        };

    }

    private void getData(String pinCode) {

        pd.setMessage("Please Wait.");
        pd.show();
        Log.d("pinC",pinCode);
        String p1 = pinCode.substring(0,3);
        String p2=pinCode.substring(3,6);
        String p3 = p1+" "+p2;

        Log.d("pinC",p3);
        RequestQueue requestQueue;

        String url = "https://checkurdoc.herokuapp.com/searchscreen/"+p3;

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

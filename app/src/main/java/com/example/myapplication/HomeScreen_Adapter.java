package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Fragments.HomeFragment;

import java.util.Collection;
import java.util.List;

public class HomeScreen_Adapter  extends RecyclerView.Adapter<HomeScreen_Adapter .ViewHolder> {

    LayoutInflater layoutInflater;
    List<HomeScreen_Data> data;
    private RecyclerViewClickListener listener;

    public  HomeScreen_Adapter(Context ctx , List<HomeScreen_Data> data , RecyclerViewClickListener listener){
        this.layoutInflater = LayoutInflater.from(ctx);
        this.data = data;
        this.listener = listener;
//        this.mOnNoteListener = onNoteListener;
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView name , address;


        public ViewHolder(View view) {
            super(view);


            name = (TextView) view.findViewById(R.id.Docname);
            address = (TextView) view.findViewById(R.id.Address);
            view.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
        listener.onClick(v,getAdapterPosition());
        }
    }







    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view =layoutInflater.inflate(R.layout.adapter_layout,viewGroup,false);
        return new ViewHolder(view);

//        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.name.setText(data.get(position).getName());
        viewHolder.address.setText(data.get(position).getAddress());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v,int position);
    }

    public void clearData(){
        data.clear();
    }
}


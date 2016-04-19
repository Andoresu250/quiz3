package com.example.casamovil.firebase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private String[] data;
    private RecyclerClickListner mRecyclerClickListner;

    public ViewAdapter(Context context, String[] data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String information = data[position];
        holder.tv.setText(information);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public void setRecyclerClickListner(RecyclerClickListner recyclerClickListner){
        mRecyclerClickListner = recyclerClickListner;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.userName);
        }

        @Override
        public void onClick(View v) {
            if(mRecyclerClickListner != null){
                mRecyclerClickListner.itemClick(v, getPosition());
            }
        }
    }

    public interface RecyclerClickListner
    {
        public void itemClick(View view, int position);
    }

}


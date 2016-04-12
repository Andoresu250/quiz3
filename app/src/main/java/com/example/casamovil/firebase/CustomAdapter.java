package com.example.casamovil.firebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by dell on 04/04/2016.
 */
public class CustomAdapter extends BaseAdapter{

    private static final String TAG = "AS_ListView";
    private Context context;
    private String[] values;

    public CustomAdapter(Context context, String[] values){
        this.context = context;
        this.values = values;

    }

    @Override
    public int getCount() {
        return  values.length;
    }

    @Override
    public Object getItem(int position) {
        return values[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String s = values[position];
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_row, null);
        }
        ImageButton button = (ImageButton) convertView.findViewById(R.id.btnRemove);
        button.setFocusableInTouchMode(false);
        button.setFocusable(false);
        button.setTag(s);
        TextView textView = (TextView) convertView.findViewById(R.id.userName);
        textView.setText(s);
        convertView.setTag(s);
        return  convertView;
    }
}

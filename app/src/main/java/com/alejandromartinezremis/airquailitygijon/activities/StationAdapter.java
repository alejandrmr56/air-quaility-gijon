package com.alejandromartinezremis.airquailitygijon.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alejandromartinezremis.airquailitygijon.R;
import com.alejandromartinezremis.airquailitygijon.pojos.ListViewItem;

import java.util.List;

public class StationAdapter extends BaseAdapter {
    List<ListViewItem> listViewItems;
    private Context context;

    public StationAdapter(List<ListViewItem> listViewItems, Context context){
        this.listViewItems = listViewItems;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listViewItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.element_property, null);

        ListViewItem listViewItem = (ListViewItem) getItem(position);

        ((TextView)convertView.findViewById(R.id.textViewId)).setText(listViewItem.getId());
        ((TextView)convertView.findViewById(R.id.textViewValue)).setText(listViewItem.getValue());

        return convertView;
    }
}

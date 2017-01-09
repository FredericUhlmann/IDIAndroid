package com.example.pr_idi.mydatabaseexample;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by fredy on 8/01/17.
 */

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {
    private List<Film> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tv1;
        public TextView tv2;
        public TextView tv3;
        public TextView tv4;
        public TextView tv5;
        public TextView tv6;

        public ViewHolder(View v) {
            super(v);
            tv1 = (TextView) v.findViewById(R.id.rv_tv_1);
            tv2 = (TextView) v.findViewById(R.id.rv_tv_2);
            tv3 = (TextView) v.findViewById(R.id.rv_tv_3);
            tv4 = (TextView) v.findViewById(R.id.rv_tv_4);
            tv5 = (TextView) v.findViewById(R.id.rv_tv_5);
            tv6 = (TextView) v.findViewById(R.id.rv_tv_6);

        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FilmAdapter(List<Film> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FilmAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_film, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.tv1.setText(mDataset.get(position).getTitle());
        holder.tv2.setText(mDataset.get(position).getCountry());
        holder.tv3.setText(Integer.toString(mDataset.get(position).getYear()));
        holder.tv4.setText(mDataset.get(position).getDirector());
        holder.tv5.setText(mDataset.get(position).getProtagonist());
        holder.tv6.setText(Integer.toString(mDataset.get(position).getCritics_rate()));



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

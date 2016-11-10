package com.blackboardtheory.qresume.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.blackboardtheory.qresume.R;
import com.blackboardtheory.qresume.objects.Profile;

import java.util.List;

/**
 * Created by bdevereaux3 on 11/7/16.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    private List<Profile> mDataset;
    private Context context;



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        //public View v;
        public TextView name, school;
        public Context context;
        public ViewHolder(View v, Context context) {
            super(v);
            //this.v = v;
            v.setOnClickListener(this);
            this.name = (TextView)v.findViewById(R.id.name_text);
            this.school = (TextView)v.findViewById(R.id.school_text);
            this.context = context;
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Toast.makeText(this.context, "Position " + Integer.toString(position) + " clicked!", Toast.LENGTH_SHORT).show();
            name.setText("HAHAHAHAHA");
            name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FavoritesAdapter(List<Profile> favoriteProfiles, Context context) {
        mDataset = favoriteProfiles;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite, parent, false);
        if(viewType == R.layout.item_favorite) {
            Log.d("DEBUGGING", "it's true!");
        }
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v, context);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //set up a profile view here
        holder.name.setText(mDataset.get(position).getName());
        holder.school.setText(mDataset.get(position).getSchool());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}



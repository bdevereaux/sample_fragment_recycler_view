package com.blackboardtheory.qresume.adapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.blackboardtheory.qresume.R;
import com.blackboardtheory.qresume.fragments.FavoriteDetailFragment;
import com.blackboardtheory.qresume.fragments.FavoritesFragment;
import com.blackboardtheory.qresume.objects.Profile;

import java.util.List;

import static android.transition.TransitionSet.ORDERING_TOGETHER;

/**
 * Created by bdevereaux3 on 11/7/16.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    private List<Profile> mDataset;
    private Context context;
    private ImageLoader mImageLoader;



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View v;
        public TextView name, school;
        public NetworkImageView pic;
        public Context context;
        public ImageLoader mImageLoader;
        public ViewHolder(View v, Context context, ImageLoader mImageLoader) {
            super(v);
            this.v = v;
            this.name = (TextView)v.findViewById(R.id.name_text);
            this.school = (TextView)v.findViewById(R.id.school_text);
            this.pic = (NetworkImageView)v.findViewById(R.id.profile_pic);
            this.context = context;
            this.mImageLoader = mImageLoader;
        }

        public void bind(Profile profile, FavoritesAdapter.MyClickListener onClickListener) {
            name.setText(profile.getTitle());
            school.setText(Integer.toString(profile.getUserID()));
            pic.setImageUrl(profile.getIconURL(), mImageLoader);
            pic.setTransitionName(Integer.toString(profile.getUserID()));
            pic.setOnClickListener(onClickListener);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FavoritesAdapter(List<Profile> favoriteProfiles, Context context, ImageLoader mImageLoader) {
        mDataset = favoriteProfiles;
        this.context = context;
        this.mImageLoader = mImageLoader;
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
        return new ViewHolder(v, context, mImageLoader);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //set up a profile view here
        holder.bind(mDataset.get(position), new MyClickListener(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class DetailsTransition extends TransitionSet {
        public DetailsTransition() {
            setOrdering(ORDERING_TOGETHER);
            addTransition(new ChangeBounds()).
                    addTransition(new ChangeTransform()).
                    addTransition(new ChangeImageTransform());
        }
    }

    public class MyClickListener implements View.OnClickListener {
        int position;

        public MyClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            FavoriteDetailFragment detailFragment = new FavoriteDetailFragment();
            Bundle details = new Bundle();
            details.putString("url", mDataset.get(position).getIconURL());
            detailFragment.setArguments(details);

            detailFragment.setSharedElementEnterTransition(new FavoritesAdapter.DetailsTransition());
            detailFragment.setEnterTransition(new Fade());
            //((FavoritesFragment)context).setExitTransition(new Fade());
            detailFragment.setSharedElementReturnTransition(new DetailsTransition());

            FragmentManager fm = ((Activity)context).getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();

            transaction.addSharedElement(view, "poster")
                    .replace(R.id.view_container, detailFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}



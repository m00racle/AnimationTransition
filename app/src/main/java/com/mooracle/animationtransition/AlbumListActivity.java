package com.mooracle.animationtransition;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AlbumListActivity extends AppCompatActivity {

    //define Views:
    private RecyclerView albumRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list);

        //initialize transitions:
        setUpTransitions();

        //populate the album Recycler view:
        albumRecyclerView = findViewById(R.id.albumRecyclerView);
        populate();
    }

    private void populate() {
        //set the grid layout of all album
        //make the grid 2 vertical lines
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        albumRecyclerView.setLayoutManager(layoutManager);

        //populate the list of album:
        final int[] albumArts = {
                R.drawable.mean_something_kinder_than_wolves,
                R.drawable.cylinders_chris_zabriskie,
                R.drawable.broken_distance_sutro,
                R.drawable.playing_with_scratches_ruckus_roboticus,
                R.drawable.keep_it_together_guster,
                R.drawable.the_carpenter_avett_brothers,
                R.drawable.please_sondre_lerche,
                R.drawable.direct_to_video_chris_zabriskie
        };

        //create the adapter
        RecyclerView.Adapter adapter = new RecyclerView.Adapter<AlbumViewHolder>() {
            @NonNull
            @Override
            public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                //inflate  the grid
                View albumView = getLayoutInflater().inflate(R.layout.content_album_list, parent,false);

                return new AlbumViewHolder(albumView, new OnVHClickedListener() {
                    //here is where the OnVHClickListener is useful:
                    @Override
                    public void onVHClicked(AlbumViewHolder vh) {
                        //when user click one of the album art:
                        //get the resource id:
                        int albumArtResId = albumArts[vh.getAdapterPosition() % albumArts.length];
                        //this is how to get the position on the albumArts.length

                        //prepare detail activity and put the album as Extra of an Intent:
                        Intent intent = new Intent(AlbumListActivity.this, AlbumDetailActivity.class);
                        intent.putExtra(AlbumDetailActivity.ALBUM_ART_RESID_EXTRA, albumArtResId);

                        // add activity options object to add transition animations
                        ActivityOptions options = ActivityOptions
                                .makeSceneTransitionAnimation(AlbumListActivity.this,
                                        vh.albumArt, "albumArt");

                        //start that activity: adding options that turned into a bundle object
                        startActivity(intent, options.toBundle());
                    }
                });
            }

            @Override
            public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
                holder.albumArt.setImageResource(albumArts[position % albumArts.length]);
            }

            @Override
            public int getItemCount() {
                return albumArts.length *4;
            }
        };
        albumRecyclerView.setAdapter(adapter);
    }

    //set View Holder
    interface OnVHClickedListener {
        void onVHClicked(AlbumViewHolder vh);
    }
    static class AlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final OnVHClickedListener listener;
        ImageView albumArt;

        //constructor
        AlbumViewHolder(View itemView, OnVHClickedListener listener){
            super(itemView);
            albumArt = itemView.findViewById(R.id.albumArt);

            itemView.setOnClickListener(this);
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            listener.onVHClicked(this);
        }
    }

    // refactor rename this to be setupTransitions so it will be the same as the other class
    private void setUpTransitions() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_album_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

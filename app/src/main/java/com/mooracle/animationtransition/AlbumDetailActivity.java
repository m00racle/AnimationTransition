package com.mooracle.animationtransition;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

public class AlbumDetailActivity extends AppCompatActivity {

    static final String ALBUM_ART_RESID_EXTRA = "ALBUM ART RES ID";

    //view declaration
    private ImageView albumArtView;
    ImageButton fab;
    private ViewGroup titlePanel, trackPanel, detailContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //instantiate views
        setContentView(R.layout.activity_album_detail);
        albumArtView = findViewById(R.id.album_art);
        fab = findViewById(R.id.fab);
        titlePanel = findViewById(R.id.title_panel);
        trackPanel = findViewById(R.id.track_panel);
        detailContainer = findViewById(R.id.detail_container);

        //populate the activity:
        populate();
    }

    private void populate() {
        //get the extra res ID from the intent that leads to this activity:
        int albumArtResId = getIntent().getIntExtra(ALBUM_ART_RESID_EXTRA, R.drawable.mean_something_kinder_than_wolves);

        //put the Album Art Res ID into the view of AlbumArtView ImageView
        albumArtView.setImageResource(albumArtResId);

        //get colors from bitmap to colorize
        //1. get the bitmap version of the album art
        Bitmap albumBitmap = getReducedBitmap(albumArtResId);

        //2. get the colors data from that bitmap to color the fab and Panels using palette
        //read more about palette: https://developer.android.com/training/material/palette-colors
        colorizeFromImage(albumBitmap);
    }

    private void colorizeFromImage(Bitmap image) {
        Palette palette = Palette.from(image).generate();

        //set the panel AND fab default color:
        int defaultPanelColor = 0xFF808080;
        int defaultFabColor = 0xFFEEEEEE;

        //set the panel color: at the moment use the dark and light version of default panel color using palette
        //to edit the color.
        titlePanel.setBackgroundColor(palette.getDarkVibrantColor(defaultPanelColor));
        trackPanel.setBackgroundColor(palette.getLightMutedColor(defaultPanelColor));

        //set the fab colors: Floating Action Button will have two state enabled and pressed both of these states
        //will have two colors for each state
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_enabled},
                new int[]{android.R.attr.state_pressed}
        };

        //set the color to be connected to each state, we are using palette here
        int[] colors = new int[]{
                palette.getVibrantColor(defaultFabColor),
                palette.getLightVibrantColor(defaultFabColor)
        };
        fab.setBackgroundTintList(new ColorStateList(states, colors));
    }

    private Bitmap getReducedBitmap(int albumArtResId){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = 8;
        return BitmapFactory.decodeResource(getResources(), albumArtResId, options);
    }
}

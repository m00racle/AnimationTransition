package com.mooracle.animationtransition;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

public class AlbumDetailActivity extends AppCompatActivity {

    public static final String ALBUM_ART_RESID_EXTRA = "ALBUM ART RES ID";

    //view declaration
    ImageView albumArtView;
    ImageButton fab;
    ViewGroup titlePanel, trackPanel, detailContainer;

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
    }

}

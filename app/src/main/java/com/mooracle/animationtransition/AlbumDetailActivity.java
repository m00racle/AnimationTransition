package com.mooracle.animationtransition;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class AlbumDetailActivity extends AppCompatActivity {

    public static final String ALBUM_ART_RESID_EXTRA = "ALBUM ART RES ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);

    }

}

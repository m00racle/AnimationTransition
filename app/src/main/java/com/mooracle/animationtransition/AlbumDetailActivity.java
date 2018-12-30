package com.mooracle.animationtransition;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.transition.*;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
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
        
        //set click on album art view
        albumArtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call the animate method in the main class
                animate();
            }
        });

        //set track panel onClick Listener
        trackPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // define the root view hierarchy where transition happens
                ViewGroup transitionRoot = detailContainer;

                // create new scene and set it to contain our transition layout using static method getSceneForLayout
                Scene expandedScene = Scene.getSceneForLayout(transitionRoot, R.layout.activity_album_detail_expanded,
                        v.getContext());

                // create transition set to regulate the orders of transitions and set it to be sequential
                TransitionSet set = new TransitionSet();
                set.setOrdering(TransitionSet.ORDERING_SEQUENTIAL);

                // add Change bounds into this Transition set
                ChangeBounds changeBounds = new ChangeBounds();
                changeBounds.setDuration(200); // in milliseconds
                set.addTransition(changeBounds);

                // create a new Fade object for the lyric (set the target to lyrics)
                Fade fadeLyrics = new Fade();
                fadeLyrics.addTarget(R.id.lyrics);
                fadeLyrics.setDuration(150); //in milliseconds

                // add this lyric fade transitions to the Transition set
                set.addTransition(fadeLyrics);

                // pass expanded scene to transition manager and set it go
                TransitionManager.go(expandedScene, set);
            }
        });
    }

    private void animate() {
        //use android.animation to animate fab to scale up from 0 to 1 (current value) each time album art clicked
        /* This block of code is commented out since will be substituted using xml code animation set
        // create Object animator object that will be used to scale both to X and Y axis
        ObjectAnimator fabScaleX = ObjectAnimator.ofFloat(fab, "scaleX", 0, 1);
        ObjectAnimator fabScaleY = ObjectAnimator.ofFloat(fab, "scaleY", 0, 1);

        // make animation set consist of fab scale X and scale Y simultaneously by making animation set
        AnimatorSet fabScale = new AnimatorSet();
        fabScale.playTogether(fabScaleX, fabScaleY);*/

        //set animator object that will be inflated by the res/animator/scale.xml
        Animator fabScale = AnimatorInflater.loadAnimator(this, R.animator.scale);

        //set the fabScale (now an animator object) target which is the fab imageButton:
        fabScale.setTarget(fab);

        // animate panels (title and track) to swipe down when album art is clicked
        // create object animator object to animate title panel from postion top to bottom
        ObjectAnimator animatorTitle = ObjectAnimator.ofInt(titlePanel, "bottom",
                titlePanel.getTop(), titlePanel.getBottom());

        //set interpolator (accelerate) for title panel
        animatorTitle.setInterpolator(new AccelerateInterpolator());

        //set duration for title panel animation
        animatorTitle.setDuration(300); //in milliseconds

        // create similar for track panel
        ObjectAnimator animatorTrack = ObjectAnimator.ofInt(trackPanel, "bottom",
                trackPanel.getTop(), trackPanel.getBottom());

        //set interpolator (decelerate) for track panel
        animatorTrack.setInterpolator(new DecelerateInterpolator());

        //set duration for track panel
        animatorTrack.setDuration(150); //in milliseconds

        //combine all into one set with title panel and fab simultaneously then track panel after that
        AnimatorSet firstSet = new AnimatorSet();
        firstSet.playTogether(fabScale, animatorTitle);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(firstSet, animatorTrack);

        //before the animation starts we need to set initial values for fab and all panels
        int panelStartValue = titlePanel.getTop();
        titlePanel.setBottom(panelStartValue);
        trackPanel.setBottom(panelStartValue);
        fab.setScaleY(0);
        fab.setScaleX(0);

        //start the animation set:
        set.start();
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

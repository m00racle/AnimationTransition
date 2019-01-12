package com.mooracle.animationtransition;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.transition.*;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.mooracle.animationtransition.transition.Fold;
import com.mooracle.animationtransition.transition.Scale;

public class AlbumDetailActivity extends AppCompatActivity {

    static final String ALBUM_ART_RESID_EXTRA = "ALBUM ART RES ID";

    //view declaration
    private ImageView albumArtView;
    ImageButton fab;
    private ViewGroup titlePanel, trackPanel, detailContainer;

    // declare transition manager field:
    private TransitionManager transitionManager;

    // declare Scene fields: (fix the expandedScene used in set up method) also current scene as info holder
    private Scene expandedScene, collapsedScene, currentScene;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //instantiate views : optimize this so that can be called for binding all the time
        setContentView(R.layout.activity_album_detail);
        setBindingViews();

        //populate the activity:
        populate();

        // creating the set up transitions right away
        setUpTransitions();
        setListeners();

    }

    private void setListeners() {
        //set click on album art view
        albumArtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set the initial conditions for fab, title panel and track panel prior to transition:
                fab.setVisibility(View.INVISIBLE);
                titlePanel.setVisibility(View.INVISIBLE);
                trackPanel.setVisibility(View.INVISIBLE);

                //  use the newly created extended Transition animation of Fold and Scale when clicked
                Transition transition = createTransition();

                //begin the delayed transition with root on detail container and the transition that we just created
                TransitionManager.beginDelayedTransition(detailContainer, transition);

                //set the end state which is back to visible but using the transition defined in createTransition():
                fab.setVisibility(View.VISIBLE);
                titlePanel.setVisibility(View.VISIBLE);
                trackPanel.setVisibility(View.VISIBLE);
            }
        });

        //set track panel onClick Listener
        // optimize this make it a separate method to be called from various listener
        trackPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set toggle options between expanded or collapse scenes using the information about current scene
                if (currentScene == expandedScene){
                    currentScene = collapsedScene;
                }
                else {
                    currentScene = expandedScene;
                }
                // set the transition manager to transition to
                transitionManager.transitionTo(currentScene);
            }
        });
    }

    private Transition createTransition(){
        //instantiate transition set
        TransitionSet set = new TransitionSet();
        set.setOrdering(TransitionSet.ORDERING_SEQUENTIAL);

        //set Transition for the floating action button, set the duration 150 milliseconds,
        Transition transitionFab = new Scale();
        transitionFab.setDuration(150);
        transitionFab.addTarget(fab);

        //set Transition for the title panel, set the duration 300 ms
        Transition transitionTitle = new Fold();
        transitionTitle.setDuration(300);
        transitionTitle.addTarget(titlePanel);

        //set Transition for the track panel and set the duration to 150 ms
        Transition transitionTrack = new Fold();
        transitionTrack.setDuration(150);
        transitionTrack.addTarget(trackPanel);

        //make fab and panel animation begin together providing more seamless animation
        TransitionSet fabPanelSet = new TransitionSet();
        fabPanelSet.setOrdering(TransitionSet.ORDERING_TOGETHER);
        fabPanelSet.addTransition(transitionFab);
        fabPanelSet.addTransition(transitionTitle);

        //make the track transition animation after the fab and panel animations.
        set.addTransition(fabPanelSet);
        set.addTransition(transitionTrack);

        return set;
    }

    private void setBindingViews() {
        albumArtView = findViewById(R.id.album_art);
        fab = findViewById(R.id.fab);
        titlePanel = findViewById(R.id.title_panel);
        trackPanel = findViewById(R.id.track_panel);
        detailContainer = findViewById(R.id.detail_container);
    }

    private void setUpTransitions() {

        // need to exclude slide for status bar
        Slide slide = new Slide(Gravity.BOTTOM);
        slide.excludeTarget(android.R.id.statusBarBackground, true);

        //set the enter transition to support transition other than album art
        getWindow().setEnterTransition(slide);

        // defines the transitionManager
        transitionManager = new TransitionManager();

        // define the root view hierarchy where transition happens
        ViewGroup transitionRoot = detailContainer;

        //this is the expanded scene:
        // create new scene and set it to contain our transition layout using static method getSceneForLayout
        expandedScene = Scene.getSceneForLayout(transitionRoot, R.layout.activity_album_detail_expanded,
                this);

        // fix the views in the new expanded layout using the populate method to fill the actual views
        expandedScene.setEnterAction(new Runnable() {
            @Override
            public void run() {
                // we need to bind the views again:
                setBindingViews();

                //call the populate method to bind to the current resources to views
                populate();

                // set the current scene as expanded scene
                currentScene = expandedScene;

                //set up all listeners:
                setListeners();
            }
        });

        // create transition set to regulate the orders of transitions and set it to be sequential
        // rename this to expanded transition set
        TransitionSet expandTransitionSet = new TransitionSet();
        expandTransitionSet.setOrdering(TransitionSet.ORDERING_SEQUENTIAL);

        // add Change bounds into this Transition set
        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(200); // in milliseconds
        expandTransitionSet.addTransition(changeBounds);

        // create a new Fade object for the lyric (set the target to lyrics)
        Fade fadeLyrics = new Fade();
        fadeLyrics.addTarget(R.id.lyrics);
        fadeLyrics.setDuration(150); //in milliseconds

        // add this lyric fade transitions to the Transition set
        expandTransitionSet.addTransition(fadeLyrics);

        //this is for collapse Scene
        // create new scene and set it to contain our transition layout using static method getSceneForLayout
        collapsedScene = Scene.getSceneForLayout(transitionRoot, R.layout.activity_album_detail,
                this);

        // fix the views in the new expanded layout using the populate method to fill the actual views
        collapsedScene.setEnterAction(new Runnable() {
            @Override
            public void run() {
                // we need to bind the views again:
                setBindingViews();

                //call the populate method to bind to the current resources to views
                populate();

                // set the current scene as collapse scene (optimize this please)
                currentScene = collapsedScene;

                //set up all listeners
                setListeners();
            }
        });

        // create transition set to regulate the orders of transitions and set it to be sequential
        // rename this to expanded transition set
        TransitionSet collapseTransitionSet = new TransitionSet();
        collapseTransitionSet.setOrdering(TransitionSet.ORDERING_SEQUENTIAL);

       // the resetBounds happens after lyrics are fading out

        // create a new Fade object for the lyric (set the target to lyrics)
        Fade fadeOutLyrics = new Fade();
        fadeOutLyrics.addTarget(R.id.lyrics);
        fadeOutLyrics.setDuration(150); //in milliseconds

        // add this lyric fade transitions to the Transition set
        collapseTransitionSet.addTransition(fadeOutLyrics);

        // add Change bounds into this Transition set
        ChangeBounds resetBounds = new ChangeBounds();
        resetBounds.setDuration(200); // in milliseconds
        collapseTransitionSet.addTransition(resetBounds);

        // set the transition manager for both collapse and expands
        transitionManager.setTransition(expandedScene, collapsedScene, collapseTransitionSet);
        transitionManager.setTransition(collapsedScene, expandedScene, expandTransitionSet);
        collapsedScene.enter(); //enter this scene, change all values with new ones
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

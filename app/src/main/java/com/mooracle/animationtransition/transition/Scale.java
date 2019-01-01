package com.mooracle.animationtransition.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.view.View;
import android.view.ViewGroup;

public class Scale extends Visibility {
    //override the onAppear method

    @Override
    public Animator onAppear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        return createScaleAnimator(view, 0, 1);
    }

    //override the onDisappear method

    @Override
    public Animator onDisappear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        return createScaleAnimator(view, 1, 0);
    }

    //create the helper method to create the scale animator
    private Animator createScaleAnimator (View view, float fromScale, float toScale){
        //prepare AnimatorSet
        AnimatorSet animatorSet = new AnimatorSet();

        //prepare object animator for each x and y
        ObjectAnimator x = ObjectAnimator.ofFloat(view, "scaleX", fromScale, toScale);
        ObjectAnimator y = ObjectAnimator.ofFloat(view, "scaleY", fromScale, toScale);

        //set them to play together
        animatorSet.playTogether(x, y);

        return animatorSet;
    }
}

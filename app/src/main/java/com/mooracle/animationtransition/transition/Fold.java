package com.mooracle.animationtransition.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.view.View;
import android.view.ViewGroup;

public class Fold extends Visibility {
    //implement onAppear

    @Override
    public Animator onAppear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        //return animator made by createFoldAnimator with folding false since initially it was not folded
        return createFoldAnimator(view, false);
    }

    //implement onDisappear

    @Override
    public Animator onDisappear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        //return animator made by createFoldAnimator with folding true since initially it was folded
        return createFoldAnimator(view, true);
    }

    //create helper method to create Animator object
    private Animator createFoldAnimator(View view, Boolean folding){
        //setting the start condition:
        int start = view.getTop();

        //setting the end condition
        int end = view.getTop() + view.getMeasuredHeight() - 1;

        //toggle if we are folded or not
        if (folding) {
            //if it is folded then we switch starts with end by using the int temp variable to temporary store
            int temp = start;
            start = end;
            end = temp;
        }

        //set the initial condition of the bottom:
        view.setBottom(start);

        return ObjectAnimator.ofInt(view, "bottom", start, end);
    }
}

#ANIMATION TRANSITION

##PROJECT DESCRIPTION
This project is to answer the problems arise from the 
original Animation transition courses from Treehouse. The 
original project file failed to work in workstation and Mac
environment.

Thus I decided to make this from scratch. Please note some
of the images are taken from the original project just for
consistency purposes.

##Documentation
The documentation is shared with the  [original android animation course project](https://github.com/mooracle/android-animations-transitions)
which is [this Google Docs](https://docs.google.com/document/d/16d5h4MTKKhYKzeFlnYeTuba7MuR7mOipVhwBgLFxi-0/edit?usp=sharing).

After this all commits will be registered also in the 
above documentation file.
##Notes
1. in the mac_xxhdpi the large drawables are moved from drawables folder to xxhdpi drawables folder. This is because the 
drawables invokes error when used in API 27 (Oreo) with Pixel 2 XL 1080 resolutions. 
2. The Recycler view in the Album list activity also changed to trully match parents on both directions, also the margins for
top and bottom to parents are set to 0 to give true scrolling effect on the recycler view. 
3. Note that in the amount of album art drawables list the list in the app is repeated (4 times at least) this is in accordance to the size specified in the Recycler View adapter which being multiplied by 4.
4. Now after basic animation the project code is all ready to be used for all the
rest of the course on animation and transition.
5. We make Optimization in [this GitHub commit](https://github.com/mooracle/AnimationTransition/commit/0ef4b368c5c4215271ac99c3d1a59907ecc76025) to provide features like in the butter knife that provides binding to views and to listeners.
6. We already have all transitions in place [after this commit](https://github.com/mooracle/AnimationTransition/commit/b8ffb7fc3a483adafe5901845980ca8974fb9e5e). 
Yes, the final extended custom transition is not exactly like the one in the course video, more like the original animation should run but we learn more about custom visibility animations.
7. There are some optimization for [Activity and Fragment Transition](https://github.com/mooracle/AnimationTransition/commit/574d79e4188e9a3eba0987a609649fecb920ebf3) which change the bundling of the option object inside the start activity rather than in separate instruction code.
8. There also some optimization for [Four Type Transition](https://github.com/mooracle/AnimationTransition/commit/ef1890866045c6e2b84ba2234de190691057f4fd) which add one more transition which is return transition.
9. Also there one branch for backward compatibility

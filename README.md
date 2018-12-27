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
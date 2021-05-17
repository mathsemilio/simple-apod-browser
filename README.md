# Simple APoD Browser
Astronomical Picture of the Day client application for Android.

## What is APoD?
APoD is short for Astronomical Picture of the Day, which is a website provided by NASA and 
the Michigan Technological University. According to the website, "Each day a different image or
photograph of our universe is featured, along with a brief explanation written by a professional
astronomer."

*For more info, refer to these links:*
<p><a href="https://en.wikipedia.org/wiki/Astronomy_Picture_of_the_Day">Astronomical Picture of the Day - Wikipedia</a></p>
<p><a href="https://apod.nasa.gov/apod/astropix.html">Astronomical Picture of the Day - Official Website</a></p>

## Application Objective
This application makes it easy to found and see the latest APoDs. It also enables the user to 
bookmark their favorite ones for easy future access. The app also features other interesting 
features, like "Pick APoD by Date" and "Get Random APoD".

**Important:** 
This application makes use of the APoD API, which is part of the open APIs provided by NASA.
For more info, please refer to this <a href="https://api.nasa.gov/">link.</a>

## Points to be fixed
1. Display of images on the image detail screen;

## Build instructions
Before building the project, you either need to provide your API key or use the "DEMO_KEY".
Open the APoDEndpoint.kt file and replace all occurrences of ```apiKeyProvider.getAPoDKey()``` with your key or with "DEMO_KEY".

**Note:** You can generate a API key at the <a href="https://api.nasa.gov/">NASA Open APIs website.</a>

## External Libraries used 
1. Room;
2. Retrofit;
3. Glide;
4. Kotlin Coroutines for Android;
5. Swipe-Refresh Layout;
6. PhotoView;
7. Jetpack Navigation Component;
8. Preferences (PreferenceX)

*For more info on the libraries used, refer to these links:*
<p><a href="https://developer.android.com/training/data-storage/room/">Room</a></p>
<p><a href="https://square.github.io/retrofit/">Retrofit</a></p>
<p><a href="https://github.com/bumptech/glide">Glide</a></p>
<p><a href="https://developer.android.com/kotlin/coroutines">Kotlin Coroutines for Android</a></p>
<p><a href="https://developer.android.com/jetpack/androidx/releases/swiperefreshlayout">Swipe-Refresh Layout</a></p>
<p><a href="https://github.com/Baseflow/PhotoView">PhotoView</a></p>
<p><a href="https://developer.android.com/guide/navigation/navigation-getting-started">Jetpack Navigation Component</a></p>
<p><a href="https://developer.android.com/jetpack/androidx/releases/preference/">Preferences (PreferenceX)</a></p>

## License
```
Copyright 2021 Matheus Menezes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

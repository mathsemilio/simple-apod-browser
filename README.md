# Simple APOD Browser
Astronomical Picture of the Day client application for Android.

## Objective
This application makes it easy to find and see the latest APODs. It also enables the user to bookmark their favorite ones for easy future access. The app also features other interesting features, like picking APOD by date and "Feeling lucky...", which picks an random APOD.

### What is APOD?
APOD is short for [Astronomical Picture of the Day](https://en.wikipedia.org/wiki/Astronomy_Picture_of_the_Day), which is a website provided by NASA and the Michigan Technological University. According to the [official website](https://apod.nasa.gov/apod/astropix.html): "Each day a different image or photograph of our universe is featured, along with a brief explanation written by a professional astronomer."

**Important:** 
This application makes use of the APOD API, which is part of the <a href="https://api.nasa.gov/">open APIs</a> provided by NASA.

## Build Instructions
Before building the project, you either need to provide your own key or use the API's demo key.
Open the ```ApodApi.kt``` file and replace all occurrences of ```APIKeyProvider.key``` with your key or with "DEMO_KEY".

**Note:** You can generate an API key at the <a href="https://api.nasa.gov/">NASA Open APIs website</a>.

## Development Technologies
3rd party libraries used:

1. <a href="https://developer.android.com/training/data-storage/room/">Room</a>;
2. <a href="https://square.github.io/retrofit/">Retrofit</a>;
3. <a href="https://github.com/bumptech/glide">Glide</a>;
4. <a href="https://developer.android.com/kotlin/coroutines">Kotlin Coroutines for Android</a>;
5. <a href="https://developer.android.com/jetpack/androidx/releases/swiperefreshlayout">Swipe-Refresh Layout</a>;
6. <a href="https://github.com/Baseflow/PhotoView">PhotoView</a>;
7. <a href="https://developer.android.com/guide/navigation/navigation-getting-started">Jetpack Navigation Component</a>;
8. <a href="https://developer.android.com/jetpack/androidx/releases/preference/">Jetpack Preference</a>.

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

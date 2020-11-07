# tawkPracticalExam
Simple Mobile App for fetching list of github users and showing respective profile.

#### Built using MVVM Architechture

### Features
#### 1. Display List of Github Users
#### 2. Display Github user Profile
#### 3. Add notes per user
#### 4. Functioning in offline mode
#### 5. Supports dark mode

### Dependencies

#### Shimmer
**Used for telling users that there's something loading in a fancy way.**
  * implementation 'com.facebook.shimmer:shimmer:0.5.0'

#### Room
**Used for caching**
  * implementation "androidx.room:room-runtime:2.2.5"
  * implementation "androidx.room:room-ktx:2.2.5"

#### Gson
**Deserializer**
  * implementation 'com.google.code.gson:gson:2.8.6'
  * implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

#### Retrofit
**Network/Api Calls**
  * implementation 'com.squareup.retrofit2:retrofit:2.9.0'
  * implementation 'com.squareup.okhttp3:logging-interceptor:4.9.0'

#### Coil
**Image Loading**
  * implementation 'io.coil-kt:coil:1.0.0'




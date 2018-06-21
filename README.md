# Morph Bottom Navigation  
[ ![Download](https://api.bintray.com/packages/tbuonomo/maven/morph-bottom-navigation/images/download.svg) ](https://bintray.com/tbuonomo/maven/morph-bottom-navigation/_latestVersion)

This library represents a Bottom Navigation with an awesome morph effect on top of the selected item.

![ezgif com-crop](https://user-images.githubusercontent.com/15737675/41735760-d633e706-758a-11e8-9f30-3f07c8ed4371.gif)

Don't forget to star the project if you like it! 
![star](https://user-images.githubusercontent.com/15737675/39397370-85f5b294-4afe-11e8-9c02-0dfdf014136a.png)
 == ![heart](https://user-images.githubusercontent.com/15737675/39397367-6e312c2e-4afe-11e8-9fbf-32001b0165a1.png)
 
 And feel free to submit issues and enhancement requests !
 
 ![ezgif com-video-to-gif](https://user-images.githubusercontent.com/15737675/41736506-026b6fd6-758d-11e8-9be6-7bc217aaa1e8.gif)
![ezgif com-video-to-gif 1](https://user-images.githubusercontent.com/15737675/41736508-03b14cf8-758d-11e8-8bef-3909528f405d.gif)
![ezgif com-video-to-gif 2](https://user-images.githubusercontent.com/15737675/41736510-04dc9ce0-758d-11e8-9951-11d924ebc6b0.gif)


## How to
#### Gradle
```Gradle
dependencies {
    implementation 'com.tbuonomo:morph-bottom-navigation:1.0.1'
}
```
#### Usage
`MorphBottomNavigationView` extends the `BottomNavigationView` from the official [Google Material Component repository](https://github.com/material-components/material-components-android), so you can use it as described on the [Android Developers documentation](https://developer.android.com/reference/com/google/android/material/bottomnavigation/BottomNavigationView)

#### In your XML layout
```Xml
<com.tbuonomo.morphbottomnavigation.MorphBottomNavigationView
    android:id="@+id/bottomNavigationView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:layout_constraintBottom_toBottomOf="parent"
    app:menu="@menu/menu_bottom_navigation"
    app:backgroundTint="@color/colorPrimary"
    app:morphCornerRadius="128dp"
    app:morphItemRadius="64dp"
    app:morphVerticalOffset="8dp"
    style="@style/BottomNavigationView"
    />
```

#### Custom Attributes
| Attribute | Description |
| --- | --- |
| `backgroundTint` | Color of the bottom navigation background |
| `morphCornerRadius` | Radius in dp of the morph corners (by default 128dp) |
| `morphItemRadius` | Radius in dp of the morph item circle shape (by default 64dp) |
| `morphVerticalOffset` | The vertical offset of the morph shape above the bottom navigation (by default 8dp) |



## License
    Copyright 2018 Tommy Buonomo
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


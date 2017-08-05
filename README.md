# Mango(In progress)

Mango is an Android App for [Dribbble](https://dribbble.com/). Inspired by [Resplash](https://github.com/b-lam/Resplash), [Plaid](https://github.com/nickbutcher/plaid) and  [Protein](https://github.com/gejiaheng/Protein).

**STILL UNDER DEVELOPING**.

## Features
+ Kotlin and RxKotlin: Yes, fully written in Kotlin.
+ MVP architecture: Whole project is built on MVP architecture.
+ Material Design: Trying my best. No bottom navigation bar🙈.
+ Deep link: Yes. Using [DeepLinkDispatch](https://github.com/airbnb/DeepLinkDispatch) from [Airbnb](https://github.com/airbnb).
+ Dependency injection: Yes, using [dagger2](https://github.com/google/dagger).
+ Usage of awesome libraries: [Anko common](https://github.com/Kotlin/anko), [Flexbox layout](https://github.com/google/flexbox-layout), [Glide](https://github.com/bumptech/glide), [Gson](https://github.com/google/gson), [Retrofit](https://github.com/square/retrofit), [Room](https://developer.android.com/topic/libraries/architecture/room.html).

## Screenshots

| Main     | Shot     | User     |
| :-------------: | :-------------: | :-------------: |
| ![Main](./art/homescreen.png) | ![Shot](./art/shot_detail.png) | ![User](./art/user_profile.png) |

## Build
### Open the project in Android Studio
```
git clone https://github.com/TonnyL/Mango.git
```

Open the `Mango/` directory in Android Studio.

### Troubleshooting
+ Update the Android Studio to latest version.
+ Update the Kotlin to lates version.
+ Try to clean the project and rebuild it.
+ If none of the solutions above, file an issue or email me.

### Dribbble OAuth
The App client id, client secret and client access token are placed in [ApiConstants.kt](./app/src/main/java/io/github/tonnyl/mango/retrofit/ApiConstants.kt) file. But due to the [Rate Limiting](http://developer.dribbble.com/v1/#rate-limiting) and the possibility that Mango App may be available in Google Play (Check the [blueprints](https://github.com/TonnyL/Mango/wiki/Blueprints) for more details), if you want to continue the development, I suggest you register your own App at [Dribbble Developer Center](https://dribbble.com/account/applications/new) and modify the constants with your owns.

## License
```
MIT License

Copyright (c) 2017 Lizhaotailang

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```

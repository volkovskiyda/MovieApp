#### Movie app

##### Environment

- Android Studio Bumblebee | 2021.1.1 Beta 2
- OpenJDK version 11.0.12
- Kotlin version 1.5.31
- Gradle 7.2
- Hilt 2.40

#### Project requirements

* Specify **movieapp-service-account.json** to deploy build with `Fastlane`
* Set **release signing configs** in `app/keystore.properties` to deploy `release` build:
  `releaseStorePassword` `releaseKeyAlias` `releaseKeyPassword`
* Set **apiKey** value in `app/apikey.properties`

- ***Optional***: ignore **apiKey** -> **git update-index --skip-worktree app/apikey.properties**

###### You can see video cast at [`youtube`](https://www.youtube.com/watch?v=mPKpoz3BTRA&list=PLK7Hkn6sI-e280phjJ0ufKTSt9DDLecPb)

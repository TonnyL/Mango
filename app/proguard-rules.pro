# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/lizhaotailang/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# ===== Kotlin =====
-dontwarn kotlin.**
-keepclassmembers class **$WhenMappings {
    <fields>;
}

# ===== Retrofit =====
-dontwarn okio.**
-dontwarn javax.annotation.**
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

# ===== DeepLinkDispatch =====
-keep class com.airbnb.deeplinkdispatch.** { *; }
-keepclasseswithmembers class * {
     @com.airbnb.deeplinkdispatch.DeepLink <methods>;
}

# ===== Glide =====
# -keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * implements com.bumptech.glide.module.GlideModule
# -keep public class * extends com.bumptech.glide.AppGlideModule
-keep class * extends com.bumptech.glide.AppGlideModule
# -keep enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
-keep enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# ===== FlexBox Layout =====
# The FlexboxLayoutManager may be set from a layout xml, in that situation the RecyclerView
# tries to instantiate the layout manager using reflection.
# This is to prevent the layout manager from being obfuscated.
-keep public class com.google.android.flexbox.FlexboxLayoutManager

# ===== Gson =====
-keep class com.google.gson.** { *; }
-keepattributes Signature


# ===== RxJava =====
-dontwarn rx.**

# ===== Room =====
-dontwarn android.arch.util.paging.CountedDataSource
-dontwarn android.arch.persistence.room.paging.LimitOffsetDataSource
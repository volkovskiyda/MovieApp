# ktor serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** { *** Companion; }
-keepclasseswithmembers class kotlinx.serialization.json.** { kotlinx.serialization.KSerializer serializer(...); }
-keep,includedescriptorclasses class com.gmail.volkovskiyda.**$$serializer { *; }
-keepclassmembers class com.gmail.volkovskiyda.** { *** Companion; }
-keepclasseswithmembers class com.gmail.volkovskiyda.** { kotlinx.serialization.KSerializer serializer(...); }

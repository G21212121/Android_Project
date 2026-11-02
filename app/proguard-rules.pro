# Dodaj reguły ProGuard specyficzne dla projektu poniżej.
# Domyślnie plik konfiguracji proguard-android-optimize.txt jest dołączany automatycznie.

# ── Aplikacja ─────────────────────────────────────────────────
-keep class com.boulderingapp.** { *; }

# ── Hilt / Dagger ─────────────────────────────────────────────
-keep class dagger.hilt.** { *; }
-keep @dagger.hilt.android.lifecycle.HiltViewModel class * { *; }

# ── Retrofit ──────────────────────────────────────────────────
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# ── OkHttp ────────────────────────────────────────────────────
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**

# ── Gson ──────────────────────────────────────────────────────
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory { *; }
-keep class * implements com.google.gson.JsonSerializer { *; }
-keep class * implements com.google.gson.JsonDeserializer { *; }

# ── Room ──────────────────────────────────────────────────────
-keep class * extends androidx.room.RoomDatabase { *; }
-dontwarn androidx.room.**

# ── Kotlinx Coroutines ────────────────────────────────────────
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

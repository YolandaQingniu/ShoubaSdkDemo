### Permissions we need

#### On Android 12 or higher we need

> android.permission.BLUETOOTH_ADVERTISE
>
> android.permission.BLUETOOTH_SCAN
>
> android.permission.BLUETOOTH_CONNECT

#### Below android12 we need

> android.permission.BLUETOOTH
>
> android.permission.BLUETOOTH_ADMIN
>
> android.permission.ACCESS_COARSE_LOCATION
>
> android.permission.ACCESS_FINE_LOCATION

### Google's official documentation:

https://developer.android.com/guide/topics/connectivity/bluetooth/permissions

### ProGuard

```
-keep class com.qingniu.** {*;}

-keep classmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
```

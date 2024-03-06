### API文档
https://yolandaqingniu.github.io/ShoubaSDK_doc/zh/

### 所需权限

#### 如果您的app的targetSdk>30，且手机的系统版本为安卓12及以上

> android.permission.BLUETOOTH_ADVERTISE
>
> android.permission.BLUETOOTH_SCAN
>
> android.permission.BLUETOOTH_CONNECT

#### 否则需要

> android.permission.BLUETOOTH
>
> android.permission.BLUETOOTH_ADMIN
>
> android.permission.ACCESS_COARSE_LOCATION
>
> android.permission.ACCESS_FINE_LOCATION

### Google官方适配文档:

https://developer.android.com/guide/topics/connectivity/bluetooth/permissions

### 混淆

```
-keep classmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
```

### 引用

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

Core Plugin:

> implementation "com.github.YolandaQingniu.ShoubaSdkDemo:QNPluginX:1.0.1"

Scale Plugin:

> implementation "com.github.YolandaQingniu.ShoubaSdkDemo:QNScalePluginX:1.0.1"

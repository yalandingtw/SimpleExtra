# SimpleExtra
Android annotation injection library for parse bundle.


## 如何使用 Usage
 - Add Repository in Global build.gradle: 
```
repositories {
    maven { url 'https://dl.bintray.com/ch8154/maven' }
}
```
  

 - Add dependency in buidle.gradle:

```
dependencies {
    compile 'tw.yalan:simple-extra:1.0.0'
}
```

 - Add @Extra/@NestedExtra annotation on Field
```
    @Extra(key = "needLogin", defaultValue = DefaultValue.TRUE)
    boolean extraBooleanDefaultTrue;
    
    @Extra(key = "TESTStringArray")
    String[] testStringArray;
    
    @Extra(key = "DemoObject")
    DemoObjectParcelable singleObject;
    
    @NestedExtra(keys = {"BUNDLE", "NESTED"})
    String nextedValue;
```
 - Call SimpleExtra.inject() anywhere you want.
```
    SimpleExtra.inject(this, bundle);
```

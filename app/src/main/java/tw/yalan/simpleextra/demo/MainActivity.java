package tw.yalan.simpleextra.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import tw.yalan.simpleextra.SimpleExtra;
import tw.yalan.simpleextra.annotations.ArrayListExtra;
import tw.yalan.simpleextra.annotations.NestedExtra;
import tw.yalan.simpleextra.base.DefaultValue;
import tw.yalan.simpleextra.annotations.Extra;

public class MainActivity extends AppCompatActivity {
    @Extra(key = "int")
    int extraInt;
    @Extra(key = "string")
    String extraString;
    @Extra(key = "float")
    float extraFloat;
    @Extra(key = "double")
    double extraDouble;
    @Extra(key = "long")
    long extraLong;
    @Extra(key = "boolean")
    boolean extraBoolean;
    @Extra(key = "needLogin", defaultValue = DefaultValue.TRUE)
    boolean extraBooleanDefaultTrue;
    @Extra(key = "TESTStringArray")
    String[] testStringArray;
    @NestedExtra(keys = {"BUNDLE", "NESTED"})
    String nextedValue;
    @Extra(key = "DemoObject")
    DemoObjectParcelable singleObject;
    @Extra(key = "DemoObjectArray")
    DemoObjectParcelable[] objectsArray;
    @Extra(key = "StringArrayList")
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundleNested = new Bundle();
        bundleNested.putString("NESTED", "NESTED VALUE");
        Bundle bundle = new Bundle();
        bundle.putInt("int", 123);
        bundle.putString("string", "string123");
        bundle.putFloat("float", 888);
        bundle.putDouble("double", 777);
        bundle.putLong("long", 666);
        bundle.putBoolean("boolean", true);
        bundle.putStringArray("TESTStringArray", new String[]{"1", "2", "3"});
        bundle.putBundle("BUNDLE", bundleNested);
        bundle.putParcelable("DemoObject", new DemoObjectParcelable("TiTLE", "Name", "value"));
        bundle.putParcelableArray("DemoObjectArray", new DemoObjectParcelable[]{new DemoObjectParcelable("TiTLE1", "Name1", "value1")
                , new DemoObjectParcelable("TiTLE2", "Name2", "value2")
                , new DemoObjectParcelable("TiTLE3", "Name3", "value3")});
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("String1");
        stringArrayList.add("String2");
        stringArrayList.add("String3");
        bundle.putStringArrayList("StringArrayList", stringArrayList);
        long startTime = System.currentTimeMillis();
        SimpleExtra.inject(this, bundle);
        long endTime = System.currentTimeMillis();
        Log.d("SimpleExtra", "time:" + (endTime - startTime));

        Log.d("SimpleExtra", "int:" + extraInt);
        Log.d("SimpleExtra", "string:" + extraString);
        Log.d("SimpleExtra", "float:" + extraFloat);
        Log.d("SimpleExtra", "double:" + extraDouble);
        Log.d("SimpleExtra", "long:" + extraLong);
        Log.d("SimpleExtra", "boolean:" + extraBoolean);
        Log.d("SimpleExtra", "needLogin:" + extraBooleanDefaultTrue);
        Log.d("SimpleExtra", "testStringArray:" + Arrays.toString(testStringArray));
        Log.d("SimpleExtra", "nextedValue:" + nextedValue);
        Log.d("SimpleExtra", "DemoObject:" + singleObject.toString());
        Log.d("SimpleExtra", "DemoObject Array:" + Arrays.toString(objectsArray));
        Log.d("SimpleExtra", "String Array List:" + arrayList.toString());
    }
}

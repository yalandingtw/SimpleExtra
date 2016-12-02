package tw.yalan.simpleextra.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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
        SimpleExtra.inject(this, bundle);

        Log.d("SimpleExtra", "int:" + extraInt);
        Log.d("SimpleExtra", "string:" + extraString);
        Log.d("SimpleExtra", "float:" + extraFloat);
        Log.d("SimpleExtra", "double:" + extraDouble);
        Log.d("SimpleExtra", "long:" + extraLong);
        Log.d("SimpleExtra", "boolean:" + extraBoolean);
        Log.d("SimpleExtra", "needLogin:" + extraBooleanDefaultTrue);
        Log.d("SimpleExtra", "testStringArray:" + testStringArray.toString());
        Log.d("SimpleExtra", "nextedValue:" + nextedValue);

    }
}

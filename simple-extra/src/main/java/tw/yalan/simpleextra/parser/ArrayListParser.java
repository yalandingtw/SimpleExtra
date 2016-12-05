package tw.yalan.simpleextra.parser;

/**
 * Copyright (C) 2016 Alan Ding
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;

import tw.yalan.simpleextra.annotations.ArrayListExtra;
import tw.yalan.simpleextra.annotations.ArrayListNestedExtra;
import tw.yalan.simpleextra.base.Parser;

/**
 * Created by Alan Ding on 2016/12/2.
 */
public class ArrayListParser implements Parser<Bundle> {

    @Override
    public boolean parse(@NonNull Object object, @NonNull Bundle bindObject, @NonNull Field field) {
        ArrayListExtra annoExtra = field.getAnnotation(ArrayListExtra.class);
        ArrayListNestedExtra annoNestedExtra = field.getAnnotation(ArrayListNestedExtra.class);
        if (annoExtra != null) {
            return parseExtraField(object, bindObject, annoExtra, field);
        } else if (annoNestedExtra != null) {
            return parseNestedExtraField(object, bindObject, annoNestedExtra, field);
        }
        return false;
    }

    private boolean parseNestedExtraField(Object object, Bundle bindObject, ArrayListNestedExtra annoNestedExtra, Field field) {
        String[] keys = annoNestedExtra.keys();
        Class<?> targetType = annoNestedExtra.type();
        Class<?> type = field.getType();

        try {
            field.setAccessible(true);
            Bundle currentExtra = null;
            for (int i = 0; i < keys.length; i++) {
                if (i != keys.length - 1) {
                    currentExtra = bindObject.getBundle(keys[i]);
                    if (currentExtra == null) {
                        Log.e("SimpleExtra", "Array Nested extra parse failed on key:" + keys[i]);
                        return false;
                    }
                } else {
                    if (type.equals(ArrayList.class)) {
                        setArrayListValue(targetType, field, object, currentExtra, keys[i]);
                    }
//                    else if (type.equals(Array.class)) {
//                        setArrayValue(targetType, field, object, bindObject, keys[i]);
//                    }
                }
            }

        } catch (IllegalAccessException e) {
            Log.e("SimpleExtra", "Array Nested extra parse failed");
            return false;
        }
        return true;
    }

    private boolean parseExtraField(Object object, Bundle bindObject, ArrayListExtra annoExtra, Field field) {
        String key = annoExtra.key();
        Class<?> targetType = annoExtra.type();
        Class<?> type = field.getType();
        try {
            field.setAccessible(true);
            if (type.equals(ArrayList.class)) {
                setArrayListValue(targetType, field, object, bindObject, key);
            }
//            else {
//                setArrayValue(targetType, field, object, bindObject, key);
//            }
        } catch (IllegalAccessException e) {
            Log.e("SimpleExtra", "Inject field " + field.getName() + " failed. key:" + key, e);
            return false;
        }
        return true;
    }




    private void setArrayListValue(Class<?> fieldType, Field field, Object object, Bundle bindObject, String key) throws IllegalAccessException {
        if (fieldType.equals(String.class)) {
            field.set(object, bindObject.getStringArrayList(key));
        } else if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
            field.set(object, bindObject.getIntegerArrayList(key));
        } else if (fieldType.equals(Parcelable.class)) {
            field.set(object, bindObject.getParcelableArrayList(key));
        } else if (fieldType.equals(Serializable.class)) {
            field.set(object, bindObject.getSerializable(key));
        }
    }
}

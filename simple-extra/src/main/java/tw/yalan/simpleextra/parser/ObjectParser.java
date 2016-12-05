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

import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;

import tw.yalan.simpleextra.base.DefaultValue;
import tw.yalan.simpleextra.annotations.Extra;
import tw.yalan.simpleextra.annotations.NestedExtra;
import tw.yalan.simpleextra.base.Parser;

/**
 * Created by Alan Ding on 2016/12/2.
 */
public class ObjectParser implements Parser<Bundle> {

    @Override
    public boolean parse(@NonNull Object object, @NonNull Bundle bindObject, @NonNull Field field) {
        Extra annoExtra = field.getAnnotation(Extra.class);
        NestedExtra annoNestedExtra = field.getAnnotation(NestedExtra.class);
        if (annoExtra != null) {
            return parseExtraField(object, bindObject, annoExtra, field);
        } else if (annoNestedExtra != null) {
            return parseNestedExtraField(object, bindObject, annoNestedExtra, field);
        }
        return false;
    }

    private boolean parseNestedExtraField(Object object, Bundle bindObject, NestedExtra annoNestedExtra, Field field) {
        String[] keys = annoNestedExtra.keys();
        DefaultValue defaultValue = annoNestedExtra.defaultValue();
        if (defaultValue == DefaultValue.NO_DEFAULT) {
            defaultValue = null;
        }
        Class<?> fieldType = field.getType();
        try {
            field.setAccessible(true);
            Bundle currentExtra = null;
            for (int i = 0; i < keys.length; i++) {
                if (i != keys.length - 1) {
                    currentExtra = bindObject.getBundle(keys[i]);
                    if (currentExtra == null) {
                        Log.e("SimpleExtra", "Nested extra parse failed on key:" + keys[i]);
                        return false;
                    }
                } else {
                    setValue(fieldType, field, object, currentExtra, keys[i], defaultValue);
                }
            }

        } catch (Exception e) {
            Log.e("SimpleExtra", "Nested extra parse failed");
            return false;
        }
        return true;
    }

    private boolean parseExtraField(Object object, Bundle bindObject, Extra annoExtra, Field field) {
        String key = annoExtra.key();
        DefaultValue defaultValue = annoExtra.defaultValue();
        if (defaultValue == DefaultValue.NO_DEFAULT) {
            defaultValue = null;
        }
        Class<?> fieldType = field.getType();
        try {
            field.setAccessible(true);
            setValue(fieldType, field, object, bindObject, key, defaultValue);
        } catch (IllegalAccessException e) {
            Log.e("SimpleExtra", "Inject field " + field.getName() + " failed. key:" + key, e);
            return false;
        }
        return true;
    }

    private void setValue(Class<?> fieldType, Field field, Object object, Bundle bindObject, String key, DefaultValue defaultValue) throws IllegalAccessException {
        if (fieldType.equals(String.class)) {
            if (defaultValue == null) {
                field.set(object, bindObject.getString(key));
            } else {
                switch (defaultValue) {
                    case EMPTY_STRING:
                        field.set(object, bindObject.getString(key, ""));
                        break;
                    case NULL:
                    default:
                        field.set(object, bindObject.getString(key));
                        break;
                }
            }

        } else if (fieldType.equals(Boolean.class) || fieldType.equals(boolean.class)) {
            if (defaultValue == null) {
                field.set(object, bindObject.getBoolean(key));
            } else {
                switch (defaultValue) {
                    case TRUE:
                        field.set(object, bindObject.getBoolean(key, true));
                        break;
                    case FALSE:
                    default:
                        field.set(object, bindObject.getBoolean(key, false));
                        break;
                }
            }
        } else if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
            if (defaultValue == null) {
                field.set(object, bindObject.getInt(key));
            } else {
                switch (defaultValue) {
                    case ONE:
                        field.set(object, bindObject.getInt(key, 1));
                        break;
                    case ONE_NEGATIVE:
                        field.set(object, bindObject.getInt(key, -1));
                        break;
                    case ZERO:
                    default:
                        field.set(object, bindObject.getInt(key, 0));
                        break;
                }
            }
        } else if (fieldType.equals(Float.class) || fieldType.equals(float.class)) {
            if (defaultValue == null) {
                field.set(object, bindObject.getFloat(key));
            } else {
                switch (defaultValue) {
                    case ONE:
                        field.set(object, bindObject.getFloat(key, 1f));
                        break;
                    case ONE_NEGATIVE:
                        field.set(object, bindObject.getFloat(key, -1f));
                        break;
                    case ZERO:
                    default:
                        field.set(object, bindObject.getFloat(key, 0f));
                        break;
                }
            }
        } else if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
            if (defaultValue == null) {
                field.set(object, bindObject.getLong(key));
            } else {
                switch (defaultValue) {
                    case ONE:
                        field.set(object, bindObject.getLong(key, 1l));
                        break;
                    case ONE_NEGATIVE:
                        field.set(object, bindObject.getLong(key, -1l));
                        break;
                    case ZERO:
                    default:
                        field.set(object, bindObject.getLong(key, 0l));
                        break;
                }
            }
        } else if (fieldType.equals(Double.class) || fieldType.equals(double.class)) {
            if (defaultValue == null) {
                field.set(object, bindObject.getDouble(key));
            } else {
                switch (defaultValue) {
                    case ONE:
                        field.set(object, bindObject.getDouble(key, 1d));
                        break;
                    case ONE_NEGATIVE:
                        field.set(object, bindObject.getDouble(key, -1d));
                        break;
                    case ZERO:
                    default:
                        field.set(object, bindObject.getDouble(key, 0d));
                        break;
                }
            }
        } else if (fieldType.equals(Byte.class)) {
            field.set(object, bindObject.getByte(key));
        } else if (fieldType.equals(Bundle.class)) {
            field.set(object, bindObject.getBundle(key));
        } else if (fieldType.equals(Parcelable.class)) {
            field.set(object, bindObject.getParcelable(key));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2 && fieldType.equals(IBinder.class)) {
            field.set(object, bindObject.getBinder(key));
        } else if (fieldType.equals(ArrayList.class)) {
            Type genericType = field.getGenericType();
            setArrayListValue(genericType, field, object, bindObject, key);
        } else if (Serializable.class.isAssignableFrom(field.getType())) {
            field.set(object, bindObject.getSerializable(key));
        } else {
            setArrayValue(fieldType, field, object, bindObject, key);
        }
    }

    private void setArrayValue(Class<?> fieldType, Field field, Object object, Bundle bindObject, String key) throws IllegalAccessException {
        if (fieldType.equals(String[].class)) {
            field.set(object, bindObject.getStringArray(key));
        } else if (fieldType.equals(Boolean[].class) || fieldType.equals(boolean[].class)) {
            field.set(object, bindObject.getBooleanArray(key));
        } else if (fieldType.equals(Integer[].class) || fieldType.equals(int[].class)) {
            field.set(object, bindObject.getIntArray(key));
        } else if (fieldType.equals(Double[].class) || fieldType.equals(double[].class)) {
            field.set(object, bindObject.getDoubleArray(key));
        } else if (fieldType.equals(Float[].class) || fieldType.equals(float[].class)) {
            field.set(object, bindObject.getFloatArray(key));
        } else if (fieldType.equals(Long[].class) || fieldType.equals(long[].class)) {
            field.set(object, bindObject.getLongArray(key));
        } else if (fieldType.equals(Byte[].class)) {
            field.set(object, bindObject.getByteArray(key));
        } else if (fieldType.equals(Parcelable[].class)) {
            field.set(object, bindObject.getParcelableArray(key));
        } else if (fieldType.equals(Serializable[].class)) {
            field.set(object, bindObject.getSerializable(key));
        } else {
            field.set(object, bindObject.getSerializable(key));
        }
    }

    private void setArrayListValue(Type fieldType, Field field, Object object, Bundle bindObject, String key) throws IllegalAccessException {
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


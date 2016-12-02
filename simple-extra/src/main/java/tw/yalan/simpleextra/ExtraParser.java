package tw.yalan.simpleextra;

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
import android.util.Log;

import java.io.Serializable;
import java.lang.reflect.Field;

import tw.yalan.simpleextra.base.DefaultValue;
import tw.yalan.simpleextra.base.Extra;
import tw.yalan.simpleextra.base.Parser;

/**
 * Created by Alan Ding on 2016/12/2.
 */
public class ExtraParser implements Parser<Bundle> {

    @Override
    public void parse(Object object, Bundle bindObject) {
        Field[] fields = object.getClass().getFields();
        for (Field field : fields) {
            Extra annotation = field.getAnnotation(Extra.class);
            if (annotation != null) {
                String key = annotation.key();
                DefaultValue defaultValue = annotation.defaultValue();
                if (defaultValue == DefaultValue.NO_DEFAULT) {
                    defaultValue = null;
                }
                Class<?> fieldType = field.getType();
                try {
                    field.setAccessible(true);
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

//                    throw new Exception("UUID's Name must to be 'String' type.");
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
                                case ZERO:
                                default:
                                    field.set(object, bindObject.getLong(key, 0l));
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
                                    field.set(object, bindObject.getLong(key, 1l));
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
                                    field.set(object, bindObject.getDouble(key, 1.0));
                                    break;
                                case ZERO:
                                default:
                                    field.set(object, bindObject.getDouble(key, 0.0));
                                    break;
                            }
                        }
                    } else if (fieldType.equals(Byte.class)) {
                        field.set(object, bindObject.getByte(key));
                    } else if (fieldType.equals(Bundle.class)) {
                        field.set(object, bindObject.getBundle(key));
                    } else if (fieldType.equals(Parcelable.class)) {
                        field.set(object, bindObject.getParcelable(key));
                    } else if (fieldType.equals(Serializable.class)) {
                        field.set(object, bindObject.getSerializable(key));
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2 && fieldType.equals(IBinder.class)) {
                        field.set(object, bindObject.getBinder(key));
                    }
                } catch (IllegalAccessException e) {
                    Log.e("SimpleExtra", "Inject field " + field.getName() + " failed. key:" + key, e);
//                        e.printStackTrace();
                }
//                nameMap.put((String) field.get(object), name);
            }
        }
    }
}

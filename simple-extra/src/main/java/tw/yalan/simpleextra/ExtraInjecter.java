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

import android.os.Bundle;

import java.lang.reflect.Field;

import tw.yalan.simpleextra.annotations.Extra;
import tw.yalan.simpleextra.annotations.NestedExtra;
import tw.yalan.simpleextra.base.Injecter;
import tw.yalan.simpleextra.parser.ObjectParser;

/**
 * Created by Alan Ding on 2016/12/2.
 */
public class ExtraInjecter implements Injecter<Bundle> {
    private boolean isSuccess = false;
    private ObjectParser parser;

    protected ExtraInjecter() {
    }

    @Override
    public Injecter<Bundle> bind(Object object, Bundle bindObject) {
        if (bindObject == null) {
            isSuccess = false;
            return this;
        }
        parser = new ObjectParser();

        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.getAnnotation(Extra.class) != null || field.getAnnotation(NestedExtra.class) != null)
                parser.parse(object, bindObject, field);
        }

        isSuccess = true;
        return this;
    }

    @Override
    public boolean isSuccess() {
        return isSuccess;
    }

    @Override
    public void recycle() {
        parser = null;
    }
}

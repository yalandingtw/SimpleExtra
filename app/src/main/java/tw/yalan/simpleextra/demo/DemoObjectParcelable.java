package tw.yalan.simpleextra.demo;

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

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Alan Ding on 2016/12/5.
 */
public class DemoObjectParcelable implements Parcelable {
    public String title;
    public String name;
    public String value;

    public DemoObjectParcelable(String title, String name, String value) {
        this.title = title;
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "DemoObject{" +
                "title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.name);
        dest.writeString(this.value);
    }

    public DemoObjectParcelable() {
    }

    protected DemoObjectParcelable(Parcel in) {
        this.title = in.readString();
        this.name = in.readString();
        this.value = in.readString();
    }

    public static final Creator<DemoObjectParcelable> CREATOR = new Creator<DemoObjectParcelable>() {
        @Override
        public DemoObjectParcelable createFromParcel(Parcel source) {
            return new DemoObjectParcelable(source);
        }

        @Override
        public DemoObjectParcelable[] newArray(int size) {
            return new DemoObjectParcelable[size];
        }
    };
}

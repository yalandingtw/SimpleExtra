package tw.yalan.simpleextra.base;

import android.os.Bundle;

import java.lang.reflect.Field;

/**
 * Created by Alan Ding on 2016/12/2.
 */

public interface Parser<T> {
    boolean parse(Object object, T parseObject, Field field);
}

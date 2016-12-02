package tw.yalan.simpleextra.base;

import android.os.Bundle;

/**
 * Created by Alan Ding on 2016/12/2.
 */

public interface Parser<T> {
    void parse(Object object, T parseObject);
}

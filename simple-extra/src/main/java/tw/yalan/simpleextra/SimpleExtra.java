package tw.yalan.simpleextra;

import android.os.Bundle;

/**
 * Created by Alan Ding on 2016/12/2.
 */
public class SimpleExtra {
    private static SimpleExtra ourInstance = new SimpleExtra();
    private ExtraInjecter injecter;

    public static void inject(Object object, Bundle extra) {
        ourInstance.init();
        ourInstance.getExtraInjecter().bind(object, extra);
    }


    private SimpleExtra() {
    }

    private void init() {
        if (injecter != null) {
            injecter.recycle();
        }
        injecter = new ExtraInjecter();
    }

    public ExtraInjecter getExtraInjecter() {
        return injecter;
    }
}

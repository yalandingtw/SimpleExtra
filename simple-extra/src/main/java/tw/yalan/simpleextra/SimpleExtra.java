package tw.yalan.simpleextra;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;

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

    public static void inject(Activity activity) {
        Intent intent = activity.getIntent();
        if (intent != null) {
            Bundle extra = intent.getExtras();
            ourInstance.init();
            ourInstance.getExtraInjecter().bind(activity, extra);
        } else {
            Log.e("SimpleExtra", "Intent is null.");
        }
    }

    public static void inject(Fragment fragment) {
        Bundle extra = fragment.getArguments();
        ourInstance.init();
        ourInstance.getExtraInjecter().bind(fragment, extra);
    }

    public static void inject(DialogFragment dialogFragment) {
        Bundle extra = dialogFragment.getArguments();
        ourInstance.init();
        ourInstance.getExtraInjecter().bind(dialogFragment, extra);
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

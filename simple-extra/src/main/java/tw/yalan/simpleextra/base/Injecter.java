package tw.yalan.simpleextra.base;

/**
 * Created by Alan Ding on 2016/12/2.
 */

public interface Injecter<T> {
    Injecter<T> bind(Object object, T bindObject);

    boolean isSuccess();

    void recycle();
}

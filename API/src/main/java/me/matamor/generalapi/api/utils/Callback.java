package me.matamor.generalapi.api.utils;

public interface Callback<T> {

    void done(T result, Exception exception);

}

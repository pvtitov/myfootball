package ru.github.pvtitov.myfootball.contracts.mvp.base;

public interface Presenter<V extends View> {

    void attachView(V view);
    void detachView();
}

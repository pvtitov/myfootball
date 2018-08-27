package ru.github.pvtitov.myfootball.contracts.mvp.base;

public abstract class BasePresenter<V extends View, R extends Repository> implements Presenter<V> {

    private V view;
    private R repository;

    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public V getView() {
        return this.view;
    }

    public void setRepository(R repository) {
        this.repository = repository;
    }

    public R getRepository() {
        return repository;
    }

    public void transmitError(String message) {
        view.showMessage(message);
    }
}

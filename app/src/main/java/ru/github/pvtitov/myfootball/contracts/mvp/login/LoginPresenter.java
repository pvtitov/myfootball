package ru.github.pvtitov.myfootball.contracts.mvp.login;

import ru.github.pvtitov.myfootball.contracts.mvp.base.BasePresenter;

public abstract class LoginPresenter extends BasePresenter<LoginView, LoginRepository> {

    public void signIn() {
        LoginView view = this.getView();
        String login = view.getLogin();
        String password = view.getPassword();

        LoginRepository repository = this.getRepository();
        repository.signIn(login, password);
    }

    public void register() {
        LoginView view = this.getView();
        String login = view.getLogin();
        String password = view.getPassword();

        LoginRepository repository = this.getRepository();
        repository.register(login, password);
    }

    public void signOut() {
        LoginRepository repository = this.getRepository();
        repository.signOut();
    }

    public abstract void signedInSuccessfully();

    public abstract void registeredSuccessfully();
}

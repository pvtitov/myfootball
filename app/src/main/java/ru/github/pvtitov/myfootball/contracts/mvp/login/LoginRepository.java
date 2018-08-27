package ru.github.pvtitov.myfootball.contracts.mvp.login;

import ru.github.pvtitov.myfootball.contracts.mvp.base.Repository;

public abstract class LoginRepository implements Repository {
    public abstract void signIn(String login, String password);
    public abstract void register(String login, String password);
    public abstract void signOut();
}

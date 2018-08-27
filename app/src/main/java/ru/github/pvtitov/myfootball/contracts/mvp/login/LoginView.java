package ru.github.pvtitov.myfootball.contracts.mvp.login;

import ru.github.pvtitov.myfootball.contracts.mvp.base.View;

public interface LoginView extends View {

    String getLogin();
    String getPassword();

    void close();
}

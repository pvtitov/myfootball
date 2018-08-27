package ru.github.pvtitov.myfootball.contracts;

import java.util.Objects;

public class User {
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof User)) return false;

        User another = (User) obj;
        return Objects.equals(login, another.login);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(login);
    }
}

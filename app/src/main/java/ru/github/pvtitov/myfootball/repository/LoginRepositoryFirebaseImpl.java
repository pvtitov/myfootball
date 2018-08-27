package ru.github.pvtitov.myfootball.repository;

import android.support.annotation.NonNull;
import android.util.Patterns;

import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

import ru.github.pvtitov.myfootball.contracts.mvp.login.LoginPresenter;
import ru.github.pvtitov.myfootball.contracts.mvp.login.LoginRepository;


public class LoginRepositoryFirebaseImpl extends LoginRepository {
    private LoginPresenter presenter;
    private FirebaseAuth auth;

    public LoginRepositoryFirebaseImpl(LoginPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void signIn(String login, String password) {
        if (isProper(login, password)) {
            auth = FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(login, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            presenter.signedInSuccessfully();
                        }
                        else {
                            String message;
                            if (task.getException() != null) {
                                message = task.getException().getMessage();
                                presenter.transmitError(message);
                            }
                        }
                    });
        }
    }

    private boolean isProper(String login, String password) {
        if (!Patterns.EMAIL_ADDRESS.matcher(login).matches()) {
            presenter.transmitError("Убедитесь, что адрес почты указан правильно.");
            return false;
        }
        if (password.length() < 6) {
            presenter.transmitError("Пароль должен состоять не менее, чем из 6 символов.");
            return false;
        }
        return true;
    }

    @Override
    public void register(String login, String password) {
        if (isProper(login, password)) {
            auth = FirebaseAuth.getInstance();
            auth.createUserWithEmailAndPassword(login, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            presenter.registeredSuccessfully();
                        }
                        else {
                            String message;
                            if (task.getException() != null) {
                                message = task.getException().getMessage();
                                presenter.transmitError(message);
                            }
                        }
                    });
        }
    }

    @Override
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }
}

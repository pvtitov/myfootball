package ru.github.pvtitov.myfootball.presenter;


import com.google.firebase.auth.FirebaseAuth;

import ru.github.pvtitov.myfootball.contracts.mvp.login.LoginPresenter;
import ru.github.pvtitov.myfootball.contracts.mvp.login.LoginRepository;
import ru.github.pvtitov.myfootball.repository.LoginRepositoryFirebaseImpl;

public class LoginPresenterImpl extends LoginPresenter {

    private static LoginPresenter instance;

    public static LoginPresenter getInstance(){
        if (instance == null) instance = new LoginPresenterImpl();
        return instance;
    }

    private LoginPresenterImpl(){
        LoginRepository repository = new LoginRepositoryFirebaseImpl(this);
        setRepository(repository);
    }

    @Override
    public void signedInSuccessfully() {
        getView().showMessage("Signed in successfully");
        getView().close();
    }

    @Override
    public void registeredSuccessfully() {
        getView().showMessage("Registered successfully");
        getView().close();
    }
}

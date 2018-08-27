package ru.github.pvtitov.myfootball;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import ru.github.pvtitov.myfootball.contracts.mvp.login.LoginPresenter;
import ru.github.pvtitov.myfootball.contracts.mvp.login.LoginRepository;
import ru.github.pvtitov.myfootball.contracts.mvp.login.LoginView;
import ru.github.pvtitov.myfootball.presenter.LoginPresenterImpl;

import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    private LoginPresenter presenter;

    @Mock private LoginView view;
    @Mock private LoginRepository repository;

    @Before
    public void setUpPresenter() {
        presenter = LoginPresenterImpl.getInstance();
        presenter.attachView(view);
        presenter.setRepository(repository);
    }

    @Test
    public void attachView_ViewSetInsidePresenter() {
        assertNotNull(presenter.getView());
    }

    @Test public void detachView_ViewSetToNull() {
        presenter.detachView();
        assertNull(presenter.getView());
    }

    @Test public void transmitError_CallsShowMessageOnView() {
        presenter.transmitError("error");
        verify(view).showMessage("error");
    }

    @Test public void signIn_WithAnyLoginAndPassword_CallsSignInOnRepository() {
        setLoginAndPassword();
        presenter.signIn();
        verify(repository).signIn("login", "password");
    }

    @Test public void register_WithAnyLoginAndPassword_CallsRegisterOnRepository() {
        setLoginAndPassword();
        presenter.register();
        verify(repository).register("login", "password");
    }

    private void setLoginAndPassword() {
        when(presenter.getView().getLogin()).thenReturn("login");
        when(presenter.getView().getPassword()).thenReturn("password");
    }

    @Test public void signOut_CallsSignOutOnRepository() {
        presenter.signOut();
        verify(repository).signOut();
    }

    @Test public void signedInSuccessfully_CallsShowMessageOnView() {
        presenter.signedInSuccessfully();
        verify(view).showMessage(anyString());
    }

    @Test public void registeredSuccessfully_CallsShowMessageOnView() {
        presenter.registeredSuccessfully();
        verify(view).showMessage(anyString());
    }
}

package ru.github.pvtitov.myfootball.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ru.github.pvtitov.myfootball.R;
import ru.github.pvtitov.myfootball.contracts.mvp.login.LoginPresenter;
import ru.github.pvtitov.myfootball.contracts.mvp.login.LoginView;
import ru.github.pvtitov.myfootball.presenter.LoginPresenterImpl;

public class LoginFragment extends Fragment implements LoginView{

    private LoginPresenter presenter;
    private String login;
    private String password;
    private EditText loginEt;
    private EditText passwordEt;

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void close() {
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }

    private void setLoginAndPass() {
        login = loginEt.getText().toString();
        password = passwordEt.getText().toString();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        presenter = LoginPresenterImpl.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginEt = view.findViewById(R.id.login_et);
        passwordEt = view.findViewById(R.id.password_et);

        Button signInBtn = view.findViewById(R.id.login_btn);
        signInBtn.setOnClickListener(v -> {
            setLoginAndPass();
            presenter.signIn();
        });

        Button registerBtn = view.findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(v -> {
            setLoginAndPass();
            presenter.register();
        });

        Button signOutBtn = view.findViewById(R.id.logout_btn);
        signOutBtn.setOnClickListener(v -> {
            setLoginAndPass();
            presenter.signOut();
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        presenter.attachView(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        presenter.detachView();
    }
}

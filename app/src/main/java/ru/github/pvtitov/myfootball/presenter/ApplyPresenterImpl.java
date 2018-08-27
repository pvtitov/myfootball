package ru.github.pvtitov.myfootball.presenter;

import ru.github.pvtitov.myfootball.contracts.Game;
import ru.github.pvtitov.myfootball.contracts.User;
import ru.github.pvtitov.myfootball.contracts.mvp.apply_for_game.ApplyPresenter;
import ru.github.pvtitov.myfootball.contracts.mvp.apply_for_game.ApplyRepository;
import ru.github.pvtitov.myfootball.repository.ApplyRepositoryFirebaseImpl;

public class ApplyPresenterImpl extends ApplyPresenter {

    private static ApplyPresenter instance;

    public static ApplyPresenter getInstance() {
        if (instance == null) instance = new ApplyPresenterImpl();
        return instance;
    }

    private ApplyPresenterImpl() {
        ApplyRepository repository = new ApplyRepositoryFirebaseImpl(this);
        setRepository(repository);
    }

    @Override
    public void applyForGame(Game game, boolean addToCalendar) {
        User currentUser = getRepository().getCurrentUser();
        getRepository().applyForGame(game, currentUser);
        if (addToCalendar) getView().addToCalendar(game);
        getView().close();
    }
}

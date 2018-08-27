package ru.github.pvtitov.myfootball.presenter;

import java.util.List;

import ru.github.pvtitov.myfootball.contracts.Game;
import ru.github.pvtitov.myfootball.contracts.mvp.games_consumer.GamesConsumerPresenter;
import ru.github.pvtitov.myfootball.contracts.mvp.games_consumer.GamesConsumerRepository;
import ru.github.pvtitov.myfootball.repository.GamesConsumerRepositoryFirebaseImpl;

public class GamesConsumerPresenterImpl extends GamesConsumerPresenter {

    private static GamesConsumerPresenter instance;

    public static GamesConsumerPresenter getInstance() {
        if (instance == null) instance = new GamesConsumerPresenterImpl();
        return instance;
    }

    private GamesConsumerPresenterImpl() {
        GamesConsumerRepository repository = new GamesConsumerRepositoryFirebaseImpl(this);
        setRepository(repository);
    }

    @Override
    public void loadGames() {
        getRepository().loadGames();
    }

    @Override
    public void loadCurrentUserLogin() {
        getRepository().loadCurrentUserLogin();
    }

    @Override
    public void displayGames(List<Game> games) {
        if (getView() != null) getView().displayGames(games);
    }

    @Override
    public void displayLogin(String login) {
        if (!login.isEmpty()) getView().displayLogin(login);
    }
}

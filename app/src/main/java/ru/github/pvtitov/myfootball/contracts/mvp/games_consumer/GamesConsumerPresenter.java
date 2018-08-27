package ru.github.pvtitov.myfootball.contracts.mvp.games_consumer;

import java.util.List;

import ru.github.pvtitov.myfootball.contracts.Game;
import ru.github.pvtitov.myfootball.contracts.mvp.base.BasePresenter;

public abstract class GamesConsumerPresenter extends BasePresenter<GamesConsumerView, GamesConsumerRepository> {
    public abstract void loadGames();
    public abstract void loadCurrentUserLogin();
    public abstract void displayGames(List<Game> games);
    public abstract void displayLogin(String login);
}

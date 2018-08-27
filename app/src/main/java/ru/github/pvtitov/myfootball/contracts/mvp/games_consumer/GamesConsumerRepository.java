package ru.github.pvtitov.myfootball.contracts.mvp.games_consumer;

import ru.github.pvtitov.myfootball.contracts.mvp.base.Repository;

public abstract class GamesConsumerRepository implements Repository {
    public abstract void loadGames();
    public abstract void loadCurrentUserLogin();
}

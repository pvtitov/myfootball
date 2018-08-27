package ru.github.pvtitov.myfootball.contracts.mvp.game_publisher;

import ru.github.pvtitov.myfootball.contracts.Game;
import ru.github.pvtitov.myfootball.contracts.User;
import ru.github.pvtitov.myfootball.contracts.mvp.base.Repository;

public abstract class GamePublisherRepository implements Repository {
    public abstract void publishGame(Game game);
    public abstract User getCurrentUser();
}

package ru.github.pvtitov.myfootball.contracts.mvp.game_publisher;

import java.util.List;

import ru.github.pvtitov.myfootball.contracts.Game;
import ru.github.pvtitov.myfootball.contracts.User;
import ru.github.pvtitov.myfootball.contracts.mvp.base.BasePresenter;

public abstract class GamePublisherPresenter extends BasePresenter<GamePublisherView, GamePublisherRepository> {
    public abstract void publishGame();
    public abstract User getCurrentUser();
}

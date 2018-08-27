package ru.github.pvtitov.myfootball.contracts.mvp.apply_for_game;

import ru.github.pvtitov.myfootball.contracts.Game;
import ru.github.pvtitov.myfootball.contracts.mvp.base.BasePresenter;

public abstract class ApplyPresenter extends BasePresenter<ApplyView, ApplyRepository> {
    public abstract void applyForGame(Game game, boolean addToCalendar);
}

package ru.github.pvtitov.myfootball.contracts.mvp.apply_for_game;

import ru.github.pvtitov.myfootball.contracts.Game;
import ru.github.pvtitov.myfootball.contracts.User;
import ru.github.pvtitov.myfootball.contracts.mvp.base.Repository;

public abstract class ApplyRepository implements Repository {
    public abstract void applyForGame(Game game, User currentUser);
    public abstract User getCurrentUser();
}

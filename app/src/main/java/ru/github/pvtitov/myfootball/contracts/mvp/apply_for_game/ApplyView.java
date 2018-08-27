package ru.github.pvtitov.myfootball.contracts.mvp.apply_for_game;

import ru.github.pvtitov.myfootball.contracts.Game;
import ru.github.pvtitov.myfootball.contracts.mvp.base.View;

public interface ApplyView extends View{
    void addToCalendar(Game game);
    void close();
}

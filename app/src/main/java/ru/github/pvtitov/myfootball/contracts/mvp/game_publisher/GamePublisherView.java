package ru.github.pvtitov.myfootball.contracts.mvp.game_publisher;

import java.util.List;

import ru.github.pvtitov.myfootball.contracts.Game;
import ru.github.pvtitov.myfootball.contracts.mvp.base.View;

public interface GamePublisherView extends View {
    Game getGame();
    void close();
}

package ru.github.pvtitov.myfootball.contracts.mvp.games_consumer;

import java.util.List;

import ru.github.pvtitov.myfootball.contracts.Game;
import ru.github.pvtitov.myfootball.contracts.mvp.base.View;

public interface GamesConsumerView extends View {
    void displayLogin(String login);
    void displayGames(List<Game> games);
}

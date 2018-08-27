package ru.github.pvtitov.myfootball.presenter;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import ru.github.pvtitov.myfootball.contracts.Game;
import ru.github.pvtitov.myfootball.contracts.User;
import ru.github.pvtitov.myfootball.contracts.mvp.game_publisher.GamePublisherPresenter;
import ru.github.pvtitov.myfootball.contracts.mvp.game_publisher.GamePublisherRepository;
import ru.github.pvtitov.myfootball.repository.GamePublisherRepositoryFirebaseImpl;

public class GamePublisherPresenterImpl extends GamePublisherPresenter {

    private static GamePublisherPresenter instance;

    public static GamePublisherPresenter getInstance() {
        if (instance == null) instance = new GamePublisherPresenterImpl();
        return instance;
    }

    private GamePublisherPresenterImpl() {
        GamePublisherRepository repository = new GamePublisherRepositoryFirebaseImpl(this);
        setRepository(repository);
    }

    @Override
    public void publishGame() {
        Game game = getView().getGame();
        Calendar currentCalendar = Calendar.getInstance(TimeZone.getDefault());
        long currentTime = currentCalendar.getTimeInMillis();
        currentCalendar.add(Calendar.MONTH,1);
        long currentTimePlusMonth = currentCalendar.getTimeInMillis();

        long c = game.calculateTimeInMillis();
        if ((c > currentTime) && (c < currentTimePlusMonth)) {
            getRepository().publishGame(game);
            getView().close();
        } else {
            getView().showMessage("Необходимо выбрать дату и время. Игра объявляется не раньше, чем за месяц.");
        }
    }

    @Override
    public User getCurrentUser() {
        return getRepository().getCurrentUser();
    }
}

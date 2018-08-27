package ru.github.pvtitov.myfootball;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import ru.github.pvtitov.myfootball.contracts.Game;
import ru.github.pvtitov.myfootball.contracts.mvp.games_consumer.GamesConsumerPresenter;
import ru.github.pvtitov.myfootball.contracts.mvp.games_consumer.GamesConsumerRepository;
import ru.github.pvtitov.myfootball.contracts.mvp.games_consumer.GamesConsumerView;
import ru.github.pvtitov.myfootball.presenter.GamesConsumerPresenterImpl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GamesConsumerPresenterTest {

    private GamesConsumerPresenter presenter;

    @Mock private GamesConsumerView view;
    @Mock private GamesConsumerRepository repository;

    @Before
    public void setUpPresenter() {
        presenter = GamesConsumerPresenterImpl.getInstance();
        presenter.attachView(view);
        presenter.setRepository(repository);
    }

    @Test
    public void attachView_ViewSetInsidePresenter() {
        assertNotNull(presenter.getView());
    }

    @Test public void detachView_ViewSetToNull() {
        presenter.detachView();
        assertNull(presenter.getView());
    }

    @Test public void transmitError_CallsShowMessageOnView() {
        presenter.transmitError("error");
        verify(view).showMessage("error");
    }

    @Test public void loadGames_CallsLoadGamesOnRepository() {
        presenter.loadGames();
        verify(repository).loadGames();
    }

    @Test public void loadCurrentUserLogin_CallsLoadCurrentUserLoginOnRepository() {
        presenter.loadCurrentUserLogin();
        verify(repository).loadCurrentUserLogin();
    }

    @Test public void displayGames_CallsDisplayGamesOnView() {
        List<Game> games = new ArrayList<>();
        presenter.displayGames(games);
        verify(view).displayGames(games);
    }

    @Test public void displayLogin_CallsDisplayGamesOnView() {
        presenter.displayLogin("login");
        verify(view).displayLogin("login");
    }
}

package ru.github.pvtitov.myfootball;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import ru.github.pvtitov.myfootball.contracts.Game;
import ru.github.pvtitov.myfootball.contracts.User;
import ru.github.pvtitov.myfootball.contracts.mvp.apply_for_game.ApplyPresenter;
import ru.github.pvtitov.myfootball.contracts.mvp.apply_for_game.ApplyRepository;
import ru.github.pvtitov.myfootball.contracts.mvp.apply_for_game.ApplyView;
import ru.github.pvtitov.myfootball.presenter.ApplyPresenterImpl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApplyPresenterTest {

    private ApplyPresenter presenter;

    @Mock private ApplyView view;
    @Mock private ApplyRepository repository;

    @Before
    public void setUpPresenter() {
        presenter = ApplyPresenterImpl.getInstance();
        presenter.attachView(view);
        presenter.setRepository(repository);
    }

    @Test public void applyForGame_CallsApplyForGameOnRepository() {
        Game game = new Game();
        User currentUser = new User();
        when(repository.getCurrentUser()).thenReturn(currentUser);

        presenter.applyForGame(game, checkBox.isChecked());

        verify(repository).applyForGame(game, currentUser);
    }
}

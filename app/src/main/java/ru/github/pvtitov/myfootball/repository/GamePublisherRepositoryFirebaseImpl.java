package ru.github.pvtitov.myfootball.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ru.github.pvtitov.myfootball.contracts.Game;
import ru.github.pvtitov.myfootball.contracts.User;
import ru.github.pvtitov.myfootball.contracts.mvp.game_publisher.GamePublisherPresenter;
import ru.github.pvtitov.myfootball.contracts.mvp.game_publisher.GamePublisherRepository;

public class GamePublisherRepositoryFirebaseImpl extends GamePublisherRepository {

    private GamePublisherPresenter presenter;

    public GamePublisherRepositoryFirebaseImpl(GamePublisherPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void publishGame(Game game) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.push().setValue(game);
    }

    @Override
    public User getCurrentUser() {
        User user = new User();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            user.setLogin(firebaseUser.getEmail());
        }
        return user;
    }
}

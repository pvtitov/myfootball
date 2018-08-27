package ru.github.pvtitov.myfootball.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import ru.github.pvtitov.myfootball.contracts.Game;
import ru.github.pvtitov.myfootball.contracts.User;
import ru.github.pvtitov.myfootball.contracts.mvp.apply_for_game.ApplyPresenter;
import ru.github.pvtitov.myfootball.contracts.mvp.apply_for_game.ApplyRepository;

public class ApplyRepositoryFirebaseImpl extends ApplyRepository {

    private ApplyPresenter presenter;

    public ApplyRepositoryFirebaseImpl(ApplyPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void applyForGame(Game game, User currentUser) {
        game.addAttendee(currentUser);
        List<?> newAttendees = game.getAttendees();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child(game.getKey()).child("attendees").setValue(newAttendees);
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

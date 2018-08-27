package ru.github.pvtitov.myfootball.repository;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ru.github.pvtitov.myfootball.contracts.Game;
import ru.github.pvtitov.myfootball.contracts.mvp.games_consumer.GamesConsumerPresenter;
import ru.github.pvtitov.myfootball.contracts.mvp.games_consumer.GamesConsumerRepository;

public class GamesConsumerRepositoryFirebaseImpl extends GamesConsumerRepository implements FirebaseAuth.AuthStateListener {

    private GamesConsumerPresenter presenter;
    private FirebaseAuth firebaseAuth;

    public GamesConsumerRepositoryFirebaseImpl(GamesConsumerPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void loadGames() {
        List<Game> games = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference rootReference = database.getReference();
        rootReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                games.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    try {
                        Game game = ds.getValue(Game.class);

                        if (game != null) {

                            game.setKey(ds.getKey());

                            if (game.calculateTimeInMillis() < Calendar.getInstance(Locale.getDefault()).getTimeInMillis()) {
                                rootReference.child(game.getKey()).removeValue();
                            }

                            games.add(game);
                        }
                    } catch (DatabaseException e){
                        e.printStackTrace();
                    }
                }
                presenter.displayGames(games);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void loadCurrentUserLogin() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.addAuthStateListener(this);
        loadUser(firebaseAuth);
    }

    private void loadUser(FirebaseAuth firebaseAuth) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            presenter.displayLogin(firebaseUser.getEmail());
        } else {
            presenter.displayLogin("");
        }
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        loadUser(firebaseAuth);
    }
}

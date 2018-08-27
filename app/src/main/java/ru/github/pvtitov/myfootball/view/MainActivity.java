package ru.github.pvtitov.myfootball.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import ru.github.pvtitov.myfootball.R;
import ru.github.pvtitov.myfootball.contracts.Game;
import ru.github.pvtitov.myfootball.contracts.mvp.games_consumer.GamesConsumerPresenter;
import ru.github.pvtitov.myfootball.contracts.mvp.games_consumer.GamesConsumerView;
import ru.github.pvtitov.myfootball.presenter.GamesConsumerPresenterImpl;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GamesConsumerView{

    public static final String FRAGMENT_MAP = "FRAGMENT_MAP";
    public static final String FRAGMENT_APPLY = "FRAGMENT_APPLY";
    public static final String FRAGMENT_PUBLISH = "FRAGMENT_PUBLISH";
    public static final String FRAGMENT_LOGIN = "FRAGMENT_LOGIN";


    private GamesConsumerPresenter presenter;
    private CustomMapFragment mapFragment;
    private FragmentManager fragmentManager;
    private Toolbar toolbar;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = GamesConsumerPresenterImpl.getInstance();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        mapFragment = (CustomMapFragment) fragmentManager.findFragmentByTag(FRAGMENT_MAP);

        if (mapFragment == null){
            mapFragment = new CustomMapFragment();
            mapFragment.getMapAsync(MainActivity.this);
            mapFragment.setRetainInstance(true);
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, mapFragment, FRAGMENT_MAP).commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter.attachView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        presenter.detachView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.loadCurrentUserLogin();

        if(mapFragment != null){
            map = mapFragment.getMap();
        }

        if (map != null) setOnMarkerClickListener(map);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        mapFragment.saveMap(map);
        setOnMarkerClickListener(map);
        setOnLongClickListener(map);
        presenter.loadGames();
    }

    private void setOnMarkerClickListener(GoogleMap map) {
        map.setOnMarkerClickListener(marker -> {

            if (marker.getTag() instanceof NewGame) {
                showCreateGameDialog(marker.getPosition());
                marker.remove();
            } else {
                showDialogApply(marker);
            }
            return true;
        });
    }

    private void showDialogApply(Marker marker) {
        if (fragmentManager != null){

            ApplyFragment applyFragment = (ApplyFragment) fragmentManager.findFragmentByTag(FRAGMENT_APPLY);

            if (applyFragment != null) {
                fragmentManager.beginTransaction().remove(applyFragment).commit();
            }

            applyFragment = new ApplyFragment();
            Game game = (Game) marker.getTag();
            applyFragment.setGame(game);

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, applyFragment, FRAGMENT_APPLY)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void setOnLongClickListener(GoogleMap map) {
        map.setOnMapLongClickListener(this::drawNewGameMarker);
    }

    private void drawNewGameMarker(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ball_marker_picked))
                .position(latLng);
        Marker marker = map.addMarker(markerOptions);
        marker.setTag(new NewGame());
    }

    private void showCreateGameDialog(LatLng latLng) {
        if (fragmentManager != null) {

            PublishGameFragment publishGameFragment = (PublishGameFragment) fragmentManager.findFragmentByTag(FRAGMENT_PUBLISH);

            if (publishGameFragment == null) {
                publishGameFragment = new PublishGameFragment();
                publishGameFragment.setLatLng(latLng);
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, publishGameFragment, FRAGMENT_PUBLISH)
                        .addToBackStack(null)
                        .commitAllowingStateLoss();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_in_menu:
                showLoginDialog();
                // TODO presenter.showLoginDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showLoginDialog() {
        if(fragmentManager != null) {
            LoginFragment loginFragment = (LoginFragment) fragmentManager.findFragmentByTag(FRAGMENT_LOGIN);
            if (loginFragment == null) {
                loginFragment = new LoginFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, loginFragment, FRAGMENT_LOGIN)
                        .addToBackStack(null)
                        .commit();
            }
        }
    }

    @Override
    public void displayLogin(String login) {
        if (login.isEmpty()) login = getString(R.string.no_login);
        toolbar.setSubtitle(login);
    }

    @Override
    public void displayGames(List<Game> games) {
        if (map != null){
            map.clear();
            for (Game game: games){
                displayGameOnMap(game, map);
            }
            focusCameraOnMoscow();
        }
    }

    private void displayGameOnMap(Game game, GoogleMap map) {
        MarkerOptions markerOptions = new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ball_marker))
                .snippet(game.getDescription())
                .title(game.getTitle())
                .position(new LatLng(game.getLatitude(), game.getLongitude()));
        Marker marker = map.addMarker(markerOptions);
        marker.setTag(game);
    }

    private void focusCameraOnMoscow() {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(55.751244, 37.618423),
                10.0f)
        );
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public static class CustomMapFragment extends SupportMapFragment{

        private GoogleMap map;

        public void saveMap(GoogleMap map){
            this.map = map;
        }

        GoogleMap getMap(){
            return map;
        }
    }

    class NewGame extends Game {
    }
}

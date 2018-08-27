package ru.github.pvtitov.myfootball.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import ru.github.pvtitov.myfootball.R;
import ru.github.pvtitov.myfootball.contracts.Game;
import ru.github.pvtitov.myfootball.contracts.mvp.game_publisher.GamePublisherPresenter;
import ru.github.pvtitov.myfootball.contracts.mvp.game_publisher.GamePublisherView;
import ru.github.pvtitov.myfootball.presenter.GamePublisherPresenterImpl;

public class PublishGameFragment extends Fragment implements GamePublisherView, TimePicker.TimeListener, CalendarView.OnDateChangeListener {

    private static final String FRAGMENT_PICK_TIME = "FRAGMENT_PICK_TIME";

    private Game game = new Game();
    private LatLng latLng = new LatLng(0, 0);

    private GamePublisherPresenter presenter;
    private EditText titleEditText;
    private EditText descriptionEditText;
    private CheckBox attendCheckBox;
    private EditText timeEditText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        presenter = GamePublisherPresenterImpl.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();

        presenter.attachView(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        presenter.detachView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_publish_game, container, false);
        titleEditText = view.findViewById(R.id.create_title);
        descriptionEditText = view.findViewById(R.id.create_description);

        CalendarView dateCalendarView = view.findViewById(R.id.create_calendar);
        dateCalendarView.setOnDateChangeListener(this);

        timeEditText = view.findViewById(R.id.create_time);
        timeEditText.setShowSoftInputOnFocus(false);
        timeEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                showTimePickerDialog();
            }
        });
        timeEditText.setOnClickListener(v -> showTimePickerDialog());

        attendCheckBox = view.findViewById(R.id.attend_game);

        Button createButton = view.findViewById(R.id.create_button);
        createButton.setOnClickListener(v -> {
            presenter.publishGame();
        });

        return view;
    }

    private void showTimePickerDialog() {
        TimePicker timePicker = new TimePicker();
        timePicker.addTimeListener(PublishGameFragment.this);
        if (getActivity() != null) {
            timePicker.show(getActivity().getFragmentManager(), FRAGMENT_PICK_TIME);
        }
    }


    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Game getGame() {

        if (titleEditText != null) game.setTitle(titleEditText.getText().toString());
        if (descriptionEditText != null) game.setDescription(descriptionEditText.getText().toString());

        if (attendCheckBox != null) {
            if (attendCheckBox.isChecked()) {
                game.addAttendee(presenter.getCurrentUser());
            }
        }

        game.setLongitude(latLng.longitude);
        game.setLatitude(latLng.latitude);

        return game;
    }

    @Override
    public void close() {
        if (getActivity() != null) getActivity().onBackPressed();
    }

    @Override
    public void onTimePicked(int hour, int minute) {
        game.setHour(hour);
        game.setMinute(minute);
        StringBuilder timeAsText = new StringBuilder()
                .append(hour > 9 ? "" : 0)
                .append(hour)
                .append(":")
                .append(minute > 9 ? "" : 0)
                .append(minute);
        timeEditText.setText(timeAsText);
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        game.setDay(dayOfMonth);
        game.setMonth(month);
        game.setYear(year);
    }
}

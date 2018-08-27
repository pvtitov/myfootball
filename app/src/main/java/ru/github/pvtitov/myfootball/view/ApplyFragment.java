package ru.github.pvtitov.myfootball.view;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import ru.github.pvtitov.myfootball.R;
import ru.github.pvtitov.myfootball.contracts.Game;
import ru.github.pvtitov.myfootball.contracts.mvp.apply_for_game.ApplyPresenter;
import ru.github.pvtitov.myfootball.contracts.mvp.apply_for_game.ApplyView;
import ru.github.pvtitov.myfootball.presenter.ApplyPresenterImpl;

public class ApplyFragment extends Fragment implements ApplyView{

    private ApplyPresenter presenter;
    private Game game;

    public void setGame(Game game){
        this.game = game;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        presenter = ApplyPresenterImpl.getInstance();
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
        final View view = inflater.inflate(R.layout.fragment_apply, container, false);

        TextView tv_title = view.findViewById(R.id.apply_title);
        TextView tv_description = view.findViewById(R.id.apply_description);
        if (game != null) {

            StringBuilder title = new StringBuilder()
                    .append(game.getTitle())
                    .append("\n")
                    .append(game.getDay() > 9 ? "" : 0)
                    .append(game.getDay())
                    .append(".")
                    .append(game.getMonth() + 1 > 9 ? "" : 0)
                    .append(game.getMonth() + 1)
                    .append(".")
                    .append(game.getYear())
                    .append(" в ")
                    .append(game.getHour() > 9 ? "" : 0)
                    .append(game.getHour())
                    .append(":")
                    .append(game.getMinute() > 9 ? "" : 0)
                    .append(game.getMinute());

            tv_title.setText(title);

            StringBuilder description = new StringBuilder()
                    .append(game.getDescription())
                    .append("\n\nУже записалось: ")
                    .append(game.getAttendees() != null ? game.getAttendees().size() : "");

            tv_description.setText(description);
        }

        Button button = view.findViewById(R.id.apply_button);
        CheckBox checkBox = view.findViewById(R.id.add_to_calendar);
        button.setOnClickListener(v -> {
            presenter.applyForGame(game, checkBox.isChecked());
        });

        return view;
    }

    @Override
    public void addToCalendar(Game game) {
        Intent intent = new Intent()
                .setAction(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, game.calculateTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, game.getTitle())
                .putExtra(CalendarContract.Events.DESCRIPTION, game.getDescription());
        if (getActivity() != null) getActivity().startActivity(intent);
    }


    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void close() {
        if (getActivity() != null) getActivity().onBackPressed();
    }
}

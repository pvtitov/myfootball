package ru.github.pvtitov.myfootball.view;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ru.github.pvtitov.myfootball.R;

public class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    interface TimeListener {
        void onTimePicked(int hour, int minute);
    }

    private List<TimeListener> timeListeners = new ArrayList<>();

    public void addTimeListener(TimeListener timeListener) {
        timeListeners.add(timeListener);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), R.style.TimePickerTheme, this,
                hour, minute, DateFormat.is24HourFormat(getActivity()));

    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        for (TimeListener listener: timeListeners) {
            listener.onTimePicked(hourOfDay, minute);
        }
    }
}

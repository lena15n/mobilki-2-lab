package com.lena.androidrest;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if (getTag().equals(getString(R.string.meeting_enter_start_date))) {
            ((CreateMeetingActivity) getActivity()).onStartDataSet(view, year, monthOfYear, dayOfMonth);
        }
        else if (getTag().equals(getString(R.string.meeting_enter_end_date))) {
            ((CreateMeetingActivity) getActivity()).onEndDataSet(view, year, monthOfYear, dayOfMonth);
        }

    }
}
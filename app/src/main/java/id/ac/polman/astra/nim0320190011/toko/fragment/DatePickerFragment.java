package id.ac.polman.astra.nim0320190011.toko.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {
    private static final String ARG_DATE = "date";

    private DatePicker mDatePicker;

    private Callbacks callbacks;

    public interface Callbacks{
        public void onDateSelected(Date date);
        public void onDateSelected(Date date, int key);
    }

    public static DatePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE,date);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public static DatePickerFragment newInstance(Date date, int key){
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE,date);
        args.putSerializable("KEY", key);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        DatePickerDialog.OnDateSetListener dateListener;

        dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int month, int dayOfMonth) {
                Date resultDate = new GregorianCalendar(year,month,dayOfMonth)
                        .getTime();
                callbacks = (Callbacks) getTargetFragment();
                callbacks.onDateSelected(resultDate);
                try{
                    int key = (int) getArguments().getSerializable("KEY");
                    callbacks.onDateSelected(resultDate, key);
                }catch (Exception e){

                }
            }
        };

        Date date = (Date) getArguments().getSerializable(ARG_DATE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int initialYear = calendar.get(Calendar.YEAR);
        int initialMonth = calendar.get(Calendar.MONTH);
        int initialDay = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(
                requireContext(),
                dateListener,
                initialYear,
                initialMonth,
                initialDay
        );
    }
}

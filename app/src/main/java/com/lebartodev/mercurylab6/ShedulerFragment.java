package com.lebartodev.mercurylab6;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.github.mrengineer13.snackbar.SnackBar;
import com.lebartodev.mercurylab6.util.SharedPrefer;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_sheduler)
public class ShedulerFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    public static final String TAG = "ShedulerFragment";
    @ViewById(R.id.date_value)
    TextView dateValue;
    @ViewById(R.id.time_value)
    TextView timeValue;
    @ViewById(R.id.task_name)
    EditText titleValue;
    @ViewById(R.id.date_ayout)
    RelativeLayout dateLayout;
    @ViewById(R.id.time_layout)
    RelativeLayout timeLayout;
    @ViewById(R.id.description_text)
    EditText descriptionValue;


    private TaskModel model;


    public ShedulerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        if (model == null) {
            initModel();
        }
        onDateChange(model.getTaskDate().get(Calendar.YEAR),
                model.getTaskDate().get(Calendar.MONTH),
                model.getTaskDate().get(Calendar.DAY_OF_MONTH));
        onTimeChange(model.getTaskDate().get(Calendar.HOUR_OF_DAY), model.getTaskDate().get(Calendar.MINUTE));
        titleValue.setText(model.getTitle());
        descriptionValue.setText(model.getDescription());


    }

    @Click
    void date_ayout() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.MyDialogTheme, this, model.getTaskDate().get(Calendar.YEAR),
                model.getTaskDate().get(Calendar.MONTH),
                model.getTaskDate().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    @Click
    void time_layout() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), R.style.MyDialogTheme, this,
                model.getTaskDate().get(Calendar.HOUR_OF_DAY), model.getTaskDate().get(Calendar.MINUTE), true);
        timePickerDialog.show();

    }

    private void initModel() {
        model = SharedPrefer.getTaskModel();
        if (model == null) {
            model = new TaskModel("", Calendar.getInstance(), "");
            SharedPrefer.setTaskModel(model);

        }
    }

    public void onDateChange(int year, int month, int day) {
        dateValue.setText(readableCalendar(day) + "." + readableCalendar(month + 1) + "." + year);

    }

    public void onTimeChange(int hour, int min) {
        timeValue.setText(readableCalendar(hour) + ":" + readableCalendar(min));

    }


    private String readableCalendar(int i) {
        if (i < 10) {
            return "0" + String.valueOf(i);

        } else
            return String.valueOf(i);

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar upatedTaskDate = model.getTaskDate();
        upatedTaskDate.set(i, i1+1, i2);
        model.setTaskDate(upatedTaskDate);
        onDateChange(i, i1, i2);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        Calendar upatedTaskDate = model.getTaskDate();
        upatedTaskDate.set(Calendar.HOUR_OF_DAY, i);
        upatedTaskDate.set(Calendar.MINUTE, i1);
        upatedTaskDate.set(Calendar.SECOND,0);
        model.setTaskDate(upatedTaskDate);
        onTimeChange(i, i1);
    }

    @Click
    void save_button() {
        if(TextUtils.isEmpty(titleValue.getText())){
            new SnackBar.Builder(getActivity()).withMessage("Title is empty").show();
            return;
        }
        if(System.currentTimeMillis()>model.getTaskDate().getTimeInMillis()){
            new SnackBar.Builder(getActivity()).withMessage("Set correct time and date, please").show();
            return;
        }
        model.setTitle(titleValue.getText().toString());
        model.setDescription(descriptionValue.getText().toString());
        new SnackBar.Builder(getActivity()).withMessage("Saved!").show();
        SharedPrefer.setTaskModel(model);


        Intent intent = new Intent(getActivity(), TimeNotification.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT );
        AlarmManager am =(AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        am.cancel(pendingIntent);
        am.set(AlarmManager.RTC_WAKEUP,model.getTaskDate().getTimeInMillis(), pendingIntent);






    }
}

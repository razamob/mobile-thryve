package com.myappcompany.thea.mobileappthryve;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class AddEmploymentAppointmentFragment extends Fragment {

    private EditText editTextEmpTitle;
    private EditText editTextEmpDescription;
    private EditText editTextEmpDate;
    private DatePickerDialog datePickerDialogEmpDate;
    private Spinner spinnerEmpTime;
    private EditText editTextEmpStudentNotes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emp_add, container, false);

        editTextEmpTitle = (EditText) view.findViewById(R.id.edit_text_emp_title);
        editTextEmpDescription = (EditText) view.findViewById(R.id.edit_text_emp_description);

        editTextEmpDate = (EditText) view.findViewById(R.id.edit_text_emp_date);
        editTextEmpDate.setInputType(InputType.TYPE_NULL);
        editTextEmpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                datePickerDialogEmpDate = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int myYear, int myMonth, int myDay) {
                                editTextEmpDate.setText((myMonth + 1) + "/" + myDay + "/" + myYear);
                            }
                        }, year, month, day);
                datePickerDialogEmpDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialogEmpDate.show();
            }
        });

        spinnerEmpTime = (Spinner) view.findViewById(R.id.spinner_emp_time);
        editTextEmpStudentNotes = (EditText) view.findViewById(R.id.edit_text_emp_studentnotes);

        //((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        //((AppCompatActivity) getActivity()).setTitle("New Appointment");

        //setHasOptionsMenu(true);

        return view;
    }

    private void bookEmpAppt() {

    }

    /*@Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.emp_add_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }*/

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_emp_appt:
                bookEmpAppt();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

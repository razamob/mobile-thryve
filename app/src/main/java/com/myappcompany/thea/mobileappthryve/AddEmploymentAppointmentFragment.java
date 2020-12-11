package com.myappcompany.thea.mobileappthryve;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Time;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.time.ZonedDateTime.now;

public class AddEmploymentAppointmentFragment extends Fragment {

    //public static final String ARG_STUDENT = "argStudentAccount";

    private AddEmpFragmentListener empFragmentListener;
    private StudentAccount currentUser;
    private EmploymentConsultantForm newEmpForm;
    private Appointment newAppt;

    private EditText editTextEmpTitle;
    private EditText editTextEmpDescription;
    private EditText editTextEmpDate;
    private DatePickerDialog datePickerDialogEmpDate;
    private Spinner spinnerEmpTime;
    private EditText editTextEmpStudentNotes;

    CheckBox checkQsso;
    CheckBox checkQfriend;
    CheckBox checkQfaculty;
    CheckBox checkQvisit;
    CheckBox checkQorient;
    CheckBox checkQevent;
    CheckBox checkQkpi2;
    CheckBox checkQoutreach;
    CheckBox checkQposters;
    CheckBox checkQstv;
    CheckBox checkQsocial;
    CheckBox checkQmedia;
    CheckBox checkQwalkby;
    CheckBox checkQwebsite;
    CheckBox checkEresume;
    CheckBox checkEcover;
    CheckBox checkEinterview;
    CheckBox checkEjobsearch;
    CheckBox checkEmockinterview;
    CheckBox checkEnetworking;
    CheckBox checkEportfolio;

    private int selectedYear;
    private int selectedMonth;
    private int selectedDay;

    List<TimeText> timeTextList = new ArrayList<>();
    List<String> timeDayList = new ArrayList<>();

    ZonedDateTime startDateTime;
    ZonedDateTime endDateTime;
    ZonedDateTime submittedEndTime;

    public interface AddEmpFragmentListener {
        void onNewEmpApptSent(Appointment newAppt, EmploymentConsultantForm newEmp);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emp_add, container, false);

        //empFragmentListener.onReceiveCurrentAccount();

        editTextEmpTitle = (EditText) view.findViewById(R.id.edit_text_emp_title);
        editTextEmpDescription = (EditText) view.findViewById(R.id.edit_text_emp_description);

        spinnerEmpTime = (Spinner) view.findViewById(R.id.spinner_emp_time);
        FloatingActionButton buttonBookEmpAppt = view.findViewById(R.id.button_book_emp_appt);
        buttonBookEmpAppt.setEnabled(false);

        editTextEmpStudentNotes = (EditText) view.findViewById(R.id.edit_text_emp_studentnotes);

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
                                selectedYear = myYear;
                                selectedMonth = myMonth + 1;
                                selectedDay = myDay;

                                editTextEmpDate.setText((myMonth + 1) + "/" + myDay + "/" + myYear);

                                timeTextList = initializeTimeTextList(timeTextList);

                                getEmpTimeListFromDay(myYear, myMonth+1, myDay);
                            }
                        }, year, month, day);
                datePickerDialogEmpDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialogEmpDate.show();
            }
        });

        spinnerEmpTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TimeText selectedTime = (TimeText) parent.getSelectedItem();
                String newTimeData = selectedTime.getTimeData();
                int newHour = Integer.parseInt(newTimeData.substring(0,2));
                startDateTime = ZonedDateTime.of(selectedYear, selectedMonth, selectedDay, newHour, 0, 0, 0, ZoneId.of("America/Toronto"));
                endDateTime = ZonedDateTime.of(selectedYear, selectedMonth, selectedDay, newHour, 30, 0, 0, ZoneId.of("America/Toronto"));
                submittedEndTime = now();
                System.out.println("year: " + selectedYear + ", month: " + selectedMonth + ", day: " + selectedDay);
                buttonBookEmpAppt.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        checkQsso = view.findViewById(R.id.checkbox_q1e_sso);
        checkQfriend = view.findViewById(R.id.checkbox_q1e_friend);
        checkQfaculty = view.findViewById(R.id.checkbox_q1e_faculty);
        checkQvisit = view.findViewById(R.id.checkbox_q1e_visit);
        checkQorient = view.findViewById(R.id.checkbox_q1e_orient);
        checkQevent = view.findViewById(R.id.checkbox_q1e_event);
        checkQkpi2 = view.findViewById(R.id.checkbox_q1e_kpi2);
        checkQoutreach = view.findViewById(R.id.checkbox_q1e_outreach);
        checkQposters = view.findViewById(R.id.checkbox_q1e_posters);
        checkQstv = view.findViewById(R.id.checkbox_q1e_stv);
        checkQsocial = view.findViewById(R.id.checkbox_q1e_social);
        checkQmedia = view.findViewById(R.id.checkbox_q1e_media);
        checkQwalkby = view.findViewById(R.id.checkbox_q1e_walkby);
        checkQwebsite = view.findViewById(R.id.checkbox_q1e_website);
        checkEresume = view.findViewById(R.id.checkbox_ecs_resume);
        checkEcover = view.findViewById(R.id.checkbox_ecs_cover);
        checkEinterview = view.findViewById(R.id.checkbox_ecs_interview);
        checkEjobsearch = view.findViewById(R.id.checkbox_ecs_jobsearch);
        checkEmockinterview = view.findViewById(R.id.checkbox_ecs_mockinterview);
        checkEnetworking = view.findViewById(R.id.checkbox_ecs_networking);
        checkEportfolio = view.findViewById(R.id.checkbox_ecs_portfolio);

        buttonBookEmpAppt.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssx");
                String formatStartTime = startDateTime.format(formatter);
                String formatEndTime = endDateTime.format(formatter);
                String formatSubmitTime = submittedEndTime.format(formatter);
                String newTitle = editTextEmpTitle.getText().toString();
                String newDescription = editTextEmpDescription.getText().toString();
                String newStudentNotes = editTextEmpStudentNotes.getText().toString();
                Appointment newAppointment = new Appointment(newTitle, formatStartTime, formatEndTime, formatSubmitTime, formatStartTime, newDescription, newStudentNotes, "x", "x", "x", "x", "P", false);
                //newAppt = newAppointment;
                System.out.println("HELLOOOOOOO");
                insertNewEmpForm(newAppointment);

                //System.out.println("newEmpForm2 id: " + newEmpForm.getId());

            }
        });

        return view;
    }

    public void saveCurrentUser(StudentAccount a) {
        currentUser = a;
    }

    private List<TimeText> initializeTimeTextList(List<TimeText> timeList) {
        timeList.clear();
        TimeText time1 = new TimeText("08:00 AM", "08:00");
        timeList.add(time1);
        TimeText time2 = new TimeText("09:00 AM", "09:00");
        timeList.add(time2);
        TimeText time3 = new TimeText("10:00 AM", "10:00");
        timeList.add(time3);
        TimeText time4 = new TimeText("11:00 AM", "11:00");
        timeList.add(time4);
        TimeText time5 = new TimeText("01:00 PM", "13:00");
        timeList.add(time5);
        TimeText time6 = new TimeText("02:00 PM", "14:00");
        timeList.add(time6);
        TimeText time7 = new TimeText("03:00 PM", "15:00");
        timeList.add(time7);
        TimeText time8 = new TimeText("04:00 PM", "16:00");
        timeList.add(time8);

        return timeList;
    }

    private void getEmpTimeListFromDay(int y, int m, int d) {
        System.out.println("myDEBUG: " + "year: " + y + ", month: " + m + ", day: " + d);
        JsonPlaceHolderApi jsonPlaceHolderApi = RetrofitInstance.getApiService();

        Call<AppointmentContainer> call = jsonPlaceHolderApi.listDayAppointments(7, y, m, d);
        call.enqueue(new Callback<AppointmentContainer>() {
            @Override
            public void onResponse(Call<AppointmentContainer> call, Response<AppointmentContainer> response) {
                if(!response.isSuccessful()) {
                    System.out.println("TimeList error!!");
                    return;
                }

                System.out.println("NO ERROR!!");
                AppointmentContainer container = response.body();
                List<Appointment> appointments = container.getMyAppointments();
                System.out.println("appointments.size() = " + appointments.size());

                List<String> timeStringList = new ArrayList<>();

                for(Appointment appointment: appointments) {
                    String thisTime = (appointment.getStartDate()).substring(11, 16);
                    timeStringList.add(thisTime);
                }

                assignTimeDay(timeStringList);
            }

            @Override
            public void onFailure(Call<AppointmentContainer> call, Throwable t) {

            }
        });
    }

    public void assignTimeDay(List<String> myStringList) {
        timeDayList = myStringList;
        System.out.println("myDEBUG: timeTextList.size() = " + timeTextList.size());

        for(String myTimes: timeDayList) {
            for(int i = 0; i < timeTextList.size(); i++) {
                System.out.println("Comparing: " + myTimes + ": " + timeTextList.get(i).getTimeData());
                if(myTimes.equals(timeTextList.get(i).getTimeData())) {
                    timeTextList.remove(i);
                    break;
                }
            }
        }

        System.out.println("myDEBUG: timeDayList.size() = " + timeDayList.size());
        System.out.println("myDEBUG: timeTextList.size() = " + timeTextList.size());

        ArrayAdapter<TimeText> adapter = new ArrayAdapter<TimeText>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, timeTextList);
        spinnerEmpTime.setAdapter(adapter);
    }

    public void insertNewEmpForm(Appointment myNewAppt) {
        EmploymentConsultantForm insertForm = new EmploymentConsultantForm(checkQsso.isChecked(),
                checkQfriend.isChecked(),
                checkQfaculty.isChecked(),
                checkQvisit.isChecked(),
                checkQorient.isChecked(),
                checkQevent.isChecked(),
                checkQkpi2.isChecked(),
                checkQoutreach.isChecked(),
                checkQposters.isChecked(),
                checkQstv.isChecked(),
                checkQsocial.isChecked(),
                checkQmedia.isChecked(),
                checkQwalkby.isChecked(),
                checkQwebsite.isChecked(),
                checkEresume.isChecked(),
                checkEcover.isChecked(),
                checkEinterview.isChecked(),
                checkEjobsearch.isChecked(),
                checkEmockinterview.isChecked(),
                checkEnetworking.isChecked(),
                checkEportfolio.isChecked());

        JsonPlaceHolderApi jsonPlaceHolderApi = RetrofitInstance.getApiService();
        System.out.println("I am here!!!");
        Call<EmploymentConsultantFormContainer> call = jsonPlaceHolderApi.createEmploymentConsultantForm(insertForm);

        call.enqueue(new Callback<EmploymentConsultantFormContainer>() {
            @Override
            public void onResponse(Call<EmploymentConsultantFormContainer> call, Response<EmploymentConsultantFormContainer> response) {
                if(!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("Message: " + response.message());
                    System.out.println("Error Body: " + response.errorBody());
                    System.out.println("Headers: " + response.headers());
                    System.out.println("Raw: " + response.raw());
                }

                System.out.println("PLEASE WORK! PLEASE!");
                EmploymentConsultantFormContainer container = response.body();
                List<EmploymentConsultantForm> employmentConsultantForms = container.getMyEmploymentConsultantForms();

                /*for(EmploymentConsultantForm employmentConsultantForm: employmentConsultantForms) {
                    System.out.println("myTest");
                    newEmpForm = employmentConsultantForm;
                }*/

                newEmpForm = employmentConsultantForms.get(0);
                System.out.println("I am here. :)))");
                System.out.println("myNewAppt: " + myNewAppt.getId() + " : " + myNewAppt.getStartDate());
                System.out.println("newEmpForm: " + newEmpForm.getId());
                empFragmentListener.onNewEmpApptSent(myNewAppt, newEmpForm);
            }

            @Override
            public void onFailure(Call<EmploymentConsultantFormContainer> call, Throwable t) {

            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AddEmpFragmentListener) {
            empFragmentListener = (AddEmpFragmentListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + " must implement AddEmpFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        empFragmentListener = null;
    }
}

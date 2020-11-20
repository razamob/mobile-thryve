package com.myappcompany.thea.mobileappthryve;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AppointmentFragment extends Fragment {

    private TextView aptResult;
    private Retrofit retrofit;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    private AppointmentViewModel appointmentViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //remove - return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);

        /*retrofit = new Retrofit.Builder()
                .baseUrl("http://web-app-thrv.us-east-2.elasticbeanstalk.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        aptResult = (TextView) view.findViewById(R.id.appointment_result);
        //aptResult.setText("TESTING!!");

        getAppointments();*/

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_appt);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        //final AppointmentAdapter adapter = new AppointmentAdapter();
        //recyclerView.setAdapter(adapter);
        AppointmentAdapter adapter = new AppointmentAdapter();
        recyclerView.setAdapter(adapter);


        appointmentViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(AppointmentViewModel.class);

        appointmentViewModel.getAllAppointments().observe(this, new Observer<List<Appointment>>() {
            @Override
            public void onChanged(List<Appointment> appointments) {
                adapter.setAppointments(appointments);
                //adapter.setAppointments(appointments);
                //Toast.makeText(getActivity(),"Test Toast!", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    /*private void getAppointments() {
        Call<AppointmentContainer> call = jsonPlaceHolderApi.getAppointments("appointments/get-appointment");

        call.enqueue(new Callback<AppointmentContainer>() {
            @Override
            public void onResponse(Call<AppointmentContainer> call, Response<AppointmentContainer> response) {
                if (!response.isSuccessful()) {
                    aptResult.setText("Code: " + response.code());
                    return;
                }

                AppointmentContainer container = response.body();

                List<Appointment> appointments = container.getMyAppointments();

                for(Appointment appointment: appointments) {
                    String content = "";
                    content += "id: " + appointment.getId() + "\n";
                    content += "title: " + appointment.getTitle() + "\n";
                    content += "start_date: " + appointment.getStartDate() + "\n";
                    content += "end_date: " + appointment.getEndDate() + "\n";
                    content += "description: " + appointment.getDescription() + "\n\n";

                    aptResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<AppointmentContainer> call, Throwable t) {
                aptResult.setText("Failure: " + t.getMessage());
            }
        });
    }*/
}
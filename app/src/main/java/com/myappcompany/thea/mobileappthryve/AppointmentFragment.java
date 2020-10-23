package com.myappcompany.thea.mobileappthryve;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AppointmentFragment extends Fragment {

    private TextView aptResult;

    private Retrofit retrofit;

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //remove - return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://web-app-thrv.us-east-2.elasticbeanstalk.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        getAppointments();

        return view;
    }

    private void getAppointments() {
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
    }
}
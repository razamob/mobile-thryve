package com.myappcompany.thea.mobileappthryve;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class AppointmentViewModel extends AndroidViewModel {
    private AppointmentRepository appointmentRepository;

    public AppointmentViewModel(@NonNull Application application) {
        super(application);

        appointmentRepository = new AppointmentRepository(application);
    }

    public LiveData<List<Appointment>> getAllAppointments() {
        return appointmentRepository.getMutableLiveData();
    }
}

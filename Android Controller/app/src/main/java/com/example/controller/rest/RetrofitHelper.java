package com.example.controller.rest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;

public class RetrofitHelper {
    private Retrofit retrofit;
    private ControlService service;

    public RetrofitHelper() {
        changeAddress(251);
    }

    //10.0.2.2는 에뮬레이터를 가동하는 호스트 컴퓨터 IP
    public void changeAddress(int address) {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1." + address + ":5000/")
                //.baseUrl("http://10.0.2.2:8080")
                .build();

        service = retrofit.create(ControlService.class);
    }

    public void joystickControl(ControlData data) throws IOException {
        service.joystickControl(data.getSpeed(), data.getSteering(), data.getFocus()).execute();
    }

    public Call<Void> getCodeBlockCall(String query){
        return service.codeBlockSend(query);
    }
}

package com.example.controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.controller.rest.ControlData;
import com.example.controller.rest.ControlService;
import com.example.controller.rest.RetrofitHelper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.github.controlwear.virtual.joystick.android.JoystickView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoyStick extends Fragment {
    private JoystickView joystick;
    private Button initBtn;
    private RetrofitHelper helper;
    private Disposable disposable;

    public JoyStick(RetrofitHelper helper) {
        this.helper = helper;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup)inflater
                .inflate(R.layout.fragment_joy_stick,container,false);

        init(viewGroup);

        return viewGroup;
    }

    private void init(ViewGroup viewGroup){
        joystick = viewGroup.findViewById(R.id.joyStick);
        initBtn = viewGroup.findViewById(R.id.initBtn);
    }

    @Override
    public void onResume() {
        super.onResume();

        disposable = getMoveEventObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .sample(5L, TimeUnit.MILLISECONDS)
                .subscribe(controlData -> helper.joystickControl(controlData),
                        throwable -> System.out.println(throwable.getLocalizedMessage()));


        initBtn.setOnClickListener(view -> {
            Call<Void> initCall = helper.getCodeBlockCall("I");

            initCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        });
    }

    private Observable<ControlData> getMoveEventObservable(){
        return Observable.create(emitter ->
            joystick.setOnMoveListener(((angle, strength) -> {
                emitter.onNext(new ControlData(strength, angle));
            }))
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
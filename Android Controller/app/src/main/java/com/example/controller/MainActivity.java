package com.example.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.controller.rest.RetrofitHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private JoyStick joyStick;
    private CodeBlock codeBlock;
    private BottomNavigationView menu;
    private RetrofitHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        isContainerReplaceSuccess(joyStick);

        menu.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.joyStick: return isContainerReplaceSuccess(joyStick);
                case R.id.codeBlock: return isContainerReplaceSuccess(codeBlock);
            }
            return false;
        });
    }

    private void init(){
        helper = new RetrofitHelper();

        joyStick = new JoyStick(helper);
        codeBlock = new CodeBlock(helper);

        menu = findViewById(R.id.menu);
    }

    private boolean isContainerReplaceSuccess(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.controlContainer, fragment)
                .commit();

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);

        return true;
    }

    private int checkedItemIdx = 0;
    private int address = 0;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.selector: {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                String[] items = getResources().getStringArray(R.array.carList);
                ArrayList<String> selectedItem = new ArrayList<>();

                selectedItem.add(items[0]);

                builder.setTitle("차량선택");
                builder.setSingleChoiceItems(R.array.carList, checkedItemIdx, ((dialogInterface, i) -> {
                    selectedItem.clear();

                    selectedItem.add(items[i]);
                    checkedItemIdx = i;
                }) );


                builder.setPositiveButton("OK",((dialogInterface, i) -> {
                    switch (selectedItem.get(0)){
                        case "1호차" : address = 22; break;
                        case "2호차" : address = 18; break;
                        case "3호차" : address = 20; break;
                    }

                    helper.changeAddress(address);
                }));

                AlertDialog dialog = builder.create();
                dialog.show();
            }

            default: return super.onOptionsItemSelected(item);
        }

    }
}
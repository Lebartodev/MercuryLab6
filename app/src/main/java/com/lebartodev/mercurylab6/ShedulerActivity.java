package com.lebartodev.mercurylab6;

import android.app.Fragment;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.a_sheduler)
public class ShedulerActivity extends AppCompatActivity {
    FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm=getFragmentManager();
        Fragment shedulerFragment = fm.findFragmentByTag(ShedulerFragment.TAG);
        if(shedulerFragment==null){
            fm.beginTransaction().replace(R.id.content,ShedulerFragment_.builder()
                    .build(),ShedulerFragment.TAG).commit();

        }

    }
}

package com.example.ahmed.bodybuillding;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by ahmed on 23/12/2016.
 */
public class StopWatch extends android.support.v4.app.Fragment {
    int seconds = 0;
    boolean running = false;
    boolean wasrunning = false;
    Button start,stop,reset;
    TextView watch;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.stopwatvh,container,false);
        watch= (TextView) view.findViewById(R.id.timertextView);
        start= (Button) view.findViewById(R.id.startbutton);
        stop= (Button) view.findViewById(R.id.stopbutton);
        reset= (Button) view.findViewById(R.id.resetbutton);


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        runTimer();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = true;

            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = false;
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seconds = 0;
            }
        });
    }

    private void runTimer() {

        final Handler h = new Handler();
        h.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format("%d:%02d:%02d", hours, minutes, secs);
                watch.setText(time);
                if(running)
                    seconds++;
                h.postDelayed(this,1000);
            }
        });
    }
}

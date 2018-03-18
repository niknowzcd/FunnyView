package com.mysample.application.md;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.widget.SeekBar;

import com.mysample.application.R;

/**
 * Created by dly on 2018/1/9.
 */

public class MainActivity extends Activity {

    CardView mCardView;

    SeekBar mRadiusSeekBar;

    SeekBar mElevationSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardview);

        mCardView = (CardView) findViewById(R.id.cardview);
        mRadiusSeekBar = (SeekBar) findViewById(R.id.cardview_radius_seekbar);
        mRadiusSeekBar
                .setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress, boolean fromUser) {
                        mCardView.setRadius(progress);

                    }
                });

        mElevationSeekBar = (SeekBar) findViewById(R.id.cardview_elevation_seekbar);
        mElevationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onProgressChanged(SeekBar seekBar,
                                          int progress, boolean fromUser) {
                mCardView.setElevation(progress);

            }
        });

    }
}

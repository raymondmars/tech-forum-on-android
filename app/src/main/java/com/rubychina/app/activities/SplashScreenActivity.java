package com.rubychina.app.activities;


import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.MotionEvent;




public class SplashScreenActivity extends Activity {

    protected boolean _active = true;
    protected int _splashTime = 10 * 1000;
    protected Intent _main_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        _main_activity = new Intent(getApplicationContext(),NavActivity.class);

        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while(_active && (waited < _splashTime)) {
                        sleep(100);
                        if(_active) {
                            waited += 100;
                        }
                        if(waited > 2000) {
                            _active = false;
                        }
                    }
                } catch(InterruptedException e) {
                    // do nothing
                } finally {
                    finish();
                    // 启动主应用
                    startActivity(_main_activity);

                }
            }
        };
        splashTread.start();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            _active = false;
        }
        return true;
    }

}

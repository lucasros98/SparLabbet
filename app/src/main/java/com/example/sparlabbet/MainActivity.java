package com.example.sparlabbet;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.inputmethod.InputMethodManager;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public Button showSparmal,showCalc;
    private FragmentManager manager;
    private FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getFragmentManager();
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(bmp);
        bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        LinearLayout layout = (LinearLayout)findViewById(R.id.layota);
        layout.setBackgroundDrawable(bitmapDrawable);
        init();
    }


    @Override
    public void onClick(View view) {
        final FragmentSparmal fSpar = new FragmentSparmal();
        final FragmentKalkylator fCalc = new FragmentKalkylator();


        switch (view.getId()) {
            case R.id.showCalc:
                transaction = manager.beginTransaction();
                transaction.replace(R.id.layout, fCalc);
                transaction.addToBackStack(null);
                transaction.commit();
                manager.executePendingTransactions();

                break;
            case R.id.showSparmal:
                transaction = manager.beginTransaction();
                transaction.replace(R.id.layout, fSpar);
                transaction.addToBackStack(null);
                transaction.commit();
                manager.executePendingTransactions();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (manager.getBackStackEntryCount() > 0) {
            manager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }


    private void init() {
        showCalc = (Button)findViewById(R.id.showCalc);
        showCalc.setOnClickListener((View.OnClickListener) this);

        showSparmal = (Button)findViewById(R.id.showSparmal);
        showSparmal.setOnClickListener((View.OnClickListener) this);

    }

}

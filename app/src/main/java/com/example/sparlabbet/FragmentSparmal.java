package com.example.sparlabbet;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FragmentSparmal extends Fragment implements View.OnClickListener {

    private Button btncalculate;
    private EditText etkapital, etspartid, etutveckling, ettoday;
    private MainActivity main;
    private double startAmount,kapital,procentage,nrOfYears;
    private Integer spartid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MainActivity main = (MainActivity)getActivity();
        View RootView = inflater.inflate(R.layout.fragment_sparmal, container, false);

        btncalculate = (Button) RootView.findViewById(R.id.buttoncalc);
        btncalculate.setOnClickListener((View.OnClickListener) this);

        etspartid = (EditText) RootView.findViewById(R.id.etspartid);
        ettoday = (EditText) RootView.findViewById(R.id.ettoday);
        etkapital = (EditText) RootView.findViewById(R.id.etkapital);
        etutveckling = (EditText) RootView.findViewById(R.id.etutveckling);

        return RootView;
    }
    public void onClick(View view) {
        String num1 = etspartid.getText().toString();
        String num2 = ettoday.getText().toString();
        String num3 = etkapital.getText().toString();
        String num4 = etutveckling.getText().toString();

        switch (view.getId()) {
            case R.id.buttoncalc:
                try {

                    if (num1.isEmpty()) {
                        etspartid.setText("0");
                        num1 = "0";
                    }

                    if (num2.isEmpty()) {
                        ettoday.setText("0");
                        num2 = "0";
                    }

                    if (num3.isEmpty()) {
                        etkapital.setText("0");
                        num3 = "0";
                    }
                    if (num4.isEmpty()) {
                        etutveckling.setText("0");
                        num4 = "0";
                    }
                } catch (Exception e) {

                }

                kapital = Double.parseDouble(num3); //Önskad kapitalmängd
                startAmount = Double.parseDouble(num2); //Startkapital
                nrOfYears = Double.parseDouble(num1); //spartid
                procentage= Double.parseDouble(num4); //procentutveckling
                spartid = Integer.parseInt(num1);

                try {

                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                    View mView = getLayoutInflater().inflate(R.layout.fragment_slutbelopp, null);
                    TextView belopp = (TextView) mView.findViewById(R.id.belopp);
                    TextView text = (TextView) mView.findViewById(R.id.textView);
                    text.setText("Månadssparande som krävs:");

                    belopp.setText(String.format("%,.0f", calculateMonthly(startAmount,kapital,nrOfYears,procentage)) + " kr");

                    Button showGraf = (Button) mView.findViewById(R.id.btnShowGraf);
                    showGraf.setText("Stäng");
                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();
                    showGraf.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();

                        }
                    });

                } catch (Exception e) {
                }
        }
    }

    private double calculateMonthly(double startAmount, double finalAmount, double nrOfYears,double annualGrowth) {
        if (annualGrowth == 0) {
            return startAmount;

        } else {
            double percentage = annualGrowth/100;

            double sum1 = startAmount * Math.pow((1 + percentage), nrOfYears);
            double sum2 =  12 * ((percentage) + 1) * ((Math.pow((1 + (percentage)), nrOfYears) - 1) / (percentage));
            double monthly = (finalAmount/sum2) - (sum1/sum2);
            if (monthly>0)
                return (finalAmount/sum2) - (sum1/sum2);
            else
                return 0.0;
        }
    }
}

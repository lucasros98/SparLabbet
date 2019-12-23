package com.example.sparlabbet;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.app.AlertDialog;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class FragmentKalkylator extends Fragment implements View.OnClickListener {

    private Button btncalculate;
    private TextView tvresult;
    private EditText etstartbelopp, etspartid, etutveckling, etmonth;
    private GraphView graph;
    private MainActivity main;
    private double startAmount,monthlySavings,procentage,nrOfYears;
    private Integer spartid;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainActivity main = (MainActivity)getActivity();
        View RootView = inflater.inflate(R.layout.fragment_kalkylator, container, false);

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(bmp);
        bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        ScrollView layout = (ScrollView)RootView.findViewById(R.id.layoutscroll);
        layout.setBackgroundDrawable(bitmapDrawable);

        btncalculate = (Button) RootView.findViewById(R.id.buttoncalc);
        btncalculate.setOnClickListener((View.OnClickListener) this);
        etspartid = (EditText) RootView.findViewById(R.id.etspartid);
        etmonth = (EditText) RootView.findViewById(R.id.etmonth);
        etstartbelopp = (EditText) RootView.findViewById(R.id.etkapital);
        etutveckling = (EditText) RootView.findViewById(R.id.etutveckling);
        btncalculate.setOnClickListener((View.OnClickListener) this);

        return RootView;
    }

    @Override
    public void onClick(View view) {
        String num1 = etspartid.getText().toString();
        String num2 = etmonth.getText().toString();
        String num3 = etstartbelopp.getText().toString();
        String num4 = etutveckling.getText().toString();

        switch (view.getId()) {
            case R.id.buttoncalc:
                try {

                    if (num1.isEmpty()) {
                        etspartid.setText("0");
                        num1 = "0";
                    }

                    if (num2.isEmpty()) {
                        etmonth.setText("0");
                        num2 = "0";
                    }

                    if (num3.isEmpty()) {
                        etstartbelopp.setText("0");
                        num3 = "0";
                    }
                    if (num4.isEmpty()) {
                        etutveckling.setText("0");
                        num4 = "0";
                    }
                } catch (Exception e) {

                }
                spartid = Integer.parseInt(num1);


                startAmount = Double.parseDouble(num3); //num 3a startbelopp
                monthlySavings = Double.parseDouble(num2); //månadssparande
                nrOfYears = Double.parseDouble(num1); //spartid
                procentage= Double.parseDouble(num4); //procentutveckling

                try {

                    final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                    View mView = getLayoutInflater().inflate(R.layout.fragment_slutbelopp, null);
                    TextView belopp = (TextView) mView.findViewById(R.id.belopp);
                    belopp.setText(String.format("%,.0f", calculateFinalAmount(startAmount,monthlySavings,nrOfYears,procentage)) + " kr");

                    Button showGraf = (Button) mView.findViewById(R.id.btnShowGraf);
                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();
                    showGraf.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            View mView1 = getLayoutInflater().inflate(R.layout.fragment_graf, null);
                            GraphView graph = (GraphView) mView1.findViewById(R.id.graph);
                            Button close = (Button) mView1.findViewById(R.id.btnClose);

                            double y;
                            LineGraphSeries<DataPoint> series;
                            series = new LineGraphSeries<DataPoint>();
                            for (int i=0; i<=spartid; i++) {
                                y = calculateFinalAmount(startAmount,monthlySavings,i,procentage);
                                series.appendData(new DataPoint(i,y),true, 500);
                            }
                            graph.addSeries(series);
                            graph.getViewport().setXAxisBoundsManual(true);
                            graph.getViewport().setMaxX(spartid);

                            dialog.dismiss();

                            mBuilder.setView(mView1);
                            final AlertDialog dialog = mBuilder.create();
                            dialog.show();
                            close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });                        }
                    });

                } catch (Exception e) {
                }

        }
    }

    private double calculateFinalAmount(double startAmount, double monthly, double nrOfYears,double annualGrowth) {
        if (annualGrowth == 0) {


            double sum = (monthly * 12 * nrOfYears) + startAmount;
            return sum;


        } else {
            double percentage = annualGrowth/100;

            double sum1 = startAmount * Math.pow((1 + percentage), nrOfYears);
            double sum2 = monthly * 12 * ((percentage) + 1) * ((Math.pow((1 + (percentage)), nrOfYears) - 1) / (percentage));
            return sum1 + sum2;
        }
    }
}

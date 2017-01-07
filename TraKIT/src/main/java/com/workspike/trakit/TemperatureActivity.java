package com.workspike.trakit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.login.LoginManager;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.Date;

public class TemperatureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       GraphView graph = (GraphView) findViewById(R.id.graph0);
     graph.setTitle("Temperature");
       Calendar calendar = Calendar.getInstance();
       Date d1 = calendar.getTime();
       calendar.add(Calendar.DATE, 1);
       Date d2 = calendar.getTime();
       calendar.add(Calendar.DATE, 1);
       Date d3 = calendar.getTime();

        // first series is a line
        DataPoint[] points = new DataPoint[100];
        for (int i = 0; i < points.length; i++) {
            points[i] = new DataPoint(i, Math.sin(i*0.5) * 5*(Math.random()*10+1));
        }
        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>(points);

      // graphView.getGridLabelRenderer();
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-150);
        graph.getViewport().setMaxY(150);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(4);
        graph.getViewport().setMaxX(80);

        // enable scaling and scrolling
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);



        series3.setDrawDataPoints(true);
        series3.setAnimated(true);
        series3.setDataPointsRadius(10);
        series3.setThickness(8);
        series3.setDrawBackground(true);
        series3.setTitle("Temperature");
        series3.setColor(Color.argb(255, 255, 60, 60));
        series3.setBackgroundColor(Color.argb(100, 204, 119, 119));

        graph.addSeries(series3);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);



        DataPoint[] points2 = new DataPoint[100];
        for (int i = 0; i < points.length; i++) {
            points[i] = new DataPoint(i, Math.sin(i*0.5) * 5*(Math.random()*10+1));
        }
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(points);

        series2.setDrawDataPoints(true);
        series2.setAnimated(true);
        series2.setDataPointsRadius(10);
        series2.setThickness(8);
        series2.setDrawBackground(true);
        series2.setTitle("Speed");
        graph.addSeries(series2);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
// set date label formatter
     //  graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(TemperatureActivity.this));
     //  graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

// set manual x bounds to have nice steps
   //    graph.getViewport().setMinX(d1.getTime());
   //    graph.getViewport().setMaxX(d3.getTime());
   //    graph.getViewport().setXAxisBoundsManual(true);


    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.temperature_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.item_dateselect) {
            Intent intent = new Intent(TemperatureActivity.this,
                    SelectTimePeriodActivity.class);
            startActivity(intent);
        }else if (id == R.id.item_timeselect) {
            Intent intent = new Intent(TemperatureActivity.this,
                    SelectTimePeriodActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }







}

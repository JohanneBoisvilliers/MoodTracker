package com.boisvilliers.johanne.moodtracker.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.boisvilliers.johanne.moodtracker.R;
import com.boisvilliers.johanne.moodtracker.model.HistoryElements;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import static com.boisvilliers.johanne.moodtracker.controller.MainActivity.BUNDLE_MOOD_TO_SAVE;

public class PieChartActivity extends AppCompatActivity {

    private PieChart mPieChart;
    private ArrayList<HistoryElements> mMoodToPutInChart;
    private List<PieEntry> mEntries;
    private int[] mMoodToInt;
    private int mSadInt, mDisappointedInt, mNormalInt, mHappyInt, mVeryHappyInt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        mPieChart = findViewById(R.id.chart); // Connect the xml file to setting the pie chart
        mMoodToPutInChart = (ArrayList<HistoryElements>) getIntent().getSerializableExtra(BUNDLE_MOOD_TO_SAVE); // Get the moods of the last week thanks to intent

        mEntries = new ArrayList<>();

        sortedListMood();
        setPieChart();
        paramPieChart();
    }
    // Set the base of pie chart
    public void setPieChart() {
        String[] labelList = new String[]{"Sad","Disappointed","Normal","Happy","Super Happy"}; // Labels for legend
        ArrayList<Integer> colors = new ArrayList<>(); //colors for pies

        PieDataSet set = new PieDataSet(mEntries, ""); // list which form pie chart
        set.setSliceSpace(2.f); // space between pies

        PieData data = new PieData(set); // chart container
        mPieChart.setData(data);
        // fill the pie chart with the elements in list of moods
        for (int i = 0; i < mMoodToPutInChart.size(); i++) {
            if(!colors.contains(mMoodToPutInChart.get(i).getColor())) {
                mEntries.add(new PieEntry(mMoodToInt[mMoodToPutInChart.get(i).getIndex()], labelList[mMoodToPutInChart.get(i).getIndex()]));
                colors.add(mMoodToPutInChart.get(i).getColor());
                set.setColors(colors);
            }
        }
    }
    public void paramPieChart(){
        Legend legend = mPieChart.getLegend(); // get the legend to set them
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.PIECHART_CENTER); // put the legend in the center of pie chart
        mPieChart.getDescription().setText("Statistic of moods for the last week"); // Set the description in the bottom of the screen
        mPieChart.getDescription().setTextSize(13f); // size of text of description
        mPieChart.setDrawEntryLabels(false); // deactivate the entry labels on pies
        mPieChart.invalidate(); // refresh the view
    }
    // Method to know how many times there is this or that mood and save the result in an array
    public void sortedListMood(){
        for (int i = 0; i < mMoodToPutInChart.size(); i++) {
            switch (mMoodToPutInChart.get(i).getIndex())
            {
                case 0:
                    mSadInt +=1;
                break;
                case 1:
                    mDisappointedInt +=1;
                break;
                case 2:
                    mNormalInt +=1;
                break;
                case 3:
                    mHappyInt+=1;
                break;
                case 4:
                    mVeryHappyInt+=1;
                break;
            }
            mMoodToInt = new int[]{mSadInt,mDisappointedInt,mNormalInt,mHappyInt,mVeryHappyInt};
        }
    }
}

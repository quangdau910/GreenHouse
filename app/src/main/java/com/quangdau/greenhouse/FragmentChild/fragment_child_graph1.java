package com.quangdau.greenhouse.FragmentChild;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.quangdau.greenhouse.ApiService.ApiServer;
import com.quangdau.greenhouse.Other.NetworkConnection;
import com.quangdau.greenhouse.R;
import com.quangdau.greenhouse.SharedPreferences.UserPreferences;
import com.quangdau.greenhouse.Spinner.CategorySpinner;
import com.quangdau.greenhouse.Spinner.CategorySpinnerAdapter;
import com.quangdau.greenhouse.modelsAPI.get_graph.DataReal;
import com.quangdau.greenhouse.modelsAPI.get_graph.DataGraph;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragment_child_graph1 extends Fragment {
    RadioGroup radioGroup;
    LineChart graph;
    TextView txtYAxisTitle;
    ArrayList<Entry> dataValues;
    ArrayList<ILineDataSet> dataSets;
    DataGraph mDataGraph;
    LineDataSet lineDataSet1;

    List<DataReal> mData;
    UserPreferences userPreferences;
    String houseID;
    SimpleDateFormat formatTime = null;
    NetworkConnection networkConnection;
    //Spinner
    Spinner spinnerTypeChart;
    CategorySpinnerAdapter categoryTypeAdapter;
    //SpinKet
    Dialog dialog;
    //Variable arraylist get data api
    List<DataReal> getApi7D;
    List<DataReal> getApi1M;
    //Boolean data graph 7d 1m
    public Boolean flagData7D = false;
    public Boolean flagData1M =false;
    //Variable value first array
    public long valueFirstArray =0;
    //Check button radio set time graph
    public final String setTime1H = "1h";
    public final String setTime1D = "1d";
    public final String setTime7D = "7d";
    public final String setTime1M = "30d";
    public String setTime = setTime1H;
    //Check type graph
    public final String graphAir = "humidity";
    public final String  graphLand = "soil_moisture1";
    public final String  graphLand2 = "soil_moisture2";
    public final String  graphLand3 = "soil_moisture3";
    public final String  graphLand4 = "soil_moisture4";
    public final String  graphTemperature = "temperature";
    public String typeGraph;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = new Bundle();
        parseData(bundle);
        bundle = this.getArguments();
        parseData(bundle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child_graph1,container,false);

        userPreferences = new UserPreferences(getActivity());
        networkConnection = new NetworkConnection(getActivity());

        //Assign variables
        radioGroup = view.findViewById(R.id.time_check_graph);
        txtYAxisTitle = view.findViewById(R.id.text_tile_yAxis);
        graph = view.findViewById(R.id.graph);

        //txtYAxisTitle.setText(getResources().getString(R.string.yAxis_humidity));

        //Custom text no data
        Paint paint = graph.getPaint(graph.PAINT_INFO);
        paint.setTextSize(50);
        graph.setNoDataText(getResources().getString(R.string.no_data));
        graph.setNoDataTextColor(getResources().getColor(R.color.blue_30));
        dataValues = new ArrayList<>();
        //Spinner view
        spinnerTypeChart = view.findViewById(R.id.typeChart1);
        List<CategorySpinner> list = new ArrayList<>();
        list.add(new CategorySpinner(getResources().getString(R.string.graph_humidity)));
        list.add(new CategorySpinner(getResources().getString(R.string.graph_temperature)));
        list.add(new CategorySpinner(getResources().getString(R.string.soil_moisture_1)));
        list.add(new CategorySpinner(getResources().getString(R.string.soil_moisture_2)));
        list.add(new CategorySpinner(getResources().getString(R.string.soil_moisture_3)));
        list.add(new CategorySpinner(getResources().getString(R.string.soil_moisture_4)));
        categoryTypeAdapter = new CategorySpinnerAdapter(getActivity(),R.layout.item_selected_language,list);
        spinnerTypeChart.setAdapter(categoryTypeAdapter);
        spinnerTypeChart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                flagData1M = false;
                flagData7D = false;
                switch (position){
                    case 0:
                        typeGraph = graphAir;
                        txtYAxisTitle.setText(getResources().getString(R.string.yAxis_humidity));
                        getArrayDataGraph(userPreferences.getToken());
                        break;
                    case 1:
                        typeGraph = graphTemperature;
                        txtYAxisTitle.setText(getResources().getString(R.string.yAxis_temperature));
                        getArrayDataGraph(userPreferences.getToken());
                        break;
                    case 2:
                        typeGraph = graphLand;
                        txtYAxisTitle.setText(getResources().getString(R.string.yAxis_soil_moisture));
                        getArrayDataGraph(userPreferences.getToken());
                        break;
                    case 3:
                        typeGraph = graphLand2;
                        txtYAxisTitle.setText(getResources().getString(R.string.yAxis_soil_moisture));
                        getArrayDataGraph(userPreferences.getToken());
                        break;
                    case 4:
                        typeGraph = graphLand3;
                        txtYAxisTitle.setText(getResources().getString(R.string.yAxis_soil_moisture));
                        getArrayDataGraph(userPreferences.getToken());
                        break;
                    case 5:
                        typeGraph = graphLand4;
                        txtYAxisTitle.setText(getResources().getString(R.string.yAxis_soil_moisture));
                        getArrayDataGraph(userPreferences.getToken());
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Event click value chart
        graph.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                IMarker marker = new YourMarkerView(getContext(), R.layout.custom_marker_view_layout);
                graph.setMarker(marker);
            }
            @Override
            public void onNothingSelected() {
            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.graph_1H:
                        setTime = setTime1H;
                        getArrayDataGraph(userPreferences.getToken());
                        break;
                    case R.id.graph_1D:
                        setTime = setTime1D;
                        getArrayDataGraph(userPreferences.getToken());
                        break;
                    case R.id.graph_7D:
                        setTime= setTime7D;
                        if(flagData7D) {
                            mData = getApi7D;
                            setDataGraph();
                        }else{
                            getArrayDataGraph(userPreferences.getToken());
                        }
                        break;
                    case R.id.graph_1M:
                        setTime= setTime1M;
                        if(flagData1M){
                            mData = getApi1M;
                            setDataGraph();
                        }else{
                            getArrayDataGraph(userPreferences.getToken());
                        }
                        break;
                }
            }
        });
        return view;
    }

    public class LineChartXAxisValueFormatter implements IAxisValueFormatter {
        @SuppressLint("SimpleDateFormat")
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            long realTime =(long) value+ valueFirstArray;
            long timeXAxis = realTime*1000L;
            Date timeMilliseconds = new Date(timeXAxis);
            switch (setTime) {
                case setTime1H:
                    setFormatTime("HH:mm:ss");
                    break;
                case setTime1D:
                    setFormatTime("HH:mm");
                    break;
                case setTime7D:
                case setTime1M:
                    setFormatTime("dd/MM");
                    break;
            }
            return formatTime.format(timeMilliseconds);
        }
    }
    private void getArrayDataGraph(String token){
        if(networkConnection.isNetworkConnected()){
            dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.win_layout_spinkit);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            //Lock window layout
            dialog.setCancelable(false);
            ApiServer get = ApiServer.retrofit.create(ApiServer.class);
            Call<DataGraph> call = get.getGraphData(token,"GetGraphData",houseID,typeGraph,setTime);
            call.enqueue(new Callback<DataGraph>() {
                @Override
                public void onResponse(Call<DataGraph> call, Response<DataGraph> response) {
                    if (response.body() != null && response.body().getData().size() != 0){
                        mData = new ArrayList<>();
                        mDataGraph = response.body();
                        long timeData;
                        float valueData;
                        for (int i = 0; i < mDataGraph.getData().size(); i++){
                            String dateString = mDataGraph.getData().get(i).getTime();
                            valueData = mDataGraph.getData().get(i).getFirst();
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
                            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+0"));
                            Date date = null;
                            try {
                                date = dateFormat.parse(dateString);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            timeData = date.getTime()/1000L;
                            mData.add(new DataReal(timeData,valueData));

                        }
                        if(setTime.equals(setTime7D) && !flagData7D){
                            getApi7D = mData;
                            flagData7D =true;
                        }
                        if(setTime.equals(setTime1M) && !flagData1M){
                            getApi1M = mData;
                            flagData1M =true;
                        }
                        setDataGraph();
                        dialog.dismiss();
                    }
                    else{
                        graph.clear();
                        dialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<DataGraph> call, Throwable t) {
                    Log.e("gh", "Error: " + t);
                    dialog.dismiss();
                }
            });
        }else {
            graph.clear();
        }

    }
    private void setDataGraph() {
            //Set data graph
            dataValues.clear();
            valueFirstArray = mData.get(0).getTime();
            for (int i = 0; i < mData.size(); i++) {
                long xAxis = mData.get(i).getTime() - valueFirstArray;
                float yAxis = mData.get(i).getValue();
                dataValues.add(new Entry(xAxis, yAxis));
            }
            lineDataSet1 = new LineDataSet(dataValues,"");
            dataSets = new ArrayList<>();
            dataSets.add(lineDataSet1);
            LineData data = new LineData(dataSets);
            data.setValueFormatter(new ValueFormat());
            graph.setData(data);
            graph.invalidate();
            SettingUIGraph();

    }
    public class YourMarkerView extends MarkerView {
        TextView tvYValue;
        TextView tvXValue;
        public YourMarkerView(Context context, int layoutResource) {
            super(context, layoutResource);
            tvYValue = findViewById(R.id.YValue);
            tvXValue = findViewById(R.id.XValue);
        }
        //Content (user-interface)
        @SuppressLint("SetTextI18n")
        @Override
        public void refreshContent(Entry e, Highlight highlight) {
            long realTime =((long) e.getX())+valueFirstArray;
            long timeXAxis = realTime*1000L;
            Date time = new Date(timeXAxis);
            SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
            formatTime.setTimeZone(TimeZone.getTimeZone("GMT+7"));
            tvYValue.setText(""+ e.getY());
            tvXValue.setText(formatTime.format(time));
            super.refreshContent(e, highlight);
        }
        private MPPointF mOffset;
        @Override
        public MPPointF getOffset() {
            if(mOffset == null) {
                //Center the marker horizontally and vertically
                mOffset = new MPPointF(-(getWidth()/2), -getHeight());
            }
            return mOffset;
        }
    }
    private class ValueFormat implements IValueFormatter{
        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return "" ;
        }
    }

    private void setFormatTime(String dateFormat){
        formatTime = new SimpleDateFormat(dateFormat);
        formatTime.setTimeZone(TimeZone.getTimeZone("GMT+7"));
    }

    private void parseData(Bundle bundle) {
        if (bundle != null){
            houseID = bundle.getString("houseID");
        }
    }

    private void SettingUIGraph() {
        //Convert XAxis is time
            XAxis xAxis = graph.getXAxis();
            xAxis.setValueFormatter(new LineChartXAxisValueFormatter());
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextSize(12);
            xAxis.setLabelCount(5,true);
        //Custom Line graph
            lineDataSet1.setLineWidth(3);
            int[] colorArray = {R.color.green_10};
            lineDataSet1.setColors(colorArray,getActivity());
            lineDataSet1.setDrawCircles(false);
        //Hide description
            Legend legend = graph.getLegend();
            legend.setEnabled(false);
            Description description = new Description();
            description.setText("");
            graph.setDescription(description);
        //Custom border
            graph.setDrawBorders(true);
            graph.setBorderColor(R.color.blue_30);
            graph.setExtraLeftOffset(20);
            graph.setExtraRightOffset(10);
        //Hide grid
            graph.getXAxis().setDrawGridLines(false);
            graph.getAxisLeft().setDrawGridLines(false);
            graph.getAxisRight().setDrawGridLines(false);
        //Lock up vertical
            graph.setScaleYEnabled(false);
            graph.fitScreen();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("gh", "Graph1: resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("gh", "Graph1: pause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("gh", "Graph1: destroy");
    }
}
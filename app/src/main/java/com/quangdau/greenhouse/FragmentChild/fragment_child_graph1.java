package com.quangdau.greenhouse.FragmentChild;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.github.ybq.android.spinkit.SpinKitView;
import com.quangdau.greenhouse.ApiService.ApiServer;
import com.quangdau.greenhouse.R;
import com.quangdau.greenhouse.SharedPreferences.UserPreferences;
import com.quangdau.greenhouse.Spinner.spinnerLimitSetting.CategorySpinner;
import com.quangdau.greenhouse.Spinner.spinnerLimitSetting.CategorySpinnerAdapter;
import com.quangdau.greenhouse.language.Language;
import com.quangdau.greenhouse.modelsAPI.get_graph.dataReal;
import com.quangdau.greenhouse.modelsAPI.get_graph.dataGraph;

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
    RadioGroup radioGroup1;
    LineChart graph;
    TextView txtYaxisTitle;
    public ArrayList<Entry> dataVals;
    public ArrayList<ILineDataSet> dataSets;
    public dataGraph  mdataGraph;
    public List<dataReal> mdata;
    UserPreferences userPreferences;
    String houseID;

    //sipiner
    private Spinner spinnerTypeChart;
    private CategorySpinnerAdapter categoryTypeAdapter;


    //spinKet
    Dialog dialog;

    //variable arraylist get data api
    public List<dataReal> getApi7D;
    public List<dataReal> getApi1M;
//    public List<dataReal> getApi7DLand;
//    public List<dataReal> getApi1MLand;
//    public List<dataReal> getApi7DLand2;
//    public List<dataReal> getApi1MLand2;
//    public List<dataReal> getApi7DLand3;
//    public List<dataReal> getApi1MLand3;
//    public List<dataReal> getApi7DLand4;
//    public List<dataReal> getApi1MLand4;
//    public List<dataReal> getApi7DTemperature;
//    public List<dataReal> getApi1MTemperature;
    //boolean data graph 7d 1m
    public Boolean data7D = false;
    public Boolean data1M =false;
//    public Boolean data7DLand = false;
//    public Boolean data1MLand = false;
//    public Boolean data7DLand2 = false;
//    public Boolean data1MLand2 = false;
//    public Boolean data7DLand3 = false;
//    public Boolean data1MLand3 = false;
//    public Boolean data7DLand4 = false;
//    public Boolean data1MLand4 = false;
//    public Boolean data7DTemperature = false;
//    public Boolean data1MTemperature =false;

    //variable value first array
    public long valueFirstArray =0;

    //check button radio set time graph
    public final String setTime1H = "1h";
    public final String setTime1D = "1d";
    public final String setTime7D = "7d";
    public final String setTime1M = "30d";
    public String setTime = setTime1H;
    // check type graph
    public final String graphAir = "humidity";
    public final String  graphLand = "soil_moisture1";
    public final String  graphLand2 = "soil_moisture2";
    public final String  graphLand3 = "soil_moisture3";
    public final String  graphLand4 = "soil_moisture4";
    public final String  graphTemperature = "temperature";
    public String typeGraph = graphAir;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child_graph1,container,false);




        radioGroup1 = (RadioGroup) view.findViewById(R.id.time_check_graph);
        txtYaxisTitle = (TextView) view.findViewById(R.id.text_tile_yAxis);
        txtYaxisTitle.setText(getResources().getString(R.string.yAxis_air_humidity));
        graph = (LineChart) view.findViewById(R.id.graph);
        graph.setTouchEnabled(true);

        userPreferences = new UserPreferences(getActivity());

        //spiner view
        spinnerTypeChart =(Spinner) view.findViewById(R.id.typeChart1);
        List<CategorySpinner> list = new ArrayList<>();
        list.add(new CategorySpinner(getResources().getString(R.string.graph_air_humidity)));
        list.add(new CategorySpinner(getResources().getString(R.string.graph_temperature)));
        list.add(new CategorySpinner(getResources().getString(R.string.Soil_Moisture_1)));
        list.add(new CategorySpinner(getResources().getString(R.string.Soil_Moisture_2)));
        list.add(new CategorySpinner(getResources().getString(R.string.Soil_Moisture_3)));
        list.add(new CategorySpinner(getResources().getString(R.string.Soil_Moisture_4)));
        categoryTypeAdapter = new CategorySpinnerAdapter(getActivity(),R.layout.item_selected_language,list);
        spinnerTypeChart.setAdapter(categoryTypeAdapter);
        spinnerTypeChart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:{
                        typeGraph = graphAir;
                        data1M =false;
                        data7D =false;
                        txtYaxisTitle.setText(getResources().getString(R.string.yAxis_air_humidity));
                        getArrayDataGraph(userPreferences.getToken());
                    }break;
                    case 1:{
                        typeGraph = graphTemperature;
                        data1M =false;
                        data7D =false;
                        txtYaxisTitle.setText(getResources().getString(R.string.yAxis_temperature));
                        getArrayDataGraph(userPreferences.getToken());
                    }break;
                    case 2:{
                        typeGraph = graphLand;
                        data1M =false;
                        data7D =false;
                        txtYaxisTitle.setText(getResources().getString(R.string.yAxis_land_humidity));
                        getArrayDataGraph(userPreferences.getToken());
                    }break;
                    case 3:{
                        typeGraph = graphLand2;
                        data1M =false;
                        data7D =false;
                        txtYaxisTitle.setText(getResources().getString(R.string.yAxis_land_humidity));
                        getArrayDataGraph(userPreferences.getToken());
                    }break;
                    case 4:{
                        typeGraph = graphLand3;
                        data1M =false;
                        data7D =false;
                        txtYaxisTitle.setText(getResources().getString(R.string.yAxis_land_humidity));
                        getArrayDataGraph(userPreferences.getToken());
                    }break;
                    case 5:{
                        typeGraph = graphLand4;
                        data1M =false;
                        data7D =false;
                        txtYaxisTitle.setText(getResources().getString(R.string.yAxis_land_humidity));
                        getArrayDataGraph(userPreferences.getToken());
                    }break;


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        //even click value Chart
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



        dataVals = new ArrayList<>();
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.graph_1H:{
                        setTime = setTime1H;
                        getArrayDataGraph(userPreferences.getToken());
                    }
                    break;
                    case R.id.graph_1D:{
                        setTime = setTime1D;
                        getArrayDataGraph(userPreferences.getToken());
                    }
                    break;
                    case R.id.graph_7D:{
                        setTime= setTime7D;
                        if(data7D) {
                            mdata = getApi7D;
                            setDataGraph();
                        }else{
                            getArrayDataGraph(userPreferences.getToken());
                        }
                    }
                    break;
                    case R.id.graph_1M:{
                        setTime= setTime1M;
                        if(data1M){
                            mdata = getApi1M;
                            setDataGraph();
                        }else{
                            getArrayDataGraph(userPreferences.getToken());
                        }
                    }
                    break;
                    default:
                        break;

                }
            }
        });


        Log.e("gh", "token"+ userPreferences.getToken());
        return view;

    }

    @Override
    public void onStart() {

        super.onStart();
    }







    public class LineChartXAxisValueFormatter implements IAxisValueFormatter {

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            long realTime =(long) value+ valueFirstArray;
            long timeXaxis = realTime*1000l;
            Date timeMilliseconds = new Date(timeXaxis);
            //DateFormat dateTimeFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
            SimpleDateFormat formatTime = null;
            switch (setTime) {
                case setTime1H: {
                    formatTime = new SimpleDateFormat("HH:mm:ss");
                    formatTime.setTimeZone(TimeZone.getTimeZone("GMT+7"));
                }
                break;
                case setTime1D: {
                    formatTime = new SimpleDateFormat("HH:mm");
                    formatTime.setTimeZone(TimeZone.getTimeZone("GMT+7"));


                }
                break;
                case setTime7D: {
                    formatTime = new SimpleDateFormat("dd/MM");
                    formatTime.setTimeZone(TimeZone.getTimeZone("GMT+7"));


                }
                break;
                case setTime1M: {
                    formatTime = new SimpleDateFormat("dd/MM");
                    formatTime.setTimeZone(TimeZone.getTimeZone("GMT+7"));
                }
                break;

            }

            return formatTime.format(timeMilliseconds);



        }
    }
    private void getArrayDataGraph(String token){
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.win_layout_spinkit);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //lock window layout
        dialog.setCancelable(false);

        SpinKitView progressBar = dialog.findViewById(R.id.progressBar);
        //progressBar.setIndeterminateDrawable(new RotatingPlane());
        dialog.show();



        ApiServer get = ApiServer.retrofit.create(ApiServer.class);
        Call<dataGraph> call = get.getGraphData(token,"GetGraphData","house1",typeGraph,setTime);
        call.enqueue(new Callback<dataGraph>() {
            @Override
            public void onResponse(Call<dataGraph> call, Response<dataGraph> response) {
                mdata = new ArrayList<>();
                mdataGraph = response.body();
                long timeMdata;
                float valueMdata;

                for (int i=0; i< mdataGraph.getData().size();i++){
                    String dateString = mdataGraph.getData().get(i).getTime();
                    valueMdata = mdataGraph.getData().get(i).getFirst();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
                    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+0"));
                    Date date = null;
                    try {
                        date = dateFormat.parse(dateString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    timeMdata = date.getTime()/1000l;
                    mdata.add(new dataReal(timeMdata,valueMdata));

                }
                if(setTime == setTime7D && data7D == false){
                        getApi7D = mdata;
                        data7D =true;
                }
                if(setTime == setTime1M && data1M ==false){
                        getApi1M =mdata;
                        data1M =true;
                }
                setDataGraph();
            }

            @Override
            public void onFailure(Call<dataGraph> call, Throwable t) {
                Log.e("gh", "Error: " + t);

            }
        });


    }



    private void setDataGraph() {
        if (mdata == null) {
            Log.e("gh","vẽ thất bại");
            return;

        }
        if(mdata !=null) {
            dataVals.clear();
            valueFirstArray = mdata.get(0).getTime();
            for (int i = 0; i < mdata.size(); i++) {
                long xAxis = mdata.get(i).getTime() - valueFirstArray;
                float yAxis = mdata.get(i).getValue();
                dataVals.add(new Entry(xAxis, yAxis));
            }


            XAxis xAxis = graph.getXAxis();

            //xAxis.setLabelCount(3,true);
            xAxis.setValueFormatter(new LineChartXAxisValueFormatter());
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextSize(12);
            xAxis.setLabelCount(5,true);




            LineDataSet lineDataSet1 = new LineDataSet(dataVals,"");
            dataSets = new ArrayList<>();
            dataSets.add(lineDataSet1);
            LineData data = new LineData(dataSets);


            data.setValueFormatter(new ValueFormat());
            graph.setData(data);
            graph.invalidate();
            graph.setDrawGridBackground(false);
            graph.setDrawBorders(true);
            graph.setBorderColor(R.color.blue_30);

            Description description = new Description();
            description.setText("");
            graph.setDescription(description);
            graph.setExtraLeftOffset(20);
            graph.setExtraRightOffset(10);
            //set style line
            Legend legend = graph.getLegend();
            legend.setEnabled(false);
            lineDataSet1.setLineWidth(3);
            lineDataSet1.setColor(Color.RED);
            //  lineDataSet1.setDrawFilled(true);
            int colorArray[] = {R.color.green_10};
            lineDataSet1.setColors(colorArray,getActivity());

            //hide grid
            graph.getXAxis().setDrawGridLines(false);
            graph.getAxisLeft().setDrawGridLines(false);
            graph.getAxisRight().setDrawGridLines(false);


            //set point
            lineDataSet1.setDrawCircles(false);



            //set No data
            graph.setNoDataText("No data to Graph");
            graph.setNoDataTextColor(Color.BLUE);
            graph.getXAxis().setTextColor(Color.BLACK);
            graph.setScaleYEnabled(false);
            graph.fitScreen();
            dialog.dismiss();

            Log.e("gh",""+data7D);








        }
    }

    public class YourMarkerView extends MarkerView {

        private TextView tvYValue;
        private TextView tvXValue;
        public YourMarkerView(Context context, int layoutResource) {
            super(context, layoutResource);
            tvYValue = (TextView) findViewById(R.id.YValue);
            tvXValue = (TextView) findViewById(R.id.XValue);
        }
        // content (user-interface)
        @Override
        public void refreshContent(Entry e, Highlight highlight) {
            long realTime =((long) e.getX())+valueFirstArray;
            long timeXaxis = realTime*1000l;
            Date time = new Date(timeXaxis);
            SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
            formatTime.setTimeZone(TimeZone.getTimeZone("GMT+7"));


            tvYValue.setText(""+e.getY());
            tvXValue.setText(formatTime.format(time));

            // this will perform necessary layout
            super.refreshContent(e, highlight);
        }

        private MPPointF mOffset;

        @Override
        public MPPointF getOffset() {

            if(mOffset == null) {
                // center the marker horizontally and vertically
                mOffset = new MPPointF(-(getWidth() / 2), -getHeight());
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




}



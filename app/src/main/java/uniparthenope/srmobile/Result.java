package uniparthenope.srmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Result extends AppCompatActivity {


    private JSONArray report_record;

    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);



// RECUPERO info
        Intent i = getIntent();

        try {
            report_record = new JSONArray( i.getStringExtra("report") );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        showReport();


    }


    private void showReport(){


        //PREPARAZIONE LIST REPORT


        spinnerAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.row);

        for(int i=0; i<report_record.length(); i++){
            try {
                JSONObject j = report_record.getJSONObject(i);
                spinnerAdapter.add(j.getString("date"));
            }
            catch (JSONException e) {
            }
            spinnerAdapter.add(String.valueOf(i));
        }


        Spinner sp = (Spinner) findViewById(R.id.spinner_result);
        sp.setAdapter(spinnerAdapter);

        AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View row_selected, int arg2, long arg3) {

                TextView txt = (TextView) row_selected.findViewById(R.id.rowtext);
                String selectedDateReport =  txt.getText().toString();
                String tmp;

                TextView tot_views = (TextView) findViewById(R.id.total_views);
                TextView transactions = (TextView) findViewById(R.id.transactions);
                TextView scr = (TextView) findViewById(R.id.scr);


                for(int i=0; i<report_record.length(); i++)
                    try {
                        JSONObject record = report_record.getJSONObject(i);
                        tmp = record.getString("date");
                        if(tmp == selectedDateReport)
                        {
                            // SET TEXT A VIDEO DEI DATI SCELTI
                            tot_views.setText(record.getString("tot_views") );
                            transactions.setText(record.getString("transactions") );
                            scr.setText(record.getString("scr") );
                            break;
                        }
                    }
                    catch (JSONException e) {
                    }


            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            { /*...*/ }
        };


        sp.setOnItemSelectedListener( onItemSelectedListener );



    }



}




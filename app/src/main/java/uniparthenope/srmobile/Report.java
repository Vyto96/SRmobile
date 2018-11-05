package uniparthenope.srmobile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Report extends AppCompatActivity {

    private String storeId;
    private String store;

    private String start_date_selected;
    private TextView start_date_tv;
    private Button set_start_date_btn;

    private String end_date_selected;
    private TextView end_date_tv;
    private Button set_end_date_btn;
    private String url = SRconstantAPI.GET_REPORT;

    private Button get_report_btn;
    private JsonObjectRequest jr;

    private JSONArray report_record;

    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);



// RECUPERO info
        Intent i = getIntent();

        storeId = i.getStringExtra("id");
        store = i.getStringExtra("store");

        start_date_tv = (TextView) findViewById(R.id.start_date_tv);
        end_date_tv = (TextView) findViewById(R.id.end_date_tv);

        set_start_date_btn = (Button) findViewById(R.id.start_date_btn);
        set_start_date_btn.setOnClickListener(startDatePickerDialog);

        set_end_date_btn = (Button) findViewById(R.id.end_date_btn);
        set_end_date_btn.setOnClickListener(endDatePickerDialog);


// BUTTON GET REPORT

        get_report_btn = (Button) findViewById(R.id.start_report_btn);


        get_report_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = String.format(url, storeId, start_date_selected, end_date_selected);

                jr = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    reportSuccessListener,
                    reportErrorListener
                )
                {
                    // ADD DATA TO HEADERS
                    @Override
                    public Map getHeaders() throws AuthFailureError {
                        HashMap headers = new HashMap();
                        headers.put("api_key", SRconstantAPI.API_KEY);
                        return headers;
                    }
                };

                MySingletonQueue.getInstance(Report.this.getApplicationContext()).addToRequestQueue(jr);


            }
        });






    }


// VOLLEY REQUEST LISTENER IMPLEMENTATION
    private Response.Listener<JSONObject> reportSuccessListener = new Response.Listener<JSONObject>(){
        @Override
        public void onResponse(JSONObject response) {
            try {

                report_record = response.getJSONObject("report").getJSONArray("report");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Toast.makeText(Report.this, "report loaded correctly", Toast.LENGTH_LONG).show();

            Intent in = new Intent(Report.this, Result.class);
            in.putExtra("report", report_record.toString());
            startActivity(in);
        }
    };


    private Response.ErrorListener reportErrorListener=new Response.ErrorListener()
    {
        @Override
        public void onErrorResponse(VolleyError err)
        {
            Toast.makeText(Report.this, "Report not found", Toast.LENGTH_LONG).show();
        }
    };






    private View.OnClickListener startDatePickerDialog = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener(){
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month = month + 1;
                    String date = day + "/" + month + "/" + year;
                    start_date_selected = SRconstantAPI.ConstructDataApiFormat(day, month, year);
                    start_date_tv.setText(date);
                }
            };


            DatePickerDialog dialog = new DatePickerDialog(
                    Report.this,
                    android.R.style.Theme_Black,
                    mDateSetListener,
                    year,month,day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();


        }
    };


    private View.OnClickListener endDatePickerDialog = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener(){
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month = month + 1;
                    String date = day + "/" + month + "/" + year;
                    end_date_selected = SRconstantAPI.ConstructDataApiFormat(day, month, year);
                    end_date_tv.setText(date);
                }
            };


            DatePickerDialog dialog = new DatePickerDialog(
                    Report.this,
                    android.R.style.Theme_Black,
                    mDateSetListener,
                    year,month,day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();


        }
    };







}



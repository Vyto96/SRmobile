package uniparthenope.srmobile;

import android.content.Intent;
import android.os.Bundle;
//import android.support.customtabs.CustomTabsIntent;
//import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

//import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class Main extends AppCompatActivity {

    private JSONObject user;
    private JSONArray userStoreList;
    private TextView tv;
    private String storesUrl = null;
    private String username = null;
    private JsonObjectRequest jr;

    private ArrayAdapter<String> spinnerAdapter;
    private String selectedStoreId;
    private String selectedStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = getIntent();

        tv = (TextView) findViewById(R.id.tv);

// RECUPERO info

        try {
            user = new JSONObject( i.getStringExtra("user") );
            storesUrl = user.getString("stores_url");
            username = user.getString("username");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        tv.setText("Hy " + username + " select your store for Report" );


// GET STORES USER BY API
        String url = SRconstantAPI.HOME.concat( storesUrl );

        jr = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    storeSuccessListener,
                    storeErrorListener
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

        MySingletonQueue.getInstance(this.getApplicationContext()).addToRequestQueue(jr);


        Button report_btn = (Button) findViewById( R.id.get_report_btn );
        report_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Main.this, Report.class);
                in.putExtra("id", selectedStoreId);
                in.putExtra("store", selectedStore);
                startActivity( in );
            }
        });



//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity( new Intent(Main.this, AddStore.class) );
//            }
//        });
    }



    // VOLLEY REQUEST LISTENER IMPLEMENTATION
    private Response.Listener<JSONObject> storeSuccessListener = new Response.Listener<JSONObject>(){
        @Override
        public void onResponse(JSONObject response) {
            try {
                userStoreList = response.getJSONArray("user_stores");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Toast.makeText(Main.this, "store loaded correctly", Toast.LENGTH_LONG).show();
            showStore();
        }
    };


    private Response.ErrorListener storeErrorListener=new Response.ErrorListener()
    {
        @Override
        public void onErrorResponse(VolleyError err)
        {
            Toast.makeText(Main.this, "no store configured, add a store from web app!", Toast.LENGTH_LONG).show();
        }
    };






    private void showStore(){


        //PREPARAZIONE SPINNER STORE
        spinnerAdapter = new ArrayAdapter<String>(this, R.layout.row);

        for(int i=0; i<userStoreList.length(); i++){
            try {
                JSONObject j=userStoreList.getJSONObject(i);
                spinnerAdapter.add(j.getString("store_name"));
            }
            catch (JSONException e) {
            }
        }

        Spinner sp = (Spinner) findViewById(R.id.spinner_store);
        sp.setAdapter(spinnerAdapter);


        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                TextView txt = (TextView) arg1.findViewById(R.id.rowtext);
                selectedStore =  txt.getText().toString();
                String tmp;

                for(int i=0; i<userStoreList.length(); i++)
                    try {
                        JSONObject j=userStoreList.getJSONObject(i);
                        tmp = j.getString("store_name");
                        if(tmp == selectedStore)
                        {
                            selectedStoreId = j.getString("id");
                            break;
                        }
                    }
                    catch (JSONException e) {
                    }


            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            { /*...*/ }
        });



    }


}

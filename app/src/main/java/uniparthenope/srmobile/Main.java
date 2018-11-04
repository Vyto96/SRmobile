package uniparthenope.srmobile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Main extends AppCompatActivity {

    private JSONObject user;
    private JSONArray userStoreList;
    private TextView tv; //, tv2;
    private String storesUrl = null;
    private String username = null;
    private JsonObjectRequest jr;
    private Intent addStoreIntent;


//    private ArrayAdapter<String> listviewAdapter;
    private ArrayAdapter<String> spinnerAdapter;

    private Collection<String> stores;


    private void showStore(){

//        ListView lv=(ListView) findViewById(R.id.listview_function);
//        listviewAdapter = new ArrayAdapter<String>(this, R.layout.row);
//        lv.setAdapter(listviewAdapter);

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
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                TextView txt=(TextView) arg1.findViewById(R.id.rowtext);
                String s=txt.getText().toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            { }
        });



    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = getIntent();

        tv = (TextView) findViewById(R.id.tv);
        //tv2 = (TextView) findViewById(R.id.tv2);

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

        Toast.makeText(Main.this, url, Toast.LENGTH_LONG ).show();

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

            }
        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addStoreIntent = new Intent(Main.this, AddStore.class);
//                startActivity(addStoreIntent);
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
            Toast.makeText(Main.this, "store ricevuti correttamente", Toast.LENGTH_LONG).show();
            showStore();
        }
    };


    private Response.ErrorListener storeErrorListener=new Response.ErrorListener()
    {
        @Override
        public void onErrorResponse(VolleyError err)
        {
            Toast.makeText(Main.this, "no store configured", Toast.LENGTH_LONG).show();
           // tv2.setText("No store configured click on plus button for add a store");
        }
    };




}

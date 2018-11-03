package uniparthenope.srmobile;

import android.content.Intent;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddStore extends AppCompatActivity {

    private EditText store_name;
    private StringRequest sr;
    private Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_store);

        store_name = (EditText) findViewById(R.id.add_store_name);

        String url = SRconstantAPI.ADD_STORE;
//        url.replace( "<id_user>" , '1');
//        url.replace("<id_ecom>", '1');

        sr = new StringRequest(
                Request.Method.POST,
                url,
                add_storeSuccessListener,
                add_storeErrorListener
        )
        {
            // ADD DATA TO HEADERS
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("api_key", SRconstantAPI.API_KEY);
                return headers;
            }
            // ADD PARAMS TO BODY
            @Override
            protected Map<String,String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("store_name", store_name.getText().toString() );

                return params;
            }
        };

        btn = (Button) findViewById(R.id.add_store_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // aggiunta alla coda delle richieste
                MySingletonQueue.getInstance( AddStore.this.getApplicationContext() ).addToRequestQueue(sr);
            }
        });
    }


    // VOLLEY REQUEST LISTENER IMPLEMENTATION
    private Response.Listener<String> add_storeSuccessListener = new Response   .Listener<String>(){
        @Override
        public void onResponse(String response) {
            Toast.makeText(AddStore.this, "Store successfull added ", Toast.LENGTH_LONG).show();
            startActivity( new Intent(AddStore.this, Main.class) );
        }
    };

    private Response.ErrorListener add_storeErrorListener=new Response.ErrorListener()
    {
        @Override
        public void onErrorResponse(VolleyError err)
        {
            Toast.makeText(AddStore.this, "Invalid field, please retry", Toast.LENGTH_LONG).show();
        }
    };



}



//***************************************************************************************************************************
    //PROVA GOOGLE CUSTOM TABS
    //***************************************************************************************************************************
//
//    String url = SRconstantAPI.MIDDLE_EBAY.concat("/get_token");
//    //String url = "https://www.google.com/";
//    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
//
//
//    // set toolbar color and/or setting custom actions before invoking build()
//    // Once ready, call CustomTabsIntent.Builder.build() to create a CustomTabsIntent
//    CustomTabsIntent customTabsIntent = builder.build();
//
//// and launch the desired Url with CustomTabsIntent.launchUrl()
//                customTabsIntent.launchUrl(Main.this, Uri.parse(url));

package uniparthenope.srmobile;

import android.content.Intent;
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

public class Register extends AppCompatActivity {

    private EditText username,  email, psw;
    private Button btn;

    private Intent loginIntent;

    private StringRequest sr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.reg_username);
        email = (EditText) findViewById(R.id.reg_email);
        psw = (EditText) findViewById(R.id.reg_psw);

        sr = new StringRequest(
                Request.Method.POST,
                SRconstantAPI.REGISTER,
                registerSuccessListener,
                registerErrorListener
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
                params.put("username", username.getText().toString() );
                params.put("password", psw.getText().toString() );
                params.put("email", email.getText().toString() );
                return params;
            }
        };

        btn = (Button) findViewById(R.id.reg_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // aggiunta alla coda delle richieste
                MySingletonQueue.getInstance( Register.this.getApplicationContext() ).addToRequestQueue(sr);
            }
        });
    }


    // VOLLEY REQUEST LISTENER IMPLEMENTATION
    private Response.Listener<String> registerSuccessListener = new Response   .Listener<String>(){
        @Override
        public void onResponse(String response) {
            Toast.makeText(Register.this, "register successfull", Toast.LENGTH_LONG).show();
            loginIntent = new Intent(Register.this, Login.class);
            startActivity(loginIntent);
        }
    };

    private Response.ErrorListener registerErrorListener=new Response.ErrorListener()
    {
        @Override
        public void onErrorResponse(VolleyError err)
        {
            Toast.makeText(Register.this, "Invalid field, please retry", Toast.LENGTH_LONG).show();
        }
    };

}

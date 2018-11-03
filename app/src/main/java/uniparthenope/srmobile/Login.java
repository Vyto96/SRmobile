package uniparthenope.srmobile;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;

import java.util.HashMap;
import java.util.Map;




public class Login extends AppCompatActivity {

    private EditText usermail;
    private EditText psw;

    private StringRequest sr;
    //private RequestQueue queue;

    private Button log_btn;
    private Intent loginIntent;

    private Button register_btn;
    private Intent registerIntent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // REGISTER BUTTON
        register_btn = (Button) findViewById(R.id.register_btn);
        registerIntent = new Intent(this, Register.class);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(registerIntent);
            }
        });



        // LOGIN BUTTON
        usermail = (EditText) findViewById(R.id.usermail);
        psw = (EditText) findViewById(R.id.psw);
        //queue = MySingletonQueue.getInstance(this.getApplicationContext()).getRequestQueue();

        String url = SRconstantAPI.LOGIN;

        sr = new StringRequest(
                Request.Method.GET,
                url,
                loginSuccessListener,
                loginErrorListener
        )
        {
            // ADD DATA TO HEADERS
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("api_key", SRconstantAPI.API_KEY);
                headers.put("logger", usermail.getText().toString() );
                headers.put("psw", psw.getText().toString() );
                return headers;
            }
        };

        loginIntent = new Intent(this, Main.class);
        log_btn = (Button) findViewById(R.id.login_btn);
        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // aggiunta alla coda delle richieste
                MySingletonQueue.getInstance( Login.this.getApplicationContext() ).addToRequestQueue(sr);
            }
        });

    }


    // VOLLEY REQUEST LISTENER IMPLEMENTATION
    private Response.Listener<String> loginSuccessListener = new Response   .Listener<String>(){
        @Override
        public void onResponse(String response) {
            Toast.makeText(Login.this, "login successfull", Toast.LENGTH_LONG).show();

            loginIntent.putExtra("user", response);
            startActivity(loginIntent);
        }
    };


    private Response.ErrorListener loginErrorListener=new Response.ErrorListener()
    {
        @Override
        public void onErrorResponse(VolleyError err)
        {
            Toast.makeText(Login.this, "Invalid username or password ", Toast.LENGTH_LONG).show();
        }
    };


}

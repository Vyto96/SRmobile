package uniparthenope.srmobile;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class MySingletonQueue {

    private static MySingletonQueue mInstance;
    private RequestQueue mRequestQueue;

    private static Context mCtx;

    private MySingletonQueue(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized MySingletonQueue getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySingletonQueue(context);
        }
        return mInstance;
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}


// Get a RequestQueue
//RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();

// Add a request (in this example, called stringRequest) to your RequestQueue.
//    MySingleton.getInstance(this).addToRequestQueue(stringRequest);





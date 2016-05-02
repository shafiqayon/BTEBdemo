package ayon.rahman.shafiqur.btebdynamicmoduleforpostdatatoserver;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class noticeshow extends AppCompatActivity {
    ImageLoader imageLoader = new ImageLoader(this);
    String title, SERVERADDRESS, imageurl, message;
    ImageView imageView;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_noticeshow);
        imageView = (ImageView) findViewById(R.id.imageView);
        tv = (TextView) findViewById(R.id.textView2);
        title = getIntent().getStringExtra("t");
        SERVERADDRESS = "http://www.inanik.info/project/m_i.php";
        Toast.makeText(noticeshow.this, title, Toast.LENGTH_SHORT).show();
        final ProgressDialog progressDialog = ProgressDialog.show(noticeshow.this, "Wait", "Syncing Notice", true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVERADDRESS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray ja = null;
                            ja = new JSONArray(response);
                            for (int i = 0; i < ja.length(); i++) {
                                JSONObject jsonObject = (JSONObject) ja.get(i);
                                message = (String) jsonObject.get("message");
                                imageurl = (String) jsonObject.get("imurl");
                            }
                            tv.setText(message);
                            imageLoader.DisplayImage(imageurl, imageView);
                            progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley error", error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", title);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}

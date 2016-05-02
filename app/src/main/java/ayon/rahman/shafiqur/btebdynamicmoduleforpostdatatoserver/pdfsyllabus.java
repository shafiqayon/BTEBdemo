package ayon.rahman.shafiqur.btebdynamicmoduleforpostdatatoserver;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class pdfsyllabus extends AppCompatActivity {

    public ArrayList<String> strArrcategory = new ArrayList<String>();
    String category, categoryname, SERVERADDRESS = "http://www.inanik.info/project/pdftitletoitemssyllabus.php";
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        webview = (WebView) findViewById(R.id.webViewpdf);
        webview.getSettings().setJavaScriptEnabled(true);


        categoryname = getIntent().getStringExtra("category");

        final ProgressDialog progressDialog = ProgressDialog.show(pdfsyllabus.this, "Wait", "Downloading Notice", true);
        progressDialog.setCancelable(true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVERADDRESS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray ja = null;
                            ja = new JSONArray(response);
                            for (int i = 0; i < ja.length(); i++) {
                                JSONObject jsonObject = (JSONObject) ja.get(i);
                                category = (String) jsonObject.get("pdfurl");
                            }
                            String pdfLink = category;
                            webview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + pdfLink);
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
                params.put("name", categoryname);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}

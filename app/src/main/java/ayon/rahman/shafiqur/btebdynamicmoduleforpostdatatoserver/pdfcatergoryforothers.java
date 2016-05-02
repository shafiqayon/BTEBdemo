package ayon.rahman.shafiqur.btebdynamicmoduleforpostdatatoserver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

public class pdfcatergoryforothers extends AppCompatActivity {
    public static final String KEY_USERNAME = "name";
    public ArrayList<String> strArrcategory = new ArrayList<String>();
    String SERVERADDRESS = "http://www.inanik.info/project/pdfcategoryviewforother.php";
    ListView listView;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfcatergory);

        final ProgressDialog progressDialog = ProgressDialog.show(pdfcatergoryforothers.this, "Wait", "Syncing Notice", true);
        progressDialog.setCancelable(true);
        listView = (ListView) findViewById(R.id.listViewpdf);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVERADDRESS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray ja = null;
                            ja = new JSONArray(response);
                            for (int i = 0; i < ja.length(); i++) {
                                JSONObject jsonObject = (JSONObject) ja.get(i);
                                String category = (String) jsonObject.get("category");
                                strArrcategory.add(category);
                            }
                            final String[] category = strArrcategory.toArray(new String[strArrcategory.size()]);
                            adapter = new ArrayAdapter<String>(pdfcatergoryforothers.this, R.layout.custom_textview, category);
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Bundle messageBundle = new Bundle();
                                    messageBundle.putString("category", strArrcategory.get(position));
                                    Intent i = new Intent(pdfcatergoryforothers.this, pdftitleforothers.class);
                                    i.putExtra("category", strArrcategory.get(position));
                                    startActivity(i);
                                }
                            });
                            progressDialog.dismiss();
                            adapter.notifyDataSetChanged();
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
                //params.put(KEY_USERNAME, "গবেষণা");

                params.put(KEY_USERNAME, "তারকা");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}

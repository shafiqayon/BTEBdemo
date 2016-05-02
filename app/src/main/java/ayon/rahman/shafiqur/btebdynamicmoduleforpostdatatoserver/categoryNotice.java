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

public class categoryNotice extends AppCompatActivity {
    public ArrayList<String> strArrTitle = new ArrayList<String>();
    public ArrayList<String> strArrMessage = new ArrayList<String>();
    String SERVERADDRESS;
    ListView listView;
    ArrayAdapter<String> adapter;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_notice);
        category = getIntent().getStringExtra("category");
        SERVERADDRESS = "http://www.inanik.info/project/hj_all_info.php";
        listView = (ListView) findViewById(R.id.listViewcategorywisenotice);
        final ProgressDialog progressDialog = ProgressDialog.show(categoryNotice.this, "Wait", "Syncing Notice", true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVERADDRESS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray ja = null;
                            ja = new JSONArray(response);
                            for (int i = 0; i < ja.length(); i++) {
                                JSONObject jsonObject = (JSONObject) ja.get(i);
                                String title = (String) jsonObject.get("title");
                                strArrTitle.add(title);
                            }
                            final String[] titlearray = strArrTitle.toArray(new String[strArrMessage.size()]);
                            adapter = new ArrayAdapter<String>(categoryNotice.this, android.R.layout.simple_list_item_1, titlearray);
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Bundle messageBundle = new Bundle();
                                    messageBundle.putString("t", strArrTitle.get(position));
                                    Intent i = new Intent(categoryNotice.this, noticeshow.class);
                                    i.putExtra("t", strArrTitle.get(position));
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
                params.put("name", category);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

package ayon.rahman.shafiqur.btebdynamicmoduleforpostdatatoserver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import java.util.HashSet;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    public static final String KEY_USERNAME = "name";
    public ArrayList<String> strArrcategory = new ArrayList<String>();
    public ArrayList<String> strArrTitle = new ArrayList<String>();
    public ArrayList<String> strArrMessage = new ArrayList<String>();
    public ArrayList<String> strArrImageUrl = new ArrayList<String>();

    public HashSet<String> strcategoryHash = new HashSet<String>();
    public HashSet<String> strTitleHash = new HashSet<String>();
    public HashSet<String> strMessageHash = new HashSet<String>();
    public HashSet<String> strImageUrlHash = new HashSet<String>();
    public RequestQueue queue;


    String SERVERADDRESS;
    EditText editText;
    ListView listView;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);
        SERVERADDRESS = "http://www.inanik.info/project/hj.php";
        listView = (ListView) findViewById(R.id.listViewCategory);
       /* txt = (TextView) findViewById(R.id.raw);
        bt = (Button) findViewById(R.id.sendData);
        editText = (EditText) findViewById(R.id.editText);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt.setText("");
                registerUser();
            }
        });*/
        registerUser();
    }

    private void registerUser() {
        final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "Wait", "Syncing Notice", true);
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
                                strcategoryHash.add(category);
                            }
                            final String[] category = strArrcategory.toArray(new String[strArrcategory.size()]);
                            adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.custom_textview, category);
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Bundle messageBundle = new Bundle();
                                    messageBundle.putString("category", strArrcategory.get(position));
                                    Intent i = new Intent(MainActivity.this, categoryNotice.class);
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
                params.put(KEY_USERNAME, "তারকা");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
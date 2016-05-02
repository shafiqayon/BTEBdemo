package ayon.rahman.shafiqur.btebdynamicmoduleforpostdatatoserver;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class noticeview extends AppCompatActivity {
    static String title;
    public Context context = noticeview.this;
    public RequestQueue queue;
    public Button sync;
    public StringBuilder stringBuilder;
    public ArrayList<String> strArrTitle = new ArrayList<String>();
    public ArrayList<String> strArrMessage = new ArrayList<String>();
    public ArrayList<String> strArrImageUrl = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    String serverAddress = "http://www.inanik.info/project/btebnoticemock.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        stringBuilder = new StringBuilder();
        queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticeview);
        final ListView listView = (ListView) findViewById(R.id.noticelistview);
        final ProgressDialog progressDialog = ProgressDialog.show(noticeview.this, "Wait", "Syncing Notice", true);
        //network connectivity check 7.02.16 11:11Am
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(serverAddress,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {

                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                title = (String) jsonObject.get("title");
                                String imgurl = (String) jsonObject.get("imurl");
                                String message = (String) jsonObject.get("notice");
                                strArrTitle.add(title);
                                strArrMessage.add(message);
                                strArrImageUrl.add(imgurl);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        //listview here
                        final String[] titlearray = strArrTitle.toArray(new String[strArrMessage.size()]);
                        adapter = new ArrayAdapter<String>(noticeview.this, R.layout.custom_textview, titlearray);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Bundle messageBundle = new Bundle();
                                messageBundle.putString("message", strArrMessage.get(position));
                                messageBundle.putString("imurl", strArrImageUrl.get(position));
                                Intent i = new Intent(noticeview.this, noticeshow.class);
                                i.putExtra("message", strArrMessage.get(position));
                                i.putExtra("imurl", strArrImageUrl.get(position));
                                startActivity(i);

                            }
                        });
                        progressDialog.dismiss();

                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }


        );
        queue.add(jsonArrayRequest);


    }

}


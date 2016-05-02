package ayon.rahman.shafiqur.btebdynamicmoduleforpostdatatoserver;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivityForDash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ImageButton im = (ImageButton) findViewById(R.id.imageButton);
        ImageButton noticebutton = (ImageButton) findViewById(R.id.imageButton);
        ImageButton othersbutton = (ImageButton) findViewById(R.id.imageButton2);
        ImageButton syllabus = (ImageButton) findViewById(R.id.imageButton3);
        ImageButton results = (ImageButton) findViewById(R.id.imageButton4);
        //ImageButton pdfreader = (ImageButton) findViewById(R.id.pdfViewer);
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            Toast.makeText(MainActivityForDash.this, "Connected to the internet", Toast.LENGTH_LONG).show();
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivityForDash.this)
                    .setMessage("Please turn on the internet connection")
                    .setTitle("No Internet Connection")
                    .setIcon(R.drawable.nointernet);
            alert.create();
            alert.show();
        }
        othersbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivityForDash.this, pdfcatergoryforothers.class);
                startActivity(i);
            }
        });
        noticebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivityForDash.this, pdfcatergoryforsyllabus.class);
                startActivity(i);
            }
        });
        syllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivityForDash.this, pdfcatergory.class);
                startActivity(i);
            }
        });
        results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivityForDash.this, pdfcatergoryforresult.class);
                startActivity(i);
            }
        });
//        /*pdfreader.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MainActivityForDash.this, pdfcatergory.class);
//                startActivity(i);
//            }
//        });*/
    }
}

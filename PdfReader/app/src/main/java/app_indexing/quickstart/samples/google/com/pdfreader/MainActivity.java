package app_indexing.quickstart.samples.google.com.pdfreader;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnView = (Button) findViewById(R.id.btnView);

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LongOperation().execute();
            }
        });
    }

    private class LongOperation extends AsyncTask<String, Void, Void> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setCancelable(false);
            pDialog.setMessage("Loading...");
            pDialog.show();
        }


        @Override
        protected Void doInBackground(String... strings) {
            try {
                String fileName = "xyz";
                String fileExtension = ".pdf";
                URL url = new URL("http://maven.apache.org/maven-1.x/maven.pdf");
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setDoOutput(true);
                c.connect();
                String PATH = Environment.getExternalStorageDirectory() + "/mandiapp/";
                File file = new File(PATH);
                file.mkdirs();
                File outputFile = new File(file, fileName + fileExtension);
                FileOutputStream fos = new FileOutputStream(outputFile);
                InputStream is = c.getInputStream();
                byte[] buffer = new byte[1024];
                int len1 = 0;
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);
                }
                fos.close();
                is.close();
                pDialog.dismiss();
                System.out.println("--pdf downloaded--ok--" + "http://maven.apache.org/maven-1.x/maven.pdf");
            } catch (Exception e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            File pdfFile = new File("/sdcard/mandiapp/xyz.pdf");
            Uri path = Uri.fromFile(pdfFile);
            intent.setDataAndType(path, "application/pdf");
            startActivity(intent);
        }
    }

}


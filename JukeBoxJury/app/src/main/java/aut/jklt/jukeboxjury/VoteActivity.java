package aut.jklt.jukeboxjury;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.view.View;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


public class VoteActivity extends AppCompatActivity {
    private final int ONE_VOTE = 1;     //the vote that is returned to .PlaylistActivity(.MainActivity in this test)
    public static final String URL = "localhost:8080/JukeBoxJury/JukeBoxJury";
    public static final String NAMESPACE = "http://jklt.webservices";
    public static final String SOAP_ACTION_PREFIX = "/";
    private static final String METHOD = "voteForMusic";
    private String title = "Thriller";
    String vote = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

//        Bundle extras = getIntent().getExtras();    //gets intent from MainActivity
//        title = extras.getString("Song Title");     //which will be the song title

        Button vote = (Button) findViewById(R.id.vote_button);
        vote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //System.out.println("About to instantiate taskRunner");
                //Toast.makeText(getBaseContext(), "About to instantiate taskRunner", Toast.LENGTH_SHORT).show();
                new AsyncTaskRunner().execute(title);
                //Toast should read "Vote was successful"
//                Toast.makeText(getBaseContext(), vote, Toast.LENGTH_SHORT).show();
                //Intent returnVote = new Intent();
                //returnVote.putExtra("vote", 1);         //has been tested with other values for int
                //setResult(RESULT_OK, returnVote);
                //finish();
            }
        });
    }

    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {

        private String resp;

        @Override
        protected String doInBackground(String... title) {
            publishProgress(); // Calls onProgressUpdate()
            try {
                // SoapEnvelop.VER11 is SOAP Version 1.1 constant

                //SoapObject that calls webservice address and method name
                SoapObject request = new SoapObject(NAMESPACE, METHOD);

                //create parameters to be passed via ksoap
                PropertyInfo song = new PropertyInfo();
                song.setName("Song Title");
                song.setValue(title);
                song.setType(String.class);
                request.addProperty(song);

                //bodyOut is the body object to be sent out with this envelope
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

                envelope.dotNet = true;     //may have to remove this

                envelope.setOutputSoapObject(request);
                envelope.bodyOut = request;
                HttpTransportSE transport = new HttpTransportSE(URL);

                try {
                    transport.call(NAMESPACE + SOAP_ACTION_PREFIX + METHOD, envelope);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }

                //bodyIn is the body object received with this envelope
                if (envelope.bodyIn != null) {
                    //getProperty() Returns a specific property at a certain index.
                    Toast.makeText(getBaseContext(), "call came back from server", Toast.LENGTH_SHORT).show();
                    System.out.println("call came back from server");
                    SoapObject resultSOAP = (SoapObject) envelope.bodyIn;
                    resp = resultSOAP.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }

        /**
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog

        }

        /**
         * //@see android.os.AsyncTask#onProgressUpdate(Progress[])
         */
        @Override
        protected void onProgressUpdate(Void... params) {
            // Things to be done while execution of long running operation is in
            // progress. For example updating ProgessDialog
            TextView vote = (TextView) findViewById(R.id.song_name);
            vote.setText("in onProgressUpdate");
        }

        /**
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            // In this example it is the return value from the web service
            //something here is not right maybe get [0] element from result string array
            TextView vote = (TextView) findViewById(R.id.song_name);
            vote.setText(result);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

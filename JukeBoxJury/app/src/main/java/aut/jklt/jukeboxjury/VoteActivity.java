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
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


public class VoteActivity extends AppCompatActivity {
    private final int ONE_VOTE = 1;     //the vote that is returned to .PlaylistActivity(.MainActivity in this test)
    public static final String URL = "localhost:8080/JukeBoxJury/JukeBoxJury?WSDL";
    public static final String NAMESPACE = "http://webservices.jklt";
    public static final String SOAP_ACTION_PREFIX = "/";
    private static final String METHOD = "voteForMusic";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();        //gets intent from MainActivity

        Button vote = (Button) findViewById(R.id.vote_button);
        vote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "You voted for the song", Toast.LENGTH_LONG).show();
                Intent returnVote = new Intent();
                returnVote.putExtra("vote", 1);         //has been tested with other values for int
                setResult(RESULT_OK, returnVote);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Loading contents..."); // Calls onProgressUpdate()
            try {
                // SoapEnvelop.VER11 is SOAP Version 1.1 constant
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                SoapObject request = new SoapObject(NAMESPACE, METHOD);
                //bodyOut is the body object to be sent out with this envelope
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
                    SoapPrimitive resultSOAP = (SoapPrimitive) ((SoapObject) envelope.bodyIn).getProperty(0);
                    resp = resultSOAP.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }

        /**
         *
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            // In this example it is the return value from the web service
            TextView vote = (TextView) findViewById(R.id.song_name);
            vote.setText(result);
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
        //@Override
        protected void onProgressUpdate(String text) {
            // Things to be done while execution of long running operation is in
            // progress. For example updating ProgessDialog
        }

//        @Override
//        public boolean onOptionsItemSelected(MenuItem item) {
//            // Handle action bar item clicks here. The action bar will
//            // automatically handle clicks on the Home/Up button, so long
//            // as you specify a parent activity in AndroidManifest.xml.
//            int id = item.getItemId();
//
//            //noinspection SimplifiableIfStatement
//            if (id == R.id.action_settings) {
//                return true;
//            }
//
//            return super.onOptionsItemSelected(item);
//        }
    }
}

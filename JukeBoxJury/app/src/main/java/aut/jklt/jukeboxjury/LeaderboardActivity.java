package aut.jklt.jukeboxjury;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class LeaderboardActivity extends AppCompatActivity {
    public static final String URL = "http://192.168.42.211:8080/JukeBoxJury/JukeBoxJury";
    public static final String NAMESPACE = "http://webservices.jklt/";

    /**********************************************************************************
     *                   YOU WILL NEED TO CHANGE THE METHOD HERE

        private static final String METHOD = "METHOD YOU ARE CALLING FROM WEB SERVICE";

     *************************************************************************************/
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        listView = (ListView)findViewById(R.id.leader_board_list);

        String[] values = new String[]{"First song,", "Second song", "Third song", "Fourth song", "Fifth song", "Sixth song" };

        new AsyncTaskRunner().execute(values);            //call inner class to run in background
    }

    /*************************************************************************************************
     * YOU WILL NEED TO LOOK AT THE DOCS FOR ASYNC TASK TO SEE HOW TO IMPLEMENT YOUR PART OF THE CODE *
     *         BUT ASK ME IF YOU GET STUCK. NOT SURE HOW MUCH HELP I'LL BE BUT YOU NEVER KNOW           *
     *************************************************************************************************/

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        SoapSerializationEnvelope envelope;

        @Override
        protected String doInBackground(String... info) {
            publishProgress("Processing Vote"); // Calls onProgressUpdate()
            try {
                //SoapObject that calls webservice address and method name
                SoapObject request = new SoapObject(NAMESPACE, METHOD);

                //add song name property to soap object to be passed via ksoap2

        /***************************************************************************
                 * YOU WILL NEED TO CHANGE THIS TO SUIT YOU PART OF THE CODE
                 *
                        request.addProperty("song",songInfo[0]);
                 *
                 *
         ***************************************************************************/

                //SoapEnvelope.VER11 is SOAP Version 1.1 constant
                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);

                //bodyOut is the body object to be sent out with this envelope
                envelope.bodyOut = request;

                //create transport package to send to web service method
                HttpTransportSE transport = new HttpTransportSE(URL);

                //attempt to connect to web service
                try {
                    transport.call(NAMESPACE + METHOD, envelope);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                catch (XmlPullParserException e) {
                    e.printStackTrace();
                }

                //bodyIn is the body object received with this envelope
                if (envelope.bodyIn != null) {
                    resp = envelope.getResponse().toString();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }

        //called before connection to web service is made
        @Override
        protected void onPreExecute() {
            // Things to be done before execution of long running operation.
        }

        //called while connection to web service is happening
        @Override
        protected void onProgressUpdate(String... params) {
            // Things to be done while execution of long running operation is in progress
            TextView songName = (TextView) findViewById(R.id.song_name);
            songName.setText(params[0]);
        }

        //called after connection to web service is complete
        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            TextView songName = (TextView) findViewById(R.id.song_name);
            songName.setText(result);
        }
    }
}



package aut.jklt.jukeboxjury;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class ListEventsActivity extends AppCompatActivity {

    public static final String URL = "http://192.168.42.211:8080/JukeBoxJury/JukeBoxJury";
    public static final String NAMESPACE = "http://webservices.jklt/";

    /**********************************************************************************
     *                   YOU WILL NEED TO CHANGE THE METHOD HERE

     private static final String METHOD = "METHOD YOU ARE CALLING FROM WEB SERVICE";

     *************************************************************************************/
    private View viewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_events);

        ListView l = (ListView) findViewById(R.id.listview);
        String[] values = new String[] { "Dance Party ", "Student DanceNight", "Rock with DJ Dallu",
                "Mtv Unplugged Arijit ", "Live performance Bruno Mars" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
        viewContainer = findViewById(R.id.undobar);
        l.setAdapter(adapter);

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
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }

                //bodyIn is the body object received with this envelope
                if (envelope.bodyIn != null) {
                    resp = envelope.getResponse().toString();
                }
            } catch (Exception e) {
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

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_list_events, menu);
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

    public void onClick(View view) {
        Toast.makeText(this, "Deletion undone", Toast.LENGTH_LONG).show();
        viewContainer.setVisibility(View.GONE);
    }
}

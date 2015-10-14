package aut.jklt.jukeboxjury;

import android.os.AsyncTask;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class AsyncTaskRunner extends AsyncTask<String, String, String> {
    public static final String URL = "localhost:8080/JukeBoxJury/JukeBoxJury?WSDL";
    public static final String NAMESPACE = "http://webservices.jklt";
    public static final String SOAP_ACTION_PREFIX = "/";
    private static final String METHOD = "voteForMusic";
    private String resp;
    private String title = "";

    @Override
    protected String doInBackground(String... params) {
        publishProgress("Loading contents..."); // Calls onProgressUpdate()
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
            //envelope.bodyOut = request;
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
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    @Override
    protected void onPostExecute(String result) {
        // execution of result of Long time consuming operation
        // In this example it is the return value from the web service
        //TextView vote = (TextView) getContext().findViewById(R.id.song_name);
        //vote.setText(result);
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
}

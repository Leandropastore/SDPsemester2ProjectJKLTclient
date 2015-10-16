//package aut.jklt.jukeboxjury;
//
//import android.os.AsyncTask;
//import android.util.Log;
//import android.widget.TextView;
//
//import org.ksoap2.SoapEnvelope;
//import org.ksoap2.serialization.PropertyInfo;
//import org.ksoap2.serialization.SoapObject;
//import org.ksoap2.serialization.SoapSerializationEnvelope;
//import org.ksoap2.transport.HttpTransportSE;
//import org.xmlpull.v1.XmlPullParserException;
//
//import java.io.IOException;
//
//public class AsyncTaskRunner extends AsyncTask<String, String, String> {
//    public static final String URL = "http://192.168.42.211:8080/JukeBoxJury/JukeBoxJury";
//    public static final String NAMESPACE = "http://webservices.jklt/";
//    public static final String SOAP_ACTION_PREFIX = "/";
//    private static final String METHOD = "voteForMusic";
//    private String methodName;
//    private String resp;
//    SoapSerializationEnvelope envelope;
//
////    @Override
//    protected String doInBackground(String... info) {
//        String songName = info[0];
//        if(songName.equals("Thriller"))
//            Log.i("It's all good", "something good");
//        methodName = info[1];
//
//        publishProgress("Processing Vote"); // Calls onProgressUpdate()
//        try {
//            //SoapEnvelop.VER11 is SOAP Version 1.1 constant
//
//            //SoapObject that calls webservice address and method name
//            SoapObject request = new SoapObject(NAMESPACE, METHOD);
//
//            //create parameters to be passed via ksoap
////            PropertyInfo songProps = new PropertyInfo();
////            songProps.setName("Song Title");
////            songProps.setValue(songName);
////            songProps.setType(String.class);
//            request.addProperty("song", songName);
//
//            //bodyOut is the body object to be sent out with this envelope
//            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//
//            envelope.dotNet = true;     //may have to remove this
//            Log.i(info[1], "Method name");
//            Log.i(info[0], "Song name");
//            envelope.setOutputSoapObject(request);
//            envelope.bodyOut = request;
//            //create transport package to send to web service method
//            HttpTransportSE transport = new HttpTransportSE(URL);
//            Log.i(info[0], "Song name");
//            //attempt to connect to web service
//            try {
//                transport.call("http://webservices.jklt/voteForMusic", envelope);
//                Log.i(info[0], "Song name");
//            }
//            catch (IOException e) {
//                e.printStackTrace();
//            }
//            catch (XmlPullParserException e) {
//                e.printStackTrace();
//            }
//
//            //bodyIn is the body object received with this envelope
//            if (envelope.bodyIn != null) {
//                //getProperty() Returns a specific property at a certain index.
//                //Toast.makeText(getBaseContext(), "call came back from server", Toast.LENGTH_SHORT).show();
////                    SoapObject resultSOAP = (SoapObject) ((SoapObject) envelope.bodyIn).getProperty(0);
//                resp = envelope.getResponse().toString();
////                    resp = ((SoapFault) envelope.bodyIn).faultstring;
////                    Log.i("", resp);
////                    resp = (String)resultSOAP.getProperty(0);
////                    resp = response.getProperty(0).toString();
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            resp = e.getMessage();
//        }
//        return resp;
//    }
//
//    //called before connection to web service is made
////    @Override
//    protected void onPreExecute() {
//        // Things to be done before execution of long running operation.
//    }
//
//    //called while connection to web service is happening
////    @Override
//    protected void onProgressUpdate(String... params) {
//        // Things to be done while execution of long running operation is in progress
////        TextView vote = (TextView) findViewById(R.id.song_name);
////        vote.setText(params[0]);
//    }
//
//    //called after connection to web service is complete
////    @Override
//    protected void onPostExecute(String result) {
//        // execution of result of Long time consuming operation
////        TextView vote = (TextView) findViewById(R.id.song_name);
////        vote.setText(result);
//    }
//}

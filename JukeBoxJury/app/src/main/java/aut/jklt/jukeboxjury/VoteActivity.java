package aut.jklt.jukeboxjury;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Toast;
import android.widget.Button;
import android.view.View;


public class VoteActivity extends ActionBarActivity {
    private final int ONE_VOTE = 1;     //the vote that is returned to .PlaylistActivity(.MainActivity in this test)

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
        })
    ;}

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

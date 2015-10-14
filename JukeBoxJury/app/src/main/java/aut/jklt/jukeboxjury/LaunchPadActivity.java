package aut.jklt.jukeboxjury;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class LaunchPadActivity extends AppCompatActivity implements View.OnClickListener {

    private final int VOTE = 1;
    private String song_title = "Thriller";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_pad);

        Button leaderBoard = (Button)findViewById(R.id.leader_board_button);
        Button playList = (Button)findViewById(R.id.playlist_button);
        ImageButton imageButton = (ImageButton)findViewById(R.id.events_button);
        leaderBoard.setOnClickListener(this);
        playList.setOnClickListener(this);
        imageButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        //Toast.makeText(getBaseContext(), "Moving on to VoteActivity", Toast.LENGTH_LONG).show();
        switch (v.getId()) {

            case R.id.leader_board_button:
                Intent leaderBoard = new Intent(getBaseContext(), LeaderboardActivity.class);
                startActivity(leaderBoard);
                break;

            case R.id.playlist_button:
                Intent playList = new Intent(getBaseContext(), VoteActivity.class);
                startActivity(playList);
                break;

            case R.id.events_button:
                Intent events = new Intent(getBaseContext(), VoteActivity.class);
                startActivity(events);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_launch_pad, menu);
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

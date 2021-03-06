package aut.jklt.jukeboxjury;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class PlayListActivity extends AppCompatActivity {

    private TextView songTitle;
    private TextView voteCount;
    private final int VOTE = 1;
    private String[] songs = {"Thriller"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);

        //header elements
        songTitle = (TextView) findViewById(R.id.song_title);
        voteCount = (TextView) findViewById(R.id.vote_count);
        songTitle.setText("Song Title");
        voteCount.setText("Vote Count");

        //creates custom adapter and sets onClickListener for song items
        ArrayAdapter<String> adapter = new CustomAdapter(this, R.layout.activity_playlist_listrow, songs);
        ListView playList = (ListView) findViewById(R.id.playlist);
        playList.setAdapter(adapter);
        playList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("Song", songs[position]);
                Intent intent = new Intent(getBaseContext(), VoteActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, VOTE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == VOTE) {
            if(resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                String value = bundle.getString("Song");
                Toast.makeText(getBaseContext(), "The song was " + value, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play_list, menu);
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

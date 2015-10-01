package aut.jklt.jukeboxjury;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView;

public class LeaderboardActivity extends Activity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        listView = (ListView)findViewById(R.id.list);

        String[] values = new String[]{"First song,", "Second song", "Third song", "Fourth song", "Fifth song", "Sixth song" };

    }
}



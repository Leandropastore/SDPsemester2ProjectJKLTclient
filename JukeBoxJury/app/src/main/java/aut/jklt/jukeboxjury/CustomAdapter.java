package aut.jklt.jukeboxjury;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Terry Green 0829446 on 28/08/15.
 */

public class CustomAdapter extends ArrayAdapter {
    private Activity activity;
    private final String[] songs;
//    private final String[] prices;
//    private final int[] icon;

    public CustomAdapter(Activity context, int resource, String[] songs) {
        super(context, resource, songs);
        this.activity = context;
        this.songs = songs;
//        this.prices = prices;
//        this.icon = icon_id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        ViewHolder view;
        if(itemView == null)
        {
            // Get a new instance of the row layout view
            LayoutInflater inflater = activity.getLayoutInflater();
            itemView = inflater.inflate(R.layout.activity_playlist_listrow, null);
            // Hold the view objects in an object, that way the don't need to be "rediscovered"
            view = new ViewHolder();
            view.item_name = (TextView) itemView.findViewById(R.id.playlist_column1);
//            view.item_price = (TextView) itemView.findViewById(R.id.price);
//            view.item_logo = (ImageView) itemView.findViewById(R.id.image);
            itemView.setTag(view);
        } else {
            view = (ViewHolder) itemView.getTag();
        }
        /** Set data to your Views. */
        String s = songs[position];
//        String p = prices[position];
//        int i = icon[position];
        view.item_name.setText(s);
//        view.item_price.setText(p);
//        view.item_logo.setImageResource(i);
        return itemView;
    }
    protected static class ViewHolder{
//        protected ImageView item_logo;
        protected TextView item_name;
//        protected TextView item_price;
    }
}

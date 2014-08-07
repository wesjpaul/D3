/*
 *  Author: Wesley Paul
 *  Date: August 07, 2014
 */

package d3.app;

/*
 * This ArrayAdapter is responsible for displaying the Ringtones and Notifications found
 * in the local memory of the device.
 */

import java.io.File;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.app.Activity;
import android.app.Dialog;

import org.apache.commons.lang.WordUtils;

public class RingtoneAdapter extends ArrayAdapter<File>{

    Context context;
    int layoutResourceId;
    File [] data;
    File directory;

    /*
     * Set the context, layout, and data list for the adapter.
     */
    public RingtoneAdapter(Context context, int layoutResourceId, File [] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        directory = Environment.getExternalStorageDirectory();
    }


    /*
     * Creates a row for the ListView given the needed position.
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RingtoneHolder holder;

        if(row == null)
        {
            // Set the layout for the new row.
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            // Create an object to hold the widgets of the row layout.
            holder = new RingtoneHolder();
            holder.gameName = (TextView)row.findViewById(R.id.fileName);
            holder.play = (ImageButton)row.findViewById(R.id.playButton);

            row.setTag(holder);

            // Set an onclick listeners for the two buttons of the row.
            holder.gameName.setOnClickListener(new View.OnClickListener() {

                // When the text area is clicked a popup is created which assists in the manipulation of this file.
                @Override
                public void onClick(View arg0) {
                    //showPopup((Activity)context, p);
                    Dialog dialog = new Dialog(context);
                    LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View v = li.inflate(R.layout.ringtone_popup, null, false);
                    dialog.setContentView(v);
                    dialog.setTitle(WordUtils.capitalize(data[position].getName().replace("_", " ").replace(".mp3", "")));
                    dialog.show();
                }
            });
            holder.play.setOnClickListener(new View.OnClickListener() {

                // When the 'play' button is pressed a MediaPlayer object is created and used to play the file.
                @Override
                public void onClick(View arg0) {
                    MediaPlayer mp = new MediaPlayer();

                    try {
                        System.out.println(data[position].getPath());
                        mp.setDataSource(data[position].getPath());
                        mp.prepare();
                        mp.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        else
        {
            holder = (RingtoneHolder)row.getTag();
        }

        // Populate the text area of the row.
        holder.gameName.setText(WordUtils.capitalize(data[position].getName().replace("_", " ").replace(".mp3", "")));

        return row;
    }

    // Simple class used to represent the widgets of each list entry.
    static class RingtoneHolder
    {
        TextView gameName;
        ImageButton play;
    }
}
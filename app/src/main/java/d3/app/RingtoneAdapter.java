package d3.app;

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

    public RingtoneAdapter(Context context, int layoutResourceId, File [] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        directory = Environment.getExternalStorageDirectory();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RingtoneHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RingtoneHolder();
            holder.gameName = (TextView)row.findViewById(R.id.fileName);
            holder.play = (ImageButton)row.findViewById(R.id.playButton);

            row.setTag(holder);

            holder.gameName.setOnClickListener(new View.OnClickListener() {

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


        holder.gameName.setText(WordUtils.capitalize(data[position].getName().replace("_", " ").replace(".mp3", "")));

        return row;
    }

    static class RingtoneHolder
    {
        TextView gameName;
        ImageButton play;
    }
}
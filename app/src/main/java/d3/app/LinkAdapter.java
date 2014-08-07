/*
 *  Author: Wesley Paul
 *  Date: August 07, 2014
 */

package d3.app;

/*
 * This ArrayAdapter is responsible for displaying the Ringtones and Notifications found
 * on websites input by the user.
 */

import java.util.List;

import org.jsoup.nodes.Element;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.app.Activity;
import android.app.Dialog;

public class LinkAdapter extends ArrayAdapter<Element>{

    Context context; 
    int layoutResourceId;    
    List<Element> data;
    //File directory;


    /*
     * Set the context, layout, and data list for the adapter.
     */
	private static String notification = "/media/audio/notifications";
	private static String ringtone = "/Ringtones"; 
    
    public LinkAdapter(Context context, int layoutResourceId, List<Element> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        //directory = Environment.getExternalStorageDirectory();
    }


    /*
     * Creates a row for the ListView given the needed position.
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LinkHolder holder;
        
        if(row == null)
        {
            // Set the layout for the new row
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            // Create an object to hold the widgets of the row layout
            holder = new LinkHolder();
            holder.linkName = (TextView)row.findViewById(R.id.fileName);
            holder.play = (ImageButton)row.findViewById(R.id.playButton);
            holder.download = (ImageButton)row.findViewById(R.id.downloadButton);
            
            row.setTag(holder);
        }
        else
        {
            holder = (LinkHolder)row.getTag();
        }

        // Set the onclick listener for the play button.  This will play the audio using the MediaPlayer API.
        holder.play.setOnClickListener(new View.OnClickListener() {
          	 
            @Override
            public void onClick(View arg0) {
                MediaPlayer mp = new MediaPlayer();
                try {
                	System.out.println(position);
                    mp.setDataSource(data.get(position).attr("abs:href"));
                    mp.prepare();
                    mp.start();
                    // reset the media player once the file is finished playing.
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp) {
                        	System.out.println(data.get(position).attr("abs:href"));
                            mp.reset();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Set the onclick listener for the Download button.
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	//showPopup((Activity)context, p);
                // Create a new dialog popup and apply the layout to it.
            	Dialog dialog = new Dialog(context);
            	LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            	View v = li.inflate(R.layout.download_popup, null, false);
            	dialog.setContentView(v);
            	dialog.setTitle(parseFileName(data.get(position).attr("abs:href")));

                // Create an object for the text field in the popup layout and set the title to the current files name.
            	final EditText title = (EditText) dialog.findViewById(R.id.title);
            	title.setText(parseFileName(data.get(position).attr("abs:href")).replaceAll("_", " ").replaceAll(".mp3", ""), TextView.BufferType.EDITABLE);

                // Create an object for the ringtone download button and apply an onclick listener.
            	Button ringtoneButton = (Button) dialog.findViewById(R.id.ringtone);
            	ringtoneButton.setOnClickListener(new View.OnClickListener() 
            	{
					@Override
					public void onClick(View arg0) {
                        // Call the Downloader class to download the file into the Ringtone directory.
						Downloader.downloadFromUrl(context, data.get(position).attr("abs:href"), ringtone, title.getText().toString());
					}
                      	});

                // Create an object for the notification download button and apply an onclick listener.
            	Button notificationButton = (Button) dialog.findViewById(R.id.notification);
            	notificationButton.setOnClickListener(new View.OnClickListener() 
            	{
					@Override
					public void onClick(View arg0) {
                        // Call the Downloader class to download the file into the Notification directory.
						Downloader.downloadFromUrl(context, data.get(position).attr("abs:href"), notification, title.getText().toString());
					}
                      	});

                // Display the popup to the user.
            	dialog.show();
            }
        });

        // Populate the text field for the row.
        holder.linkName.setText(parseFileName(data.get(position).attr("abs:href")));
        
        return row;
    }

    // Strips the file name from a path.
    public String parseFileName(String path){
    	String [] tmp = path.split("/");
    	return tmp[tmp.length - 1];
    }

    // Simple class used to represent the widgets of each list entry.
    static class LinkHolder
    {
        TextView linkName;
        ImageButton play;
        ImageButton download;
    }
}
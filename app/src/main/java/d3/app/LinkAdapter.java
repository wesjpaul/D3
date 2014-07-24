/*
    LinkAdapter.java
    responsible for blah blah blah
 */


package d3.app;

import java.io.File;
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
    
	private static String notification = "/media/audio/notifications";
	private static String ringtone = "/Ringtones"; 
    
    public LinkAdapter(Context context, int layoutResourceId, List<Element> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        //directory = Environment.getExternalStorageDirectory();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LinkHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new LinkHolder();
            holder.linkName = (TextView)row.findViewById(R.id.fileName);
            holder.play = (ImageButton)row.findViewById(R.id.playButton);
            holder.download = (ImageButton)row.findViewById(R.id.downloadButton);
            
            row.setTag(holder);//
            //hghjg
            
        }
        else
        {
            holder = (LinkHolder)row.getTag();
        }
        
        
        holder.play.setOnClickListener(new View.OnClickListener() {
          	 
            @Override
            public void onClick(View arg0) {
                MediaPlayer mp = new MediaPlayer();
                try {
                	System.out.println(position);
                    mp.setDataSource(data.get(position).attr("abs:href"));
                    mp.prepare();
                    mp.start();
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
        
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	//showPopup((Activity)context, p);
            	Dialog dialog = new Dialog(context);
            	LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            	View v = li.inflate(R.layout.download_popup, null, false);
            	dialog.setContentView(v);
            	dialog.setTitle(parseFileName(data.get(position).attr("abs:href")));
            	
            	final EditText title = (EditText) dialog.findViewById(R.id.title);
            	title.setText(parseFileName(data.get(position).attr("abs:href")).replaceAll("_", " ").replaceAll(".mp3", ""), TextView.BufferType.EDITABLE);
            	
            	Button ringtoneButton = (Button) dialog.findViewById(R.id.ringtone);
            	ringtoneButton.setOnClickListener(new View.OnClickListener() 
            	{
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Downloader.downloadFromUrl(context, data.get(position).attr("abs:href"), ringtone, title.getText().toString());
					}
            	});
            	
            	Button notificationButton = (Button) dialog.findViewById(R.id.notification);
            	notificationButton.setOnClickListener(new View.OnClickListener() 
            	{
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Downloader.downloadFromUrl(context, data.get(position).attr("abs:href"), notification, title.getText().toString());
					}
            	});
            	
            	dialog.show();
            }
        });
        
        holder.linkName.setText(parseFileName(data.get(position).attr("abs:href")));
        
        return row;
    }
    
    public String parseFileName(String path){
    	String [] tmp = path.split("/");
    	return tmp[tmp.length - 1];
    }
    
    static class LinkHolder
    {
        TextView linkName;
        ImageButton play;
        ImageButton download;
    }
}
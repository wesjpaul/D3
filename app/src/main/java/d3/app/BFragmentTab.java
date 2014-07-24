package d3.app;


import android.app.ListFragment;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ListView;

import java.io.File;

public class BFragmentTab extends ListFragment 
{
	private static String ringtonePath = "/Ringtones";
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);

	    try{
		    File directory = Environment.getExternalStorageDirectory();
		    File folder = new File( directory + ringtonePath );
		    //String [] fileNames = folder.list();
	        //File[] fileList = folder.listFiles();

		    RingtoneAdapter adapter = new RingtoneAdapter(getActivity(), R.layout.ringtone_list, folder.listFiles());
		    setListAdapter(adapter);
	    }catch(Exception e){
	    	System.out.println(e);
	    }
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// Do something with the data
	
	}
}
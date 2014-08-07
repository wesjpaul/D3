/*
 *  Author: Wesley Paul
 *  Date: August 07, 2014
 */

package d3.app;

/*
 * BFragmentTab.java is a responsible for the ringtone management UI. It uses
 * the ListFragment class with the default layout.
 */

import android.app.ListFragment;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ListView;
import java.io.File;

public class BFragmentTab extends ListFragment 
{
	private static String ringtonePath = "/Ringtones";

   /*
    * Each time the Ringtones tab is opened the Ringtones folder is added to a File
    * object and sent to the RingtoneAdapter. The ringtone management UI uses a list
    * adapter to display and manipulate ringtones on the device.
    */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);

	    try{
            // Create an object to represent the ringtone folder
		    File directory = Environment.getExternalStorageDirectory();
		    File folder = new File( directory + ringtonePath );
		    //String [] fileNames = folder.list();
	        //File[] fileList = folder.listFiles();

            // Create a new RingtoneAdapter using the ringtone_list layout and a list of the files
            // in the ringtones folder.
		    RingtoneAdapter adapter = new RingtoneAdapter(getActivity(), R.layout.ringtone_list, folder.listFiles());
            // Set the new adapter to the ListView in the fragment UI.
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
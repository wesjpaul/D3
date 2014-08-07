/*
 *  Author: Wesley Paul
 *  Date: August 07, 2014
 */

package d3.app;

/*
 * CFragmentTab.java is a responsible for the notifications management UI. It uses
 * the ListFragment class with the default layout.
 */

import java.io.File;
import android.app.ListFragment;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ListView;

public class CFragmentTab extends ListFragment 
{
	private static String ringtonePath = "/media/audio/notifications";

   /*
    * Each time the Notifications tab is opened the Notifications folder is added to a File
    * object and sent to the RingtoneAdapter. The notifications management UI uses a list
    * adapter to display and manipulate notifications on the device.
    */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);

	    try{
            // Create an object to represent the notifications folder
		    File directory = Environment.getExternalStorageDirectory();
		    File folder = new File( directory + ringtonePath );
		    //String [] fileNames = folder.list();
	        //File[] fileList = folder.listFiles();

            // Create a new RingtoneAdapter using the ringtone_list layout and a list of the files
            // in the notifications folder.
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
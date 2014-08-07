/*
 *  Author: Wesley Paul
 *  Date: August 07, 2014
 */

package d3.app;

/*
 * The Downloader.java class is responsible for downloading files that have been selected
 * by the user.
 *
 */

import java.util.List;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.content.pm.ResolveInfo;

public class Downloader {

    // These locations are not universal.
	//private static String notification = "/media/audio/notifications";
	//private static String ringtone = "/Ringtones";
	
	//public void Dowloader(){
	//
	//}
	
	// Checks to make sure it is safe to begin downloading
	public static boolean isDownloadManagerAvailable(Context context) {
	    try {
	        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
	            return false;
	        }
	        Intent intent = new Intent(Intent.ACTION_MAIN);
	        intent.addCategory(Intent.CATEGORY_LAUNCHER);
	        intent.setClassName("com.android.providers.downloads.ui", "com.android.providers.downloads.ui.DownloadList");
	        List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent,
	                PackageManager.MATCH_DEFAULT_ONLY);
	        return list.size() > 0;
	    } catch (Exception e) {
            System.out.println(e);
	        return false;
	    }
	}

    /*
     * Downloads a the file found at a given URL by creating a request and queueing it within the
     * android DownloadManager.
     */
	public static void downloadFromUrl(Context context, String url, String path, String title){
		if(isDownloadManagerAvailable(context)){
			try{
                // Create a request to download the file located in the given URL
				DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
				request.setDescription("A new ringtone!");
                // Set the title to the user specified title.
				request.setTitle(title);
				// in order for this if to run, you must use the android 3.2 to compile your app
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				    request.allowScanningByMediaScanner();
				    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
				}
                // Set the download location of the new file.
				request.setDestinationInExternalPublicDir(path, title.concat(".mp3").replace(" ", "_"));
		
				// get download service and enqueue file
				DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
				manager.enqueue(request);
			}catch(Exception e){
                System.out.println(e);
			}
		}
	}

}

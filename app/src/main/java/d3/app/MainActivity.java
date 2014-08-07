/*
 *  Author: Wesley Paul
 *  Date: August 07, 2014
 */

package d3.app;

/*
 * This class is run on startup and is responsible for setting up the main UI elements
 */

import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.Menu;
import android.view.Window;

public class MainActivity extends Activity {

    // Run on startup.  Setup UI. Creates the tabs used by the user for navigation.
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Remove title bar
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

		setContentView(R.layout.activity_main);

	    ActionBar bar = getActionBar();
        if(bar == null){
            System.out.println("hello");
        }
        // Hide the Title and Home bar
		bar.setDisplayShowTitleEnabled(false);
		bar.setDisplayShowHomeEnabled(false);
        // Show the Tab bar.
	    bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Initiate and create the tabs.
	    ActionBar.Tab tabA = bar.newTab().setText("Download");
	    ActionBar.Tab tabB = bar.newTab().setText("Ringtones");
	    ActionBar.Tab tabC = bar.newTab().setText("Notifications");

	    Fragment fragmentA = new AFragmentTab();
	    Fragment fragmentB = new BFragmentTab();
	    Fragment fragmentC = new CFragmentTab();

        // Set a listener for each tab.
	    tabA.setTabListener(new MyTabsListener(fragmentA));
	    tabB.setTabListener(new MyTabsListener(fragmentB));
	    tabC.setTabListener(new MyTabsListener(fragmentC));

        // Add the tabs to the bar.
	    bar.addTab(tabA);
	    bar.addTab(tabB);
	    bar.addTab(tabC);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

    // Manages the tabs when navigated by the user.
	protected class MyTabsListener implements ActionBar.TabListener {

	    private Fragment fragment;

	    public MyTabsListener(Fragment fragment) {
	        this.fragment = fragment;
	    }

	    public void onTabReselected(Tab tab, FragmentTransaction ft) {
	    }

	    public void onTabSelected(Tab tab, FragmentTransaction ft) {
	        ft.add(R.id.fragment_container, fragment, null);
	    }

	    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	        ft.remove(fragment);
	    }
	}

}

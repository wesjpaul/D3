package d3.app;

/*
    Fragment A holds the file downloading tab UI.

 */



import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Element;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

public class AFragmentTab extends Fragment
{
	EditText parseText;
	Button parseButton;
	LinearLayout downloadUI;
	ListView songList;
	View v;
	
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    v = inflater.inflate(R.layout.fragment_a, container, false);
	  
    setupUI();
    
    return v;
  }
  
  public void setupUI(){
	    parseButton = (Button) v.findViewById(R.id.parse_uri);
	    parseText = (EditText) v.findViewById(R.id.uri);
	    downloadUI = (LinearLayout) v.findViewById(R.id.download_ui);
	    songList = (ListView) v.findViewById(R.id.song_list);
	    
	    songList.setVisibility(View.INVISIBLE);
	    
	    parseButton.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View arg0) {
	        	try {
					parseURI(parseText.getText().toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    });
	  
  }
  
  public void populateSongList(List<Element> links){
	  downloadUI.setVisibility(View.INVISIBLE);
	  songList.setVisibility(View.VISIBLE);
	  
	  LinkAdapter adapter = new LinkAdapter(getActivity(), R.layout.link_list, links);
	  songList.setAdapter(adapter);
  }
  
  public void parseURI(String uri) throws IOException{
	  WebsiteParser parser = new WebsiteParser(uri, this);
	  parser.parse();
	  //System.out.println(uri);
  }
}
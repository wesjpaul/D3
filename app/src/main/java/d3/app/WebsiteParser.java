/*
 *  Author: Wesley Paul
 *  Date: August 07, 2014
 */

package d3.app;

/*
 * The WebsiteParser class is responsible for parsing websites and extracting audio files.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;

public class WebsiteParser {

    private String uri;
    private AFragmentTab fragment;

    public WebsiteParser(String uri, AFragmentTab fragment){
        this.uri = uri;
        this.fragment = fragment;
    }

    // Run GetHTML to retrieve website HTML.
    public void parse() throws IOException{
        GetHTML html = new GetHTML(uri, this);
        html.execute();
        //System.out.println(document.toString());
    }

    // Parse a Document containing HTML for audio files.
    // TODO: update to include more than just .mp3 audio.
    public void parseForFiles(Document html){
        if(html == null){
            System.out.println("NULL!");
        }
        // Filter all the links out of the HTML and create a list.
        Elements links = html.select("a[href]");
        List<Element> files = new ArrayList<Element>();

        // Find all the links that contain mp3 files.
        for (Element link : links) {
            //print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
            if(link.attr("abs:href").endsWith(".mp3")){
                files.add(link);
            }
        }

        // Send the list of audio files back to the download fragment to be displayed to the user.
        fragment.populateSongList(files);
    }

}

// GetHTML is an AsyncTask used to download a websites HTML in the background.
class GetHTML extends AsyncTask<String, Void, String> {
    Document html;
    WebsiteParser obj;
    String uri;

    public GetHTML(String uri, WebsiteParser obj){
        this.uri = uri;
        this.obj = obj;
    }

    // Connect to the given website and download its content.
    @Override
    protected String doInBackground(String... params) {
        try {
            this.html = Jsoup.connect(this.uri).get();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    //Once the task is complete begin parsing the downloaded HTML
    @Override
    protected void onPostExecute(String result) {
        obj.parseForFiles(this.html);
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }

}
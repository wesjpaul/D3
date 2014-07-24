package d3.app;

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

    public void parse() throws IOException{
        GetHTML html = new GetHTML(uri, this);
        html.execute();
        //System.out.println(document.toString());
    }

    public void parseForFiles(Document html){
        if(html == null){
            System.out.println("NULL!");
        }
        Elements links = html.select("a[href]");
        List<Element> files = new ArrayList<Element>();

        for (Element link : links) {
            //print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
            if(link.attr("abs:href").toString().endsWith(".mp3")){
                files.add(link);
            }
        }

        fragment.populateSongList(files);
    }

}


class GetHTML extends AsyncTask<String, Void, String> {
    Document html;
    WebsiteParser obj;
    String uri;

    public GetHTML(String uri, WebsiteParser obj){
        this.uri = uri;
        this.obj = obj;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            this.html = Jsoup.connect(this.uri).get();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

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
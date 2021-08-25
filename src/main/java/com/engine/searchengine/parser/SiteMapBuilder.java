package com.engine.searchengine.parser;

import com.engine.searchengine.parser.entity.Page;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class SiteMapBuilder extends RecursiveTask<Page> {

    Connection.Response response = null;

    private List<SiteMapBuilder> taskList = new ArrayList<>();
    private List<String> urlList = new ArrayList<>();
    private List<Page> pages = new ArrayList<>();

    String url;
    private static int counter = 0;

    public SiteMapBuilder(String url){
        this.url = url;
    }

    @Override
    protected Page compute() {
        try {
            response = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .execute();
            ArrayList<String> media = getUrls();

            for (String src : media) {
                if (counter > 500) {
                    break;
                }
                ++counter;
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (!urlList.contains(src) && !src.isEmpty() && !src.contains("#") && src.startsWith(url) && !src.equals(url)) {
                    SiteMapBuilder builder = new SiteMapBuilder(src);
                    urlList.add(src);
                    taskList.add(builder);
                    pages.add(generatePage(src));
                    builder.fork();
                }
            }
            for (SiteMapBuilder builder : taskList) {
                pages.add(builder.join());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Page();
    }

    private Page generatePage(String url) {
        Page page = new Page();
        page.setUrl(url);
        page.setCode(response.statusCode());
        page.setContent(response.body());
        return page;
    }

    private int getSitemapStatus() {
        int statusCode = response.statusCode();
        return statusCode;
    }

    private ArrayList<String> getUrls() throws IOException{
        ArrayList<String> urls = new ArrayList<String>();
        Document doc = response.parse();
        for (Element url : doc.select("a[href]")) {
            urls.add(url.absUrl("href"));
        }
        return urls;
    }

    public List<Page> getPages() {
        return pages;
    }
}

package com.engine.searchengine.parser;

import com.engine.searchengine.parser.repo.PagesRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.ForkJoinPool;

@Configuration
public class Parser {
    private final static String url = "http://www.playback.ru/";
    private final PagesRepo pagesRepo;

    @Bean
    public void parse() {
       SiteMapBuilder builder = new SiteMapBuilder(url);
       new ForkJoinPool().invoke(builder);
       builder.getPages().forEach(pagesRepo::save);
    }

    public Parser(PagesRepo pagesRepo){
        this.pagesRepo = pagesRepo;
    }


}

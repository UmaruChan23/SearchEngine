package com.engine.searchengine.parser.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "pages", indexes = {@Index(columnList = "url", name = "url")})
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String url;

    private int code;

    @Column(length = 65535, columnDefinition = "text")
    private String content;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

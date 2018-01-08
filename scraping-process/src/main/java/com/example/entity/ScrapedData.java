package com.example.entity;

/**
 * Created by shurakaw on 2017/12/09.
 */
public class ScrapedData {
  public Integer id;
  public Integer session_id;
  public Integer status;
  public String url;
  public String title;
  public String contents;
  public void setData(Integer id, Integer session_id, String url, String contents) {
    this.id = id;
    this.session_id = session_id;
    this.url = url;
    this.contents = contents;
  }
}


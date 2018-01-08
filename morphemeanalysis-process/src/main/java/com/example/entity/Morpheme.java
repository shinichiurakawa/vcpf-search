package com.example.entity;

/**
 * Created by shurakaw on 2017/12/09.
 */
public class Morpheme {
  public Integer id;
  public String word;
  public Integer castel_part_id;
  public Integer session_id;
  public Integer scraping_id;
  public Integer count;

  public void setDicData(Integer id, String word, Integer castel_part_id ) {
    this.id = id;
    this.word = word;
    this.castel_part_id = castel_part_id;
  }
  public void setMorpheme(Integer id, String word, Integer castel_part_id, Integer session_id, Integer scraping_id, Integer count) {
    setDicData(id,word,castel_part_id);
    this.session_id = session_id;
    this.scraping_id = scraping_id;
    this.count = count;
  }
}


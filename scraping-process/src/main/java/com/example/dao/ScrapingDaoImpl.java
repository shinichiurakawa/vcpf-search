package com.example.dao;

import com.example.entity.ScrapedData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;




/**
 * Created by shurakaw on 2017/12/09.
 */

@Component
public class ScrapingDaoImpl implements ScrapingDao {


  @Autowired
  JdbcTemplate jdbcTemplate;

  public List<ScrapedData> getScrapingURL(Integer session_id) {
    List<Map<String, Object>> ret;
    List<ScrapedData> data_list = new ArrayList<>();
    try {
      ret = jdbcTemplate.queryForList(
          "SELECT id, url FROM SCRAPING WHERE session_id = "+ session_id.toString() );
    } catch (EmptyResultDataAccessException ex) {
      ex.printStackTrace();
      return data_list;
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
    for (Map<String,Object> map : ret) {
      ScrapedData scrapedData = new ScrapedData();
      scrapedData.session_id = session_id;
      scrapedData.id = (Integer) map.get("id");
      scrapedData.url = (String) map.get("url");
      data_list.add(scrapedData);
    }
    return data_list;
  }

  public void setScrapedDataList(List<ScrapedData> scrapedDataList) {
    for (ScrapedData scrapedData : scrapedDataList) {
      try {
        if (scrapedData.status.equals(200)) {
          // 正常にコンテンツ取得できたものはSTATUSに"200"を設定
          String sql = "UPDATE SCRAPING SET TITLE = ?, CONTENTS = ?, STATUS = ? WHERE ID = ? AND SESSION_ID = ?";
          jdbcTemplate.update(sql, new Object[] { scrapedData.title , scrapedData.contents, scrapedData.status, scrapedData.id, scrapedData.session_id});
        } else {
          // コンテンツ取得でなかったものはSTATUSに"500"を設定（とりあえずw）
          String sql = "UPDATE SCRAPING SET STATUS = ? WHERE ID = ? AND SESSION_ID = ?";
          jdbcTemplate.update(sql, new Object[] { scrapedData.status , scrapedData.id, scrapedData.session_id});
        }
      } catch (Exception e) {
        e.printStackTrace();
        return;
      }
    }
    return;
  }
}


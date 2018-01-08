package com.example.dao;

import com.example.entity.ScrapedData;
import java.util.List;


/**
 * Created by shurakaw on 2017/12/09.
 */
public interface ScrapingDao {
  List<ScrapedData> getScrapingURL(Integer session_id);
  void setScrapedDataList(List<ScrapedData> scrapedDataList);
}


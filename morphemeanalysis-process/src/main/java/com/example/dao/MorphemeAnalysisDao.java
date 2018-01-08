package com.example.dao;

import com.example.entity.DicData;
import com.example.entity.Morpheme;
import com.example.entity.ScrapedData;
import java.util.List;


/**
 * Created by shurakaw on 2017/12/09.
 */
public interface MorphemeAnalysisDao {
  List<ScrapedData> getScrapedData(Integer session_id);
//  Boolean addDicData(List<DicData> data_list);
  Boolean addDicData(List<Morpheme> data_list);
  Boolean addSessionMorpheme(List<Morpheme> data_list);
  void initSessionDic();
  void initScrapingSeq();
}


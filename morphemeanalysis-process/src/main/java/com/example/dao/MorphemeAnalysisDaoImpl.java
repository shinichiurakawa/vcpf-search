package com.example.dao;

import com.example.entity.DicData;
import com.example.entity.Morpheme;
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
public class MorphemeAnalysisDaoImpl implements MorphemeAnalysisDao {


  @Autowired
  JdbcTemplate jdbcTemplate;

  public List<ScrapedData> getScrapedData(Integer session_id) {
    List<Map<String, Object>> ret;
    List<ScrapedData> data_list = new ArrayList<>();
    try {
      ret = jdbcTemplate.queryForList(
          "SELECT id, session_id, url, contents  FROM scraping where session_id = ? AND STATUS = 200",session_id);
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
    for (Map<String,Object> map : ret) {
      ScrapedData data = new ScrapedData();
      data.setData((Integer) map.get("id"), (Integer) map.get("session_id"), (String) map.get("url"), (String) map.get("contents"));
      data_list.add(data);
    }
    return data_list;
  }

  //    public Boolean addDicData(List<DicData> data_list) {
  public Boolean addDicData(List<Morpheme> data_list) {
    Map<String, Object> ret;
    for (Morpheme data : data_list) {
      try {
        ret = jdbcTemplate.queryForMap("SELECT id, word, castel_part_id FROM DICTIONARY WHERE word = ?", data.word);
        data.id = (Integer)ret.get("id");
        data.castel_part_id = (Integer)ret.get("castel_part_id");
      } catch (EmptyResultDataAccessException e) {
        Integer id = jdbcTemplate.queryForObject("SELECT nextval('DICTIONARY_ID_SEQ')",Integer.class);
        String sql = "INSERT INTO DICTIONARY (id,word,castel_part_id) VALUES(?,?,?)";
        jdbcTemplate.update(sql, new Object[] {id,data.word, data.castel_part_id});
        data.id = id;
      } catch (Exception e) {
        e.printStackTrace();
        return Boolean.FALSE;
      }
    }
    return Boolean.TRUE;
  }

  public Boolean addSessionMorpheme(List<Morpheme> morphemes) {
    for (Morpheme morpheme: morphemes) {
      Integer id;
      try {
        /*
        Map<String, Object> ret;
        ret = jdbcTemplate.queryForMap("SELECT MORPHEME_ID FROM MORPHEMEANALYSIS WHERE word = ?",morpheme.word);
        id = (Integer)ret.get("MORPHEME_ID");
        */
        List<Map<String, Object>> ret;
        ret = jdbcTemplate.queryForList("SELECT MORPHEME_ID FROM MORPHEMEANALYSIS WHERE word = ?",morpheme.word);
        id = (Integer)ret.get(0).get("morpheme_id");
      //} catch (EmptyResultDataAccessException e) {
      } catch (IndexOutOfBoundsException e) {
        id = jdbcTemplate.queryForObject("SELECT nextval('MORPHEME_ID_SEQ')",Integer.class);
      } catch (Exception e) {
        e.printStackTrace();
        return Boolean.FALSE;
      }

      try {
        String sql = "INSERT INTO MORPHEMEANALYSIS(morpheme_id, session_id, scraping_id, dictionary_id, word, count, castel_part_id) VALUES(?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, new Object[] {id,morpheme.session_id, morpheme.scraping_id, morpheme.id, morpheme.word, morpheme.count, morpheme.castel_part_id});
      } catch (Exception e) {
        e.printStackTrace();
        return Boolean.FALSE;
      }
    }
    return Boolean.TRUE;
  }

  public void initScrapingSeq() {
    try {
      Boolean ret;
      ret = jdbcTemplate.queryForObject("SELECT setval('MORPHEME_ID_SEQ',0)",Boolean.class);
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
  }

  public void initSessionDic() {
    try {
      jdbcTemplate.update("TRUNCATE TABLE MORPHEMEANALYSIS");
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
  }
}


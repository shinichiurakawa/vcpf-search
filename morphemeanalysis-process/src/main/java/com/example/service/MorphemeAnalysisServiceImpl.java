package com.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;
import com.example.dao.MorphemeAnalysisDao;
import com.example.entity.DicData;
import com.example.entity.Morpheme;
import com.example.entity.ScrapedData;
import com.example.entity.VCPFSession;

@EnableBinding({Source.class,Sink.class})
@Component
public class MorphemeAnalysisServiceImpl implements MorphemeAnalysisService {
  private static final Logger logger = LoggerFactory.getLogger(MorphemeAnalysisServiceImpl.class);

  @Autowired
  MorphemeAnalysisDao morphemeAnalysisDao;

  private final Source source;

  public MorphemeAnalysisServiceImpl(Source source) {
    this.source = source;
  }

  @StreamListener(Sink.INPUT)
  public void process(VCPFSession vcpf_session) {
    logger.debug("process() start");
    logger.debug("vcpf_sesssion = " + vcpf_session.toString());
    VCPFSession vcpfSession = new VCPFSession();
    vcpfSession.id = vcpf_session.id;

    morphemeAnalysisDao.initSessionDic(); // セッションのシーケンスを初期化
    morphemeAnalysisDao.initScrapingSeq();
    List<ScrapedData> scrapedDataList = morphemeAnalysisDao.getScrapedData(vcpf_session.id);
    for (ScrapedData data : scrapedDataList) {
      parse(data);
    }
    logger.debug("pre source.output().send(Message...)");
    source.output().send(MessageBuilder.withPayload(vcpf_session.id).build());
    logger.debug("aft source.output().send(Message...)");
//    source.output().send(MessageBuilder.withPayload(vcpfSession).build());
  }

  int word_count(String key, String text) {
    try {
      int count = 0;
      Matcher matcher = Pattern.compile(key).matcher(text);
      while (matcher.find()) {
        count++;
      }
      return count;
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  Boolean word_filter(String target_word) {
    // http://e-words.jp/p/r-ascii.html
    Matcher matcher_a = Pattern.compile(".*([!-/]|[:-@]|[\\[-\\`]|[\\{-\\~]).*").matcher(target_word);
//    Matcher matcher = Pattern.compile("^([!-/]|[:-@])$").matcher(target_word);
    Matcher matcher_b = Pattern.compile("(「|」|【|】|『|』|（|）)").matcher(target_word);
      if (matcher_a.matches()) {
        return true;
      } else if (matcher_b.matches()) {
        return true;
      } else {
        return false;
      }
  }

  public void parse(ScrapedData scrapedData) {
    Integer scrap_id = scrapedData.id;
    Integer session_id = scrapedData.session_id;
    Tokenizer tokenizer = new Tokenizer();
    List<String> token_list = new ArrayList<>();
    List<Morpheme> morphemes = new ArrayList<>();

    try {
      // コンテンツを単語に分解し、名詞のみを抽出する
      for (Token token : tokenizer.tokenize(scrapedData.contents)) {
        if (token.getPartOfSpeechLevel1().equals("名詞") && !this.word_filter(token.getSurface())) {
          token_list.add(token.getSurface());
        }
      }
    } catch (Exception e) {
      //e.getStackTrace();
      e.printStackTrace();
    }


    // 単語リストから重複要素を排除
    Set<String> token_set = new HashSet<>(token_list);
    List<String> uniq_token_list = new ArrayList<>(token_set);
    try {
      int c_th = 3; // 出現回数がc_th以上の単語のみ登録する
      int l_th = 2; // 単語の長さがl_th以上の単語のみ登録する
      // 単語の文字列、各単語がコンテンツに登場する回数を記録する
      for (String token : uniq_token_list) {
        if (token.length() >= l_th) {
          Morpheme morpheme = new Morpheme();
          int count = word_count(token, scrapedData.contents);
          if (count >= c_th) {
            morpheme.setMorpheme(0, token, 0, session_id, scrap_id, count);
            morphemes.add(morpheme);
          }
        }
      }
    } catch (Exception e) {
      //e.getStackTrace();
      e.printStackTrace();
    }

    try {
      // システム全体の辞書登録（この時に、既存の単語はidを取得）
      morphemeAnalysisDao.addDicData(morphemes);

      // セッションのシーケンスを初期化
      morphemeAnalysisDao.initScrapingSeq();

      // 今回の検索における辞書登録
      morphemeAnalysisDao.addSessionMorpheme(morphemes);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

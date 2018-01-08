package com.example.service;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import com.example.dao.ScrapingDao;
import com.example.entity.ScrapedData;
import com.example.entity.VCPFSession;

@Component
@EnableBinding({Source.class,Sink.class})
public class ScrapingServiceImpl implements ScrapingService {

  @Autowired
  ScrapingDao scrapingDao;

  private final Source source;

  public ScrapingServiceImpl(Source source) {
    this.source = source;
  }

  @StreamListener(Sink.INPUT)
  public void process(VCPFSession vcpf_session) {
    VCPFSession vcpfSession = new VCPFSession();
    vcpfSession.id = vcpf_session.id;
    List<ScrapedData> scrapedDataList;
    scrapedDataList = scrapingDao.getScrapingURL(vcpf_session.id); // セッションのシーケンスを初期化
    try {
      for (ScrapedData scrapedData : scrapedDataList) {
        this.scrap(scrapedData);
      }
      // テキストで取得したコンテンツを保存
      scrapingDao.setScrapedDataList(scrapedDataList);

      // 次のプロセスに処理を渡す
      source.output().send(MessageBuilder.withPayload(vcpfSession).build());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void scrap(ScrapedData scrapedData) {

    try {
      Document doc = Jsoup.connect(scrapedData.url).get();

      scrapedData.title = doc.title();
      scrapedData.contents = doc.body().text();
      scrapedData.status = 200;
    } catch (Exception e) {
      scrapedData.status = 500; // error
    }
  }
}

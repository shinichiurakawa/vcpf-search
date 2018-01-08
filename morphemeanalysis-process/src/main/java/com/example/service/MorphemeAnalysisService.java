package com.example.service;


import com.example.entity.ScrapedData;
import com.example.entity.VCPFSession;


/**
 * Created by shurakaw on 2017/12/09.
 */
public interface MorphemeAnalysisService {
  void process(VCPFSession vcpfSession);
  void parse(ScrapedData scrapedData);
}

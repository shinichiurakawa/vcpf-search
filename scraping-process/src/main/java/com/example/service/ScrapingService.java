package com.example.service;


import com.example.entity.ScrapedData;
import com.example.entity.VCPFSession;

import org.springframework.cloud.stream.messaging.Source;


/**
 * Created by shurakaw on 2017/12/09.
 */
public interface ScrapingService {
  void process(VCPFSession vcpfSession);
}

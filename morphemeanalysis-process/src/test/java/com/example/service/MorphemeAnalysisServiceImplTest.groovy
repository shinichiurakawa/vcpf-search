package com.example.service

import com.example.dao.MorphemeAnalysisDaoImpl
import com.example.entity.VCPFSession
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.springframework.cloud.stream.messaging.Source
import spock.lang.Specification

/*
class MorphemeAnalysisServiceImplTest extends spock.lang.Specification {
}
*/

class MorphemeAnalysisServiceImplTest extends Specification {
  def morphemeService

  def setup() {
    String path = "classpath:beans-morphemeanalysis-service.xml";
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext(path);
    morphemeService = applicationContext.getBean(MorphemeAnalysisServiceImpl.class)
    //morphemeService.source = Mock(Source)
  }

  def "正常系1" () {
    given:
    def vcpfSession = new VCPFSession()
    vcpfSession.with {
      id = 1
    }

    when:
    morphemeService.process(vcpfSession);

    then:
    notThrown(Exception)

  }
}

package com.example.service

import com.example.entity.VCPFSession
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext
import spock.lang.Specification
import spock.lang.Unroll
import org.springframework.boot.test.mock.mockito.MockBean

// http://int128.hatenablog.com/entry/2016/12/13/003600

class MorphemeAnalysisServiceImplTest extends Specification {
  static MorphemeAnalysisServiceImpl morphemeService

  def setup() {
    String path = "classpath:beans-morphemeanalysis-service.xml";
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext(path);
    morphemeService = applicationContext.getBean(MorphemeAnalysisServiceImpl.class)
  }

  def "正常系1" () {
    given:
    def vcpfSession = new VCPFSession()
    vcpfSession.with {
      id = 1
    }

    when:
    morphemeService.parse(vcpfSession);

    then:
    thrown(Exception)

  }
}

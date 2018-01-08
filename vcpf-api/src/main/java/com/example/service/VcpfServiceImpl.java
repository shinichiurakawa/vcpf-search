package com.example.service;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * 
 *
 */
@Component
@EnableBinding(Source.class)
public class VcpfServiceImpl implements VcpfService {
  private static final Logger logger = LoggerFactory.getLogger(VcpfServiceImpl.class);

  private final Source source;

  public VcpfServiceImpl(Source source) {
    this.source = source;
  }

  public void Cluster(String keyword) {
    System.out.print("keyword = " + keyword);
  }

  public void Send(Integer id) {
    VCPFSession vcpfSession = new VCPFSession();
    vcpfSession.id = id;
    source.output().send(MessageBuilder.withPayload(vcpfSession).build());
    // Loads data.
    logger.debug("Cluster():start");
  }

  public static class VCPFSession {
    public Integer id;
  }

}


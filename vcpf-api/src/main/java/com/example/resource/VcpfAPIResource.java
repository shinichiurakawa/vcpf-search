package com.example.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.service.VcpfService;


@Component
@Path("/")
public class VcpfAPIResource {
  @Autowired
  VcpfService vcpfService;

  @POST
  @Path("search/{keyword}")
  public String registration( @PathParam("keyword") final String keyword) {

    Integer dummy_session_id = 1;

    this.vcpfService.Cluster(keyword);
    this.vcpfService.Send(dummy_session_id);

    // 処理結果の返却
    return "OK";
  }
}

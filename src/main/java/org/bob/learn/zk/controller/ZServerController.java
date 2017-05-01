package org.bob.learn.zk.controller;

import java.util.List;

import org.bob.learn.zk.common.dto.Result;
import org.bob.learn.zk.common.dto.ZServerDTO;
import org.bob.learn.zk.service.ZServerSerive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ZServerController {
	@Autowired
	private ZServerSerive zServerSerive;

	@RequestMapping(value = "/zserver/add", method = RequestMethod.POST)
	public Result<String> addZServer(@RequestBody ZServerDTO zServer) {
		log.info("/zserver/add-入参-" + zServer.toString());
		Result<String> result = new Result<>();
		try {
			zServer.setId(zServer.getIp()+":"+zServer.getPort());
			zServerSerive.addZServer(zServer);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.setFlag(false);
			result.setMsg(e.getMessage());
		}
		log.info("/zserver/add-出参-"+result.toString());
		return result;
	}
	
	@RequestMapping(value = "/zserver/query", method = RequestMethod.GET)
	public Result<List<ZServerDTO>> queryZServers() {
		
		Result<List<ZServerDTO>> result = new Result<>();
		try {
			result.setContent(zServerSerive.queryZServers());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.setFlag(false);
			result.setMsg(e.getMessage());
		}
		return result;
	}
}

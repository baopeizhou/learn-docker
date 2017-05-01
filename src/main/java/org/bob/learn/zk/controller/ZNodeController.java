package org.bob.learn.zk.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeperMain;
import org.apache.zookeeper.data.Stat;
import org.bob.learn.zk.common.dto.Result;
import org.bob.learn.zk.common.dto.ZNodeDTO;
import org.bob.learn.zk.common.dto.ZServerDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ZNodeController {

	

	@RequestMapping(value = "/znode/add", method = RequestMethod.POST)
	public Result<String> addZNode(@RequestBody ZNodeDTO zNode) {
		Result<String> result = new Result<>();
		try {
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = "/znode/query", method = RequestMethod.GET)
	public Result<String> queryZNode(@RequestBody ZServerDTO zServer) {

		Result<String> result = new Result<>();

		return result;
	}

}

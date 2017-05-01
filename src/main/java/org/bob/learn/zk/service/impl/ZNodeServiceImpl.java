package org.bob.learn.zk.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.client.FourLetterWordMain;
import org.apache.zookeeper.data.Stat;
import org.bob.learn.zk.common.dto.ZNodeDTO;
import org.bob.learn.zk.controller.ZServerController;
import org.bob.learn.zk.service.ZNodeService;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ZNodeServiceImpl implements ZNodeService {

	/**
	 * zookeeper
	 */
	private static final Map<String, ZooKeeper> zkMap = new HashMap<>();

	/**
	 * zookeeper监视器
	 */
	private static final Map<String, Watcher> zkWatcherMap = new HashMap<>();

	@Override
	public void addZNode(ZNodeDTO zNode) {
		try {
		ZooKeeper zk = null;
		if (zkMap.containsKey(zNode.getZserverId())) {
			zk = zkMap.get(zNode.getZserverId());
		} else {
			
				zk = new ZooKeeper(zNode.getZserverId(), 5000, null);
			
			Stat stat = new Stat();
			log.info(new String(zk.getData(zNode.getPath(), true, stat)));
			zkMap.put(zNode.getZserverId(), zk);
		}
		zk.create(zNode.getPath(), zNode.getData().getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	

}

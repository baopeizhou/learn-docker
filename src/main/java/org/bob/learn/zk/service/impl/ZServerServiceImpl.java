package org.bob.learn.zk.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bob.learn.zk.common.dto.ZServerDTO;
import org.bob.learn.zk.common.util.RedisUtils;
import org.bob.learn.zk.service.ZServerSerive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.cache.CacheManager;

@Service
public class ZServerServiceImpl implements ZServerSerive {
	@Autowired
	private CacheManager cacheManager;

	private final static String CACHE_NAME_SPACE = "zserver";

	private final static String CACHE_NAME_ALL_SERVER = "zservers";

	@CachePut(value = CACHE_NAME_SPACE, key = "#zServer.id")
	@Override
	public ZServerDTO addZServer(ZServerDTO zServer) {

		List<ZServerDTO> zServerList = (List<ZServerDTO>)cacheManager.getCache(CACHE_NAME_SPACE).get(CACHE_NAME_ALL_SERVER, List.class);
		if(zServerList !=null)
		{
			zServerList.add(zServer);
		}
		else
		{
			zServerList = new ArrayList<>();
			zServerList.add(zServer);
		}
		cacheManager.getCache(CACHE_NAME_SPACE).putIfAbsent(CACHE_NAME_ALL_SERVER, zServerList);
		
		return zServer;
	}

	@Override
	public List<ZServerDTO> queryZServers() {
		List<ZServerDTO> zServerList =   (List<ZServerDTO>)cacheManager.getCache(CACHE_NAME_SPACE).get(CACHE_NAME_ALL_SERVER, List.class);	
		return zServerList;
	}

}

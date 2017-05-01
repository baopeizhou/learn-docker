package org.bob.learn.zk.service;

import java.util.List;

import org.bob.learn.zk.common.dto.ZServerDTO;

public interface ZServerSerive {
	
	/**
	 * 新增Zookeeper服务器
	 * @param zServer
	 * @return
	 */
	public ZServerDTO addZServer(ZServerDTO zServer);
	
	/**
	 * 查询Zookeeper服务器列表
	 * @return
	 */
	public List<ZServerDTO> queryZServers();

}

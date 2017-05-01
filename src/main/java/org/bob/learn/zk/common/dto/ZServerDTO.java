package org.bob.learn.zk.common.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ZServerDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 表示，ip:port
	 */
	private String id;
	
	/**
	 * 地址
	 */
	private String ip;
	
	/**
	 * 端口
	 */
	private String port;

}

package org.bob.learn.zk.common.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ZNodeDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String zserverId;
	
	private String path;
	
	private String data;

}

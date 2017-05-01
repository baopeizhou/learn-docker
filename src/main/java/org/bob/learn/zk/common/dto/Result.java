package org.bob.learn.zk.common.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class Result<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private boolean flag = true;
	
	private String msg;
	
	private T content;

}

package com.excepiton;

public class CaiPiaoException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

    public CaiPiaoException(String msg) {
        super(msg);
    }

    public CaiPiaoException(String msg, Throwable e) {
        super(msg, e);
    }
}

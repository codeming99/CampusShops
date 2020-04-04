package com.wym.o2o.exceptions;

public class ShopOperationException extends RuntimeException{

    private static final long serialVersionUID = 1172563718599527969L;

    public ShopOperationException(String msg){
        super(msg);
    }
}

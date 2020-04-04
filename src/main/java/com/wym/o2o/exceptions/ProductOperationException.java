package com.wym.o2o.exceptions;

public class ProductOperationException extends Exception {
    private static final long serialVersionUID = 1182533718599527969L;

    public ProductOperationException(String msg){
        super(msg);
    }
}

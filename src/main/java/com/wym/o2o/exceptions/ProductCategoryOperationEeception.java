package com.wym.o2o.exceptions;

public class ProductCategoryOperationEeception extends RuntimeException {

    private static final long serialVersionUID = 1182563718599527969L;

    public ProductCategoryOperationEeception(String msg){
        super(msg);
    }
}

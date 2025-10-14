package com.ahmad.products.exception;

public class ProductException extends RuntimeException{
    public ProductException(RuntimeException cause){
        super(cause);
    }
}

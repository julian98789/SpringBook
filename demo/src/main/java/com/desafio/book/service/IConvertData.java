package com.desafio.book.service;

public interface IConvertData {
    <T> T getData(String json, Class<T> clase);
}

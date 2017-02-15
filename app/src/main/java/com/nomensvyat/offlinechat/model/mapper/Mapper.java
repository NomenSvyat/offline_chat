package com.nomensvyat.offlinechat.model.mapper;

public interface Mapper<T, R> {
    R map(T source);
}

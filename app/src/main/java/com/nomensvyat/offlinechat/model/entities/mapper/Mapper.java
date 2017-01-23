package com.nomensvyat.offlinechat.model.entities.mapper;

public interface Mapper<T, R> {
    R map(T source);
}

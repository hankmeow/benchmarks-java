package com.hankmew.benchmarks.middleware.mysql.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface KVConfigMapper {

    @Select("select `value` from tb_kv_config where id=#{id}")
    String selectOneById(@Param("id") Long id);

}

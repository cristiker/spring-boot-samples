package com.cristik.boot.application.dao;

import com.cristik.boot.application.entity.po.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author cristik
 */
@Mapper
public interface CityMapper {

    @Select("SELECT * FROM CITY WHERE state = #{state}")
    City findByState(@Param("state") String state);

}
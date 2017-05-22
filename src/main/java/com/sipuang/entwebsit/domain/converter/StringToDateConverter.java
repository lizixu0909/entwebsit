package com.sipuang.entwebsit.domain.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lj on 2017/2/9.
 */
public class StringToDateConverter implements Converter<String, Date> {

    private static final String DATE = "yyyy-MM-dd";

    private static final String DATETIME = "yyyy-MM-dd HH:mm:ss";

    public Date convert(String source) {
        if (source == null || source.equals(""))
            return null;
        SimpleDateFormat simpleDateFormat;
        //判断类型
        if (source.contains(":")){
            simpleDateFormat= new SimpleDateFormat(DATETIME);
        }else{
            simpleDateFormat= new SimpleDateFormat(DATE);
        }
        //转成直接返回
        try {
            return simpleDateFormat.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}

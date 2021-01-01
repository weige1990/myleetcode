package com.test.redis.operate.common;

import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeTest {


    @Test
    public void outPrintLocalDateTime()
    {
        System.out.println("导出承建商"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}

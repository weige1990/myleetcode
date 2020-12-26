package com.test.redis.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/testHothub")
public class UserController {


    @RequestMapping("/test1")
    @ResponseBody
    public Object test1(String msg)
    {
        Map<String,Object> map=new HashMap<>();
        map.put("success",true);
        map.put("msg",msg);

        return map;
    }
}

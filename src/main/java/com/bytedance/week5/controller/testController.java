package com.bytedance.week5.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {

    @GetMapping("hello")
    @PreAuthorize("hasAuthority('test')")
    public String test(){
        return "hello";
    }
}

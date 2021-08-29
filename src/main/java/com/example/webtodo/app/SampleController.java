package com.example.webtodo.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class SampleController {
    @GetMapping
    public String test() {
        return "test";
    }
}

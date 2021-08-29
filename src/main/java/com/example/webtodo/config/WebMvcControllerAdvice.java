package com.example.webtodo.config;

import com.example.webtodo.service.TaskNotFoundException;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;


@ControllerAdvice
public class WebMvcControllerAdvice {

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        /* 入力の空文字をnullに変換する */
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public String handleException(TaskNotFoundException e, Model model) {
        model.addAttribute("message", e);

        return "error/CustomPage";
    }
}

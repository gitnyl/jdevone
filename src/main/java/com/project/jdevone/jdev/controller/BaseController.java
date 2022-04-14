package com.project.jdevone.jdev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ex")
public class BaseController {

    @GetMapping({"/", "/list"})
    public String list() {
        return "/ex/list";
    }

}

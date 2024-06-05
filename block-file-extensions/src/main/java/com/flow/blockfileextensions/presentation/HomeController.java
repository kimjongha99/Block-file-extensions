package com.flow.blockfileextensions.presentation;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {


    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

}

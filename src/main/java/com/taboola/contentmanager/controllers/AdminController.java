package com.taboola.contentmanager.controllers;

import com.taboola.contentmanager.models.contracts.PingResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminController {

    @GetMapping("ping")
    public PingResponse pingTest(){
        return new PingResponse("ok");
    }
}

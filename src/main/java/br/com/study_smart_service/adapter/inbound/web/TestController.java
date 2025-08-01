package br.com.study_smart_service.adapter.inbound.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/resource")
public class TestController {

    @GetMapping
    public String test() {
        return "funcionou";
    }
}

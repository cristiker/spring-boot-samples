package com.crsitik.atmosphere.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author cristik
 */

@Controller
@RequestMapping("/demo")
public class ChatController {

    @RequestMapping("/chat")
    public String chat() {
        return "/chat";
    }

    @RequestMapping("/chat/room")
    public String chatRoom() {
        return "/multiple-chatroom";
    }

    @RequestMapping("/push")
    public String push() {
        return "/push";
    }

    @RequestMapping("/event")
    public String event() {
        return "/event";
    }

    @RequestMapping("/test")
    public String test() {
        return "/test";
    }
}
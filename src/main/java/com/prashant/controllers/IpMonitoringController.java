package com.prashant.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author prashant sabnekar
 **/

@RestController
public class IpMonitoringController {


    @RequestMapping("/")
    public String checkApplicationIsRunning() {
        return "The IP Monitoring Application is Up & Running";
    }
}

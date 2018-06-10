package com.prashant.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prashant.model.IpConfigDTO;
import com.prashant.service.IPMonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author prashant sabnekar
 **/

@RestController
public class IpMonitoringController {


    @RequestMapping("/")
    public String checkApplicationIsRunning() {
        return "The IP Monitoring Application is Up & Running";
    }

    @Autowired
    private IPMonitoringService service;

    @RequestMapping(value="/configurations", method = RequestMethod.GET)
    public String getAllConfigurations(){
        return service.getAllConfigurations();
    }

    @RequestMapping(value = "/ipaddress", method = RequestMethod.POST)
    public ResponseEntity< String > persistIpAddress(@RequestBody String ipAddress) {
        if (service.isValid(ipAddress)) {
            service.persist(ipAddress);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }

    @RequestMapping(value = "/configuration", method = RequestMethod.POST)
    public ResponseEntity< String > persistIpConfiguration(@RequestBody String ipConfig) {
        if (service.isValidConfiguration(ipConfig)) {
            ObjectMapper mapper = new ObjectMapper();
            IpConfigDTO ipConfigDTO = null;
            try {
                ipConfigDTO = mapper.readValue(ipConfig, IpConfigDTO.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            service.persist(ipConfigDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }

    @RequestMapping(value = "/configuration", method = RequestMethod.DELETE)
    public ResponseEntity< String > deleteIpConfiguration(@RequestBody String ipConfig) {
        if (service.isValidConfiguration(ipConfig)) {
            ObjectMapper mapper = new ObjectMapper();
            IpConfigDTO ipConfigDTO = null;
            try {
                ipConfigDTO = mapper.readValue(ipConfig, IpConfigDTO.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            service.deleteIpConfig(ipConfigDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }

    @RequestMapping(value = "/address", method = RequestMethod.DELETE)
    public ResponseEntity< String > deleteIpAddress(@RequestBody String ipConfig) {
        if (service.isValid(ipConfig)) {
            service.delete(ipConfig);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }

    @RequestMapping(value="/configuration/{ip}", method = RequestMethod.GET)
    public String checkCOnfigurations(@PathVariable("ip") String ipAddress){
        return service.isIpPresent(ipAddress);
    }

}

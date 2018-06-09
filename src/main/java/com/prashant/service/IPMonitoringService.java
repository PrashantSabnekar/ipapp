package com.prashant.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prashant.model.IpConfigDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author prashant sabnekar
 **/

@Service
public class IPMonitoringService {

    private Map<String, IpConfigDTO> ipConfigs = new HashMap<>();

    public boolean isValid(IpConfigDTO config) {
        if(null == config.getIp() || config.getIp().equals("")) {
            return false;
        }
        return true;
    }

    public boolean isValid(String ipAddress) {
        if(null == ipAddress || ipAddress.equals("")) {
            return false;
        }
        return true;
    }

    public boolean isValidConfiguration(String ipConfig) {
        boolean result = false;
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(ipConfig);
            String ip = root.get("ip").asText();
           // System.out.println("ip = " + ip);
            //String tokens[] = ip.split(".");
            if(null != ip) {
                result = true;
            }

        } catch(IOException ex) {
            ex.printStackTrace();
        }


        return result;
    }

    public void persist(IpConfigDTO config) {
        ipConfigs.put(config.getIp(), config);
    }

    public void persist(String ipAddress) {
        IpConfigDTO config = new IpConfigDTO();
        config.setIp(ipAddress);
        config.setConfig("Default Configuration");
        ipConfigs.put(ipAddress, config);

    }

    public void delete(String ipAddress) {
        if(ipConfigs.containsKey(ipAddress)) {
            ipConfigs.remove(ipAddress);
        }
    }

    public void deleteIpConfig(IpConfigDTO ipConfig) {
        if(ipConfigs.containsKey(ipConfig.getIp())) {
            ipConfigs.remove(ipConfig.getIp());
        }
    }


    public String isIpPresent(String ipAddress) {

        if(ipConfigs.containsKey(ipAddress)) {
            return  "This IP Address: " + ipAddress + " is blacklisted.";
        } else {
            return  "The IP Address: " + ipAddress + " is not present in the blacklisted entries.";
        }
    }

    public String getAllConfigurations(){
        StringBuffer sb = new StringBuffer();
        ipConfigs.values().forEach(x -> {
            sb.append("[IP Address: ");
            sb.append(x.getIp());
            sb.append(", Configuration: ");
            sb.append(x.getConfig());
            sb.append("]\n");
        });
        return sb.toString();
    }


}

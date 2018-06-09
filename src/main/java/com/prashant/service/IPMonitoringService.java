package com.prashant.service;

import com.prashant.model.IpConfigDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

    public void persist(IpConfigDTO config) {
        ipConfigs.put(config.getIp(), config);
    }

    public void persist(String ipAddress) {
        IpConfigDTO config = new IpConfigDTO();
        config.setIp(ipAddress);
        ipConfigs.put(ipAddress, config);
    }

    public void delete(String ipAddress) {
        if(ipConfigs.containsKey(ipAddress)) {
            ipConfigs.remove(ipAddress);
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
            sb.append(x.getIp());
            sb.append(" ");
        });
        return sb.toString();
    }
}

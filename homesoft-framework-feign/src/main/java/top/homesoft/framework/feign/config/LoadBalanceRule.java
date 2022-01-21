package top.homesoft.framework.feign.config;

import com.netflix.loadbalancer.*;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@Slf4j
public class LoadBalanceRule extends BestAvailableRule {
    ILoadBalancer balancer = new BaseLoadBalancer();
    @Override
    public Server choose(Object o) {
        List<Server> allServers = balancer.getAllServers();
        String localIp = "";
        try {
            localIp = InetAddress.getLocalHost().getHostAddress().toString();
        } catch (UnknownHostException e) {
            logger.info("未找到本机ip：", e);
        }

        for (Server server : allServers) {
            if (server.getHost().equalsIgnoreCase(localIp)) {
                logger.info("本次Feign调用地址：host - [{}] Server - [{}]", server.getHost(), server.toString());
                return server;
            }
        }
        return super.choose(o);
    }

    @Override
    public void setLoadBalancer(ILoadBalancer iLoadBalancer) {
        this.balancer = iLoadBalancer;
    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        return this.balancer;
    }
}

package com.ZhaoChenheng.serviceB;


import com.ZhaoChenheng.api.ServiceAInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("ServiceA")
public interface ServiceAClient extends ServiceAInterface {

}

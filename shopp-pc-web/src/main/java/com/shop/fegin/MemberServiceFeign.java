package com.shop.fegin;

import com.shop.api.service.MemberService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author admin
 */
@Component
@FeignClient("member")
public interface MemberServiceFeign extends MemberService {
}

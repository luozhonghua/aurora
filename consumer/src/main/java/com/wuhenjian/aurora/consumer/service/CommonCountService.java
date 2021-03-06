package com.wuhenjian.aurora.consumer.service;

import com.wuhenjian.aurora.consumer.excphandler.CommonCountServiceExceptionHandler;
import com.wuhenjian.aurora.utils.exception.BusinessException;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author 無痕剑
 * @date 2017/12/27 11:28
 */
@Service
@FeignClient(value = "db", fallback = CommonCountServiceExceptionHandler.class)
public interface CommonCountService {
	String BASE_PATH = "/sql/commonCount";

	@RequestMapping(value = BASE_PATH + "/getAccountCode", method = RequestMethod.GET)
	Long getAccountCode() throws BusinessException;

	@RequestMapping(value = BASE_PATH + "/addAccountCode", method = RequestMethod.POST)
	void addAccountCode() throws BusinessException;
}

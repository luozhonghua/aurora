package com.wuhenjian.aurora.memberservice.excphandler;

import com.wuhenjian.aurora.memberservice.service.CommonCountService;
import com.wuhenjian.aurora.utils.constant.ResultStatus;
import com.wuhenjian.aurora.utils.entity.result.ApiResult;
import org.springframework.stereotype.Component;

/**
 * @author 無痕剑
 * @date 2017/12/27 11:29
 */
@Component("commonCountServiceExceptionHandler")
public class CommonCountServiceExceptionHandler implements CommonCountService {
	@Override
	public ApiResult getAccountCode() {
		return this.exceptionResult();
	}

	@Override
	public ApiResult addAccountCode() {
		return this.exceptionResult();
	}

	private ApiResult exceptionResult() {
		return ApiResult.fail(ResultStatus.REMOTE_SERVICE_EXCEPTION);
	}
}

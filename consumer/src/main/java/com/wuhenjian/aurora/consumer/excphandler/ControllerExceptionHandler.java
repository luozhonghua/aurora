package com.wuhenjian.aurora.consumer.excphandler;

import com.wuhenjian.aurora.utils.constant.ResultStatus;
import com.wuhenjian.aurora.utils.entity.dto.ApiResult;
import com.wuhenjian.aurora.utils.exception.BusinessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 無痕剑
 * @date 2017/12/28 16:48
 */
@RestControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ApiResult handler(Exception e) {
		e.printStackTrace();
		if (e instanceof BusinessException) {
			return ApiResult.fail((BusinessException) e);
		}
		return ApiResult.fail(ResultStatus.REMOTE_SERVICE_EXCEPTION);
	}
}

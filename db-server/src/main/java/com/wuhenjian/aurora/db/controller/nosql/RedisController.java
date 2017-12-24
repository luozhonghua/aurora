package com.wuhenjian.aurora.db.controller.nosql;

import com.wuhenjian.aurora.db.service.RedisService;
import com.wuhenjian.aurora.utils.entity.MemberAcctInfo;
import com.wuhenjian.aurora.utils.entity.constant.ResultStatus;
import com.wuhenjian.aurora.utils.entity.result.ApiResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 無痕剑
 * @date 2017/12/8 16:34
 */
@RestController
@RequestMapping("/nosql/redis")
public class RedisController {

	@Resource(name = "tokenService")
	private RedisService redisService;

	@RequestMapping(value = "/setToken", method = RequestMethod.POST)
	public ApiResult setToken(@RequestParam("token") String token, MemberAcctInfo memberAcctInfo) {
		try {
			redisService.setToken(token, memberAcctInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return ApiResult.fail(ResultStatus.SYSTEM_EXCEPTION.getCode(), e.getMessage());
		}
		return ApiResult.success();
	}

	@RequestMapping(value = "/getToken", method = RequestMethod.GET)
	public ApiResult getToken(@RequestParam("token") String token) {
		MemberAcctInfo memberAcctInfo;
		try {
			memberAcctInfo = redisService.getToken(token);
		} catch (Exception e) {
			e.printStackTrace();
			return ApiResult.fail(ResultStatus.SYSTEM_EXCEPTION.getCode(), e.getMessage());
		}
		return ApiResult.success(memberAcctInfo);
	}

	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public ApiResult del(@RequestParam("key") String key) {
		try {
			redisService.del(key);
		} catch (Exception e) {
			e.printStackTrace();
			return ApiResult.fail(ResultStatus.SYSTEM_EXCEPTION.getCode(), e.getMessage());
		}
		return ApiResult.success();
	}

	@RequestMapping(value = "set", method = RequestMethod.POST)
	public ApiResult set(@RequestParam("key") String key,
						 @RequestParam("value") String value,
						 @RequestParam(value = "expire", required = false) Integer expire) {
		try {
			redisService.set(key, value, expire);
		} catch (Exception e) {
			e.printStackTrace();
			return ApiResult.fail(ResultStatus.SYSTEM_EXCEPTION.getCode(), e.getMessage());
		}
		return ApiResult.success();
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public ApiResult get(@RequestParam("key") String key) {
		String value;
		try {
			value = redisService.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			return ApiResult.fail(ResultStatus.SYSTEM_EXCEPTION.getCode(), e.getMessage());
		}
		return ApiResult.success(value);
	}
}
package com.wuhenjian.aurora.memberservice.controller.api;

import com.wuhenjian.aurora.memberservice.service.MemberLoginService;
import com.wuhenjian.aurora.utils.StringUtil;
import com.wuhenjian.aurora.utils.entity.TokenInfo;
import com.wuhenjian.aurora.utils.entity.constant.ResultStatus;
import com.wuhenjian.aurora.utils.entity.result.ApiResult;
import com.wuhenjian.aurora.utils.exception.BusinessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author SwordNoTrace
 * @date 2017/12/4 23:13
 */
@RestController
@RequestMapping("/member")
public class MemberLoginController {

	@Resource(name = "memberLoginService")
	private MemberLoginService memberLoginService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ApiResult login(String loginType, String memberAccount, String memberPassword, String paramSign) throws BusinessException {
		if (StringUtil.hasBlank(new String[]{loginType, memberAccount, memberPassword, paramSign})) {
			return ApiResult.fail(ResultStatus.PARAM_IS_EMPTY);
		}
		TokenInfo tokenInfo = memberLoginService.login(loginType, memberAccount, memberPassword, paramSign);
		return ApiResult.success(tokenInfo);
	}
}

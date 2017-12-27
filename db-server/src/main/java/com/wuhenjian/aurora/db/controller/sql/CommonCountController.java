package com.wuhenjian.aurora.db.controller.sql;

import com.wuhenjian.aurora.db.mapper.sql.CommonCountMapper;
import com.wuhenjian.aurora.utils.entity.result.ApiResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 無痕剑
 * @date 2017/12/27 11:19
 */
@RestController
@RequestMapping(AbstractSqlBaseController.BASE_PATH + "/commonCount")
public class CommonCountController {

	@Resource(name = "commonCountMapper")
	private CommonCountMapper commonCountMapper;

	@RequestMapping(value = "/getAccountCode", method = RequestMethod.GET)
	public ApiResult getAccountCode() {
		return ApiResult.success(commonCountMapper.getAccountCode());
	}

	@RequestMapping(value = "/addAccountCode", method = RequestMethod.POST)
	public ApiResult addAccountCode() {
		commonCountMapper.addAccountCode();
		return ApiResult.success();
	}
}

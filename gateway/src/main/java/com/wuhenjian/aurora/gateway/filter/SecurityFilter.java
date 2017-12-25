package com.wuhenjian.aurora.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.wuhenjian.aurora.gateway.service.RedisService;
import com.wuhenjian.aurora.utils.ApiResultUtil;
import com.wuhenjian.aurora.utils.AuthUtil;
import com.wuhenjian.aurora.utils.DateUtil;
import com.wuhenjian.aurora.utils.JsonUtil;
import com.wuhenjian.aurora.utils.constant.CommonContant;
import com.wuhenjian.aurora.utils.constant.ResultStatus;
import com.wuhenjian.aurora.utils.entity.result.ApiResult;
import com.wuhenjian.aurora.utils.exception.BusinessException;
import com.wuhenjian.aurora.utils.security.SHA256;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * API接口安全验证
 * @author 無痕剑
 * @date 2017/12/5 10:10
 */
@Component
public class SecurityFilter extends ZuulFilter {

	@Resource(name = "redisService")
	private RedisService redisService;

	/**
	 * 返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型，具体如下：
	 * <p>pre：可以在请求被路由之前调用</p>
	 * <p>route：在路由请求时候被调用</p>
	 * <p>post：在route和error过滤器之后被调用</p>
	 * <p>error：处理请求时发生错误时被调用</p>
	 * @return 生命周期类型
	 */
	@Override
	public String filterType() {
		return "pre";
	}

	/**
	 * 数字越大越靠后
	 */
	@Override
	public int filterOrder() {
		return 0;
	}

	/**
	 * 登录接口不过滤
	 * @return Boolean
	 */
	@Override
	public boolean shouldFilter() {
		HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
		String requestURI = request.getRequestURI();
		return !requestURI.contains("/entry/");
	}

	@Override
	public Object run() {
		RequestContext context = RequestContext.getCurrentContext();
		HttpServletRequest request = context.getRequest();
		//TODO 调试的时候使用debug参数
		/******debug*******/
		String d = request.getParameter("d");
		if ("1".equals(d)) {
			return null;
		}
		/******debug*******/
		//验证时间戳
		String timestamp = request.getParameter("timestamp");//时间戳
		if (timestamp.matches("^\\d*$")) {
			this.response(context, ResultStatus.TIMESTAMP_FORMAT_ERROR);
			return null;
		}
		long ts = Long.valueOf(timestamp);
		if (System.currentTimeMillis() - ts > DateUtil.TEN_SECONDS_MS) {//与当前时间大于10秒
			this.response(context, ResultStatus.TIMESTAMP_OVERTIME);
			return  null;
		}
		//验证签名
		String paramSign = request.getParameter("param_sign");//签名
		Map<String, String[]> parameterMap = request.getParameterMap();
		//组装参数，得到参数签名
		Map<String,String> paramMap = new HashMap<>();
		for (String key : parameterMap.keySet()) {
			String[] values = parameterMap.get(key);
			for (String value : values) {//只有一个
				paramMap.put(key, value);
			}
		}
		String sign;
		try {
			sign = SHA256.encode(paramMap);
		} catch (BusinessException e) {
			this.response(context, e.getRs());
			return null;
		}
		if (!sign.equals(paramSign)) {
			this.response(context, ResultStatus.SIGN_ERROR);
			return null;
		}
		//解析token
		String accessToken = request.getParameter("access_token");//令牌
		String token;
		try {
			token = AuthUtil.decodeToken(accessToken);
		} catch (BusinessException e) {
			this.response(context, e.getRs());
			return null;
		}
		//判断token是否过期
		ApiResult r1 = redisService.getToken(token);
		Object mai;
		try {
			mai = ApiResultUtil.getObject(r1);
		} catch (BusinessException e) {
			this.response(context, ResultStatus.REMOTE_SERVICE_EXCEPTION);
			return null;
		}
		if (mai == null) {
			this.response(context, ResultStatus.TOKEN_OVERDUE);
			return null;
		}
		//将用户账户信息放到请求中去
		request.setAttribute(CommonContant.REQUEST_MEMBER_INFO, mai);
		return null;
	}

	/**
	 * 设置拦截器响应
	 * @param context 上下文
	 * @param rs 响应值
	 */
	private void response(RequestContext context, ResultStatus rs) {
		context.setSendZuulResponse(false);
		context.setResponseStatusCode(HttpServletResponse.SC_OK);
		ApiResult fail = ApiResult.fail(rs);
		String json = JsonUtil.obj2Json(fail);
        try {
            context.getResponse().getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

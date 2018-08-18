package com.sinocontact.utils.wx;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.api.MpApi;
import com.foxinmy.weixin4j.mp.model.Following;
import com.foxinmy.weixin4j.mp.model.User;
import com.foxinmy.weixin4j.mp.type.Lang;

public class UserApi extends MpApi {

	private final String accessToken;
	
	public UserApi(String accessToken){
		this.accessToken = accessToken;
	}
	public User getUser(String openId) throws WeixinException {
		return getUser(openId, Lang.zh_CN);
	}
	public User getUser(String openId, Lang lang) throws WeixinException {
		String user_info_uri = getRequestUri("api_user_info_uri");
		
		WeixinResponse response = weixinExecutor
				.get(String.format(user_info_uri, accessToken, openId, lang.name()));

		return response.getAsObject(new TypeReference<User>() {
		});
	}
	public List<User> getUsers(String... openIds) throws WeixinException {
		return getUsers(Lang.zh_CN, openIds);
	}
	public List<User> getUsers(Lang lang, String... openIds) throws WeixinException {
		String api_users_info_uri = getRequestUri("api_users_info_uri");
		StringBuilder parameter = new StringBuilder();
		parameter.append("{\"user_list\": [");
		for (String openId : openIds) {
			parameter.append("{\"openid\": \"").append(openId).append("\"");
			parameter.append(",\"lang\": \"").append(lang.name()).append("\"").append("},");
		}
		parameter.delete(parameter.length() - 1, parameter.length());
		parameter.append("]}");
		
		WeixinResponse response = weixinExecutor.post(String.format(api_users_info_uri, this.accessToken),
				parameter.toString());

		return JSON.parseArray(response.getAsJson().getString("user_info_list"), User.class);
	}
	public Following getFollowing(String nextOpenId) throws WeixinException {
		Following following = getFollowingOpenIds(nextOpenId);
		if (following.getCount() > 0) {
			List<User> users = new ArrayList<User>(following.getCount());
			for (int i = 1; i <= (int) Math.ceil(following.getCount() / 100d); i++) {
				users.addAll(getUsers(following.getOpenIds()
						.subList((i - 1) * 100, Math.min(i * 100, following.getCount())).toArray(new String[] {})));
			}
			following.setUserList(users);
		}
		return following;
	}
	public Following getFollowingOpenIds(String nextOpenId) throws WeixinException {
		String following_uri = getRequestUri("following_uri");
		
		WeixinResponse response = weixinExecutor
				.get(String.format(following_uri, this.accessToken, nextOpenId == null ? "" : nextOpenId));

		JSONObject result = response.getAsJson();
		Following following = JSON.toJavaObject(result, Following.class);

		if (following.getCount() > 0) {
			following.setOpenIds(JSON.parseArray(result.getJSONObject("data").getString("openid"), String.class));
		}
		return following;
	}
	public List<User> getAllFollowing() throws WeixinException {
		List<User> userList = new ArrayList<User>();
		String nextOpenId = null;
		Following f = null;
		for (;;) {
			f = getFollowing(nextOpenId);
			if (f.hasContent()) {
				userList.addAll(f.getUserList());
				nextOpenId = f.getNextOpenId();
				continue;
			}
			break;
		}
		return userList;
	}
	public List<String> getAllFollowingOpenIds() throws WeixinException {
		List<String> openIds = new ArrayList<String>();
		String nextOpenId = null;
		Following f = null;
		for (;;) {
			f = getFollowingOpenIds(nextOpenId);
			if (f.hasContent()) {
				openIds.addAll(f.getOpenIds());
				nextOpenId = f.getNextOpenId();
				continue;
			}
			break;
		}
		return openIds;
	}
	public ApiResult remarkUserName(String openId, String remark) throws WeixinException {
		String username_remark_uri = getRequestUri("username_remark_uri");
	
		JSONObject obj = new JSONObject();
		obj.put("openid", openId);
		obj.put("remark", remark);
		WeixinResponse response = weixinExecutor.post(String.format(username_remark_uri, this.accessToken),
				obj.toJSONString());

		return response.getAsResult();
	}
}

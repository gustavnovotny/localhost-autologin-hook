/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.sohlman.hook.locahostlogin;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.AutoLogin;
import com.liferay.portal.security.auth.BaseAutoLogin;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Logic of this hook is based on SiteMinderAutoLogin
 * 
 * @author Sampsa Sohlman
 */
public class LocalhostAutoLogin extends BaseAutoLogin {

	@Override
	protected String[] doLogin(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		Company company = PortalUtil.getCompany(request);

		long companyId = company.getCompanyId();

		
		if ( request.getLocalAddr().equals("127.0.0.1")) {
			
			long userId  = GetterUtil.get(PropsUtil.get("localhost.autologin.userId"), -1);
			
			if (userId==-1) {
				return null;
			}
			
			User user = UserLocalServiceUtil.fetchUserById( userId);
			
			if (user==null) {
				return null;
			}
			
			if (user.getCompanyId()!=companyId) {
				return null;
			}
			
			String[] credentials = new String[3];

			credentials[0] = String.valueOf(user.getUserId());
			credentials[1] = user.getPassword();
			credentials[2] = Boolean.TRUE.toString();

			String redirect = ParamUtil.getString(request, "redirect");

			if (Validator.isNotNull(redirect)) {
				request.setAttribute(
					AutoLogin.AUTO_LOGIN_REDIRECT_AND_CONTINUE,
					PortalUtil.escapeRedirect(redirect));
			}
			
			return credentials;
		}
		else {
			return null;
		}
	}

}
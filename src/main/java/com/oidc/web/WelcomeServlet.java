package com.oidc.web;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import com.oidc.oidc_implementation.User;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

@WebServlet(urlPatterns = { "/" }, loadOnStartup = 1)
public class WelcomeServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		/*
		 * Tries to find the user on the session. If no user can be found, a login link
		 * is presented.
		 *
		 * After login the user will be redirected back to this servlet with the user on
		 * the session.
		 *
		 */

		User user = (User) request.getSession().getAttribute("user");

		response.getWriter().append("<html>").append("<head></head><body>").append("<h1>Java OIDC Implementation</h1>");

		if (user == null) {
			response.getWriter().append("<p>You are not logged in.</p>")
					.append("<a href=\"/oidc-implement/login\">Log in</a>");
		} else {

			response.getWriter().append(String.format("<h2>Name</h2><p>%s</p>", user.getuserInfo().getName()))
					.append("<h2>Access token</h2>").append(String.format("<p>%s</p>", user.getAccessToken()))
					.append("<h2>Id token</h2>")
					.append(String.format("<p>%s</p>", user.getIdToken().getParsedString()));

			Iterator<String> iterator = user.getuserInfo().getStandardClaimNames().iterator();

			while (iterator.hasNext()) {

				String claimName = iterator.next();
				Object claimValue = user.getuserInfo().getClaim(claimName);

				if (claimValue != null)
					response.getWriter().append(String.format("<h2>%s</h2>", claimName))
							.append(String.format("<p>%s</p>", claimValue));
			}
		}

		response.getWriter().append("</body></html");
	}

}
package vnu.uet.data.crawler;

import vn.com.datasection.dto.UserInfo;
import vn.com.datasection.fb.ExtractUserInfo;
import vn.com.datasection.fb.connect.ConnectFB;
import vn.com.datasection.fb.connect.HttpConnect;

public class CrawlStep4GetUserLikesPage {
	public void getUserLikePage(String userID) {
		String linkUser = "https://www.facebook.com/" + userID;
		System.out.println(linkUser);
		@SuppressWarnings("unused")
		HttpConnect connect = new ConnectFB("banchanban.0", "khiemton")
				.loginFacebook();
		ExtractUserInfo userInfo = new ExtractUserInfo();
		UserInfo uI = userInfo.getUserInfo(linkUser);
		String pLikes = uI.getLikes().toString().replaceAll("=", ":");
		System.out.println(pLikes);

	}
	public static void main(String args[]){
		CrawlStep4GetUserLikesPage pageLikes = new CrawlStep4GetUserLikesPage();
		pageLikes.getUserLikePage("hang.vu.0410");
	}
}

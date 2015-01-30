package vnu.uet.data.crawler;
import vn.com.datasection.dto.UserInfo;
import vn.com.datasection.fb.ExtractUserInfo;

public class ClassifyCategory {
	public void Classify(String linkPost) {
		String linkPt = "https://www.facebook.com/"
				+ linkPost.replaceAll("_", "/posts/");
		System.out.println(linkPt);
		// set limit for loop
		// System.out.println(linkPt);
		ExtractUserInfo userInfo = new ExtractUserInfo();
		@SuppressWarnings("unused")
		UserInfo u = userInfo.getUserInfo(linkPt);
	}
}

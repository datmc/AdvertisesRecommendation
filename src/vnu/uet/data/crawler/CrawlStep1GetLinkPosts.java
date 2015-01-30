package vnu.uet.data.crawler;

/*
 * get links of posts from facebook.com/congdongvnexpress
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.types.Page;
import com.restfb.types.Post;

public class CrawlStep1GetLinkPosts {

	public int getAllPosts() throws IOException {
		int num = 0;
		String link = "congdongvnexpress";
		String APP_ID = "812290575501201";
		String APP_SECRET = "5864a83970e8ed6d9eeb0f5d7524bf34";
		AccessToken accessToken = new DefaultFacebookClient()
				.obtainAppAccessToken(APP_ID, APP_SECRET);

		FacebookClient facebookClient = new DefaultFacebookClient(
				accessToken.getAccessToken());
		Page page = facebookClient.fetchObject(link, Page.class);
		Connection<Post> feed = facebookClient.fetchConnection(page.getId()
				+ "/feed", Post.class, Parameter.with("fields", "id"));

		File dir = new File("in_out");
		if (!dir.exists()) {
			dir.mkdir();
		}
		File postLink = new File("in_out/post_link.txt");
		if (!postLink.exists()) {
			postLink.createNewFile();
		}
		FileWriter fwriter = new FileWriter(postLink);
		BufferedWriter writer = new BufferedWriter(fwriter);
		for (List<Post> posts : feed) {
			for (Post post : posts) {
				writer.write(post.getId() + "/n");
			}
		}
		writer.close();
		fwriter.close();
		return num;
	}
	public static void main(String[] args) throws IOException{
		CrawlStep1GetLinkPosts links = new CrawlStep1GetLinkPosts();
		links.getAllPosts();
	}

}

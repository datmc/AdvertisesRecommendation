/*
 * Get like, share, comment user
 */
package vnu.uet.data.crawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONException;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.types.Post;

import vn.com.datasection.dto.PostInfo;
import vn.com.datasection.fb.ExtractPostInfo;

public class CrawlStep3GetData {

	public void getPostsData(String linkPost, int num) throws IOException,
			JSONException {
		String APP_ID = "812290575501201";
		String APP_SECRET = "5864a83970e8ed6d9eeb0f5d7524bf34";
		AccessToken accessToken = new DefaultFacebookClient()
				.obtainAppAccessToken(APP_ID, APP_SECRET);

		FacebookClient facebookClient = new DefaultFacebookClient(
				accessToken.getAccessToken());

		Post post = facebookClient.fetchObject(linkPost, Post.class, Parameter
				.with("fields", "actions,link,id,message,created_time,name"));

		File dir = new File("data");
		if (!dir.exists()) {
			dir.mkdir();
		}
		File likedUser = new File("data/like_user_" + num + ".txt");
		if (!likedUser.exists()) {
			likedUser.createNewFile();
		}
		File commentedUser = new File("data/commented_user_" + num + ".txt");
		if (!commentedUser.exists()) {
			commentedUser.createNewFile();
		}
		File sharedUser = new File("data/shared_user_" + num + ".txt");
		if (!sharedUser.exists()) {
			sharedUser.createNewFile();
		}
		File postData = new File("data/post_data_" + num + ".txt");
		if (!postData.exists()) {
			postData.createNewFile();
		}

		FileWriter LikedUserId = new FileWriter(likedUser, true);
		BufferedWriter writeLikedUserId = new BufferedWriter(LikedUserId);

		FileWriter commentedUserId = new FileWriter(commentedUser, true);
		BufferedWriter writeCommentedUser = new BufferedWriter(commentedUserId);

		FileWriter sharedUserId = new FileWriter(sharedUser, true);
		BufferedWriter writeSharedUser = new BufferedWriter(sharedUserId);
		// ----
		FileWriter fileWriterData = new FileWriter(postData, true);
		BufferedWriter writeData = new BufferedWriter(fileWriterData);
		// ----------------------------------------------------------------------------------

		String linkPt = "https://www.facebook.com/"
				+ linkPost.replaceAll("_", "/posts/");
		// set limit for loop
		// System.out.println(linkPt);

		// get post info: like, share, comment
		ExtractPostInfo postInfo = new ExtractPostInfo();
		PostInfo pos = postInfo.getPostInfo(linkPt);

		// // write to link of user
		Object[] arrayLike = pos.getLikes().keySet().toArray();
		Object[] arrayComment = pos.getCommenst().keySet().toArray();
		Object[] arrayShare = pos.getShares().keySet().toArray();

		for (Object share : arrayShare) {
			writeSharedUser.write(share.toString() + "\n");

		}
		for (Object comment : arrayComment) {
			writeCommentedUser.write(comment.toString() + "\n");
		}
		for (Object like : arrayLike) {
			writeLikedUserId.write(like.toString() + "\n");
		}
		//
		// // edit output, make to json
		String postInfoInJson = pos.toJson();
		int length = postInfoInJson.length();
		String result = postInfoInJson.subSequence(0, (length - 2)) + "},";
		String string = "\"URL\":{\"" + linkPost + "\":\"" + linkPt + "\"},"
				+ "\"times\":{\"" + linkPost + "\":\"" + post.getCreatedTime()
				+ "\"}}";

		result += string;

		// write result
		writeData.write(result + "\n");
		// ------
		// make empty
		result = "";

		// ------------------------------------------------------------------------------------
		writeData.close();
		fileWriterData.close();
		writeCommentedUser.close();
		commentedUserId.close();
		writeLikedUserId.close();
		LikedUserId.close();
		writeSharedUser.close();
		sharedUserId.close();
		// -------------------------------------------------------------------------------------
		// System.out.println("done:  " + i);
		// i++;
	}

	public static void main(String[] args) throws IOException, JSONException {
		int num = 100;

		CrawlStep3GetData step2 = new CrawlStep3GetData();

		FileReader fRead = new FileReader("in_out/post_link_" + num + ".txt");
		BufferedReader reader = new BufferedReader(fRead);
		String linkPost;
		while ((linkPost = reader.readLine()) != null) {
			step2.getPostsData(linkPost, num); //
			// System.out.println("main: " + linkPost);
		}

		// num++;
		System.out.println("done"); //
		reader.close();
		fRead.close();

	}
}

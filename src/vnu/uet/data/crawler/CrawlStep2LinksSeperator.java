package vnu.uet.data.crawler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CrawlStep2LinksSeperator {
	public void split(int block, int postLimit) throws IOException {
		FileReader fread = new FileReader("in_out/post_link.txt");
		BufferedReader read = new BufferedReader(fread);
		int numberOfBlock = 1;
		while (true) {
			int i = 0;
			File f = new File("in_out/post_link_" + numberOfBlock + ".txt");
			if (!f.exists()) {
				f.createNewFile();
			}
			FileWriter fWrite = new FileWriter(f);
			String line = "";
			while (i < block) {
				line = read.readLine();
				fWrite.write(line + "\n");
				i += 1;
			}
			System.out.println(numberOfBlock);
			numberOfBlock += 1;
			fWrite.close();
			if(i + numberOfBlock*block > postLimit){
				break;
			}
			continue;
		}
		read.close();
		fread.close();
	}
	public static void main(String args[]) throws IOException{
		CrawlStep2LinksSeperator seperator = new CrawlStep2LinksSeperator();
		seperator.split(50, 7567);
	}
}

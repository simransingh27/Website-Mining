
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DemoUtils {

	public static void main(String args[]){
		
		/*
		 * 
		 * IMPORTANT:
		 * 
		 * Please DO NOT use any University web page 
	     * for testing as this may trigger a DoS (Denial-of-service) 
	     * attack alert at IT Service. 
	     * 
	     * Any sample URL provided 
		 * below is purely for information purposes only.
		 */

		String websiteURL="http://www.bbc.co.uk/news"; 
		
		//get HTML 
		String content= Utils.getTextFromAddress(websiteURL);
				
		//get all links
		ArrayList<String> urls= Utils.extractHyperlinks(websiteURL, content);
		
		//print all links
		urls.forEach(System.out::println); 
		
		//strips HTML tags
		String text= Utils.getPlainText(content);
		
		//print text
		System.out.println(text);
		
		
		List<String> keywords = Arrays.asList("university", "sports", "holidays", "britain", "brexit");
		
		//print word count
		System.out.println(	Utils.calculate(keywords, text));

	}

}

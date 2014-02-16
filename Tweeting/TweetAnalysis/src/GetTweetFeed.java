import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.List;
import java.util.Scanner;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class GetTweetFeed {

	
	
	public static void main(String[] args) {
	    ConfigurationBuilder cb = new ConfigurationBuilder();
	    cb.setDebugEnabled(true)
	          .setOAuthConsumerKey("AYokMxZeG69GUKvtdWrdw")
	          .setOAuthConsumerSecret("y5ovIAGKQZ7ytHWCeTog1s6ZVMciZc8rGJ3NIfPjry0")
	          .setOAuthAccessToken("26248032-wK1NLLB78NL7mvZpIdWxFEVFUUgJB5HyYdhaJ7jwM")
	          .setOAuthAccessTokenSecret("iPAPfLUznD33zTmOJ2pbrg1DRP3p1sOsCg4ZqWvydM4");
	    TwitterFactory tf = new TwitterFactory(cb.build());
	    Twitter twitter = tf.getInstance();
	    
	        try {
	        	FileWriter fw = new FileWriter(new File("FekuExpress.txt"));
				BufferedWriter bw = new BufferedWriter(fw);
				
	            Query query = new Query("#FekuExpress");
	            query.count(100);
	         //   query.since()
	            QueryResult result = null;
	          //  result = twitter.search(query);
	         //   List<Status> tweets = result.getTweets();
	            
	            do{
	            	result = twitter.search(query);
	                List<Status> tweets = result.getTweets();
	                for(Status tweet: tweets){
	                	bw.write(tweet.toString());
	                     System.out.println("Tweet: "+tweet.toString());
	                }
	                System.out.println("\n**************************************************\n");
	                System.out.println("\n**************************************************\n");
	                System.out.println("\n**************************************************\n");
	                query=result.nextQuery();
	                query.count(100);
	                if(query!=null)
	                     result=twitter.search(query);
	            }while(query!=null);
	            
	           
	               System.exit(0);
	        } catch (Exception te) {
	            te.printStackTrace();
	            System.out.println("Failed to search tweets: " + te.getMessage());
	            System.exit(-1);
	        }
	}
}

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import twitter4j.FilterQuery;
import twitter4j.RawStreamListener;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.internal.org.json.JSONObject;
import twitter4j.internal.org.json.JSONTokener;
import twitter4j.json.DataObjectFactory;





public final class PrintFilterStream {
    /**
     * Main entry of this application.
     *
     * @param args follow(comma separated user ids) track(comma separated filter terms)
     * @throws twitter4j.TwitterException
     */
    public static void main(String[] args) throws TwitterException {
       try{
    	 ConfigurationBuilder cb = new ConfigurationBuilder();
 	    cb.setDebugEnabled(true)
 	          .setOAuthConsumerKey("AYokMxZeG69GUKvtdWrdw")
 	          .setOAuthConsumerSecret("y5ovIAGKQZ7ytHWCeTog1s6ZVMciZc8rGJ3NIfPjry0")
 	          .setOAuthAccessToken("26248032-wK1NLLB78NL7mvZpIdWxFEVFUUgJB5HyYdhaJ7jwM")
 	          .setOAuthAccessTokenSecret("iPAPfLUznD33zTmOJ2pbrg1DRP3p1sOsCg4ZqWvydM4");
 	   FileWriter fw = new FileWriter(new File("Both.txt"));
		final BufferedWriter bw = new BufferedWriter(fw);
		
		RawStreamListener rlistener  = new RawStreamListener() {
			
			@Override
			public void onException(Exception arg0) {
				// TODO Auto-generated method stub
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void onMessage(String status) {
				System.out.println(status);
				try {
					bw.write(status);
					bw.write("\n");
				} catch (IOException e) {
					try {
						bw.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// TODO Auto-generated method stub
				
			}
		};
        StatusListener listener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
            	try {
            		
					//bw.write(status.toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	String rawJSON =DataObjectFactory.getRawJSON(status);
            	// JSONObject json = (JSONObject) JSONSerializer.toJSON(rawJSON);
                 //System.out.println(json);
               System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            @Override
            public void onStallWarning(StallWarning warning) {
                System.out.println("Got stall warning:" + warning);
            }

            @Override
            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };

        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        twitterStream.addListener(rlistener);
        ArrayList<Long> follow = new ArrayList<Long>();
        ArrayList<String> track = new ArrayList<String>();
        /*for (String arg : args) {
            if (isNumericalArgument(arg)) {
                for (String id : arg.split(",")) {
                    follow.add(Long.parseLong(id));
                }
            } else {
                track.addAll(Arrays.asList(arg.split(",")));
            }
        }
        long[] followArray = new long[follow.size()];
        for (int i = 0; i < follow.size(); i++) {
            followArray[i] = follow.get(i);
        }*/
        track.add("#FekuExpress");
        track.add("Yes We CAN");
        long[] followArray=null;
        String[] trackArray = track.toArray(new String[track.size()]);

        // filter() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
        twitterStream.filter(new FilterQuery(0, followArray, trackArray));
        
       }catch(Exception e){e.printStackTrace();
      
       }
    }

    private static boolean isNumericalArgument(String argument) {
        String args[] = argument.split(",");
        boolean isNumericalArgument = true;
        for (String arg : args) {
            try {
                Integer.parseInt(arg);
            } catch (NumberFormatException nfe) {
                isNumericalArgument = false;
                break;
            }
        }
        return isNumericalArgument;
    }
}
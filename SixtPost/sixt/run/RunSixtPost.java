package sixt.run;

import org.apache.log4j.Logger;

public class RunSixtPost {
    private static Logger logger = Logger.getLogger(RunSixtPost.class);
       
    public static void main(String[] args) {
    	RunCreateNewPost newPost = new RunCreateNewPost();
    	try {
			newPost.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
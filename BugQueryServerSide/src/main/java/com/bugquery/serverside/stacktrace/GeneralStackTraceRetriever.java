package com.bugquery.serverside.stacktrace;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.bugquery.serverside.dbparsing.DBConnector;
import com.bugquery.serverside.dbparsing.DBSearch;
import com.bugquery.serverside.entities.Post;
import com.bugquery.serverside.entities.StackTrace;
import com.bugquery.serverside.exceptions.GeneralDBException;

/**
 * Utility class for getting relevant stack traces from the database
 * @author rodedzats
 *
 */
public class GeneralStackTraceRetriever implements StackTraceRetriever{
	
	private StackTraceDistancer d;
	private DBConnector connector;
	
	public GeneralStackTraceRetriever() {
		this(new JaccardSTDistancer(), new DBConnector(){

			@Override
			public List<Post> getAllQuestionsWithTheException(@SuppressWarnings("unused") String __) {
				return null;
			}});
	}
	
	public GeneralStackTraceRetriever(StackTraceDistancer d, DBConnector connector) {
		this.d = d;
		this.connector = connector;
	}
	
	/**
	 * This function completes the pipeline between the server and the database.
	 * @param stackTrace - the stack trace which the user queried about
	 * @param numOfPosts - number of relevant posts needed
	 * @return list of most relevant post to the given stack trace
	 * @throws GeneralDBException 
	 */
	public static List<Post> getMostRelevantPosts_static(String stackTrace, int numOfPosts) throws GeneralDBException {
		if(stackTrace == null || numOfPosts <= 0)
			throw new IllegalArgumentException();
		StackTrace st = new StackTrace(stackTrace);
		List<Post> allPosts = new ArrayList<>();
		try {
			allPosts = DBSearch.getAllQuestionsWithTheException(st.getException());
		} catch(Exception e) {
			System.out.println((e + ""));
			throw new GeneralDBException("General db error: " + e.getMessage());
		}
		return GeneralStackTraceRetriever.getMostRelevantStackTraces(allPosts, st, (new JaccardSTDistancer()), numOfPosts);
	}
	
	/*
	 * This function returns the posts with the closest stack traces to the input @stackTrace
	 * using the @distance distancer to sort the traces.
	 * This function assumes that the database which contains posts with stack traces was
	 * extracted to a in memory list.
	 */
	public static List<Post> getMostRelevantStackTraces(List<Post> allPosts, final StackTrace t, StackTraceDistancer d, int numOfPosts) {
		if(allPosts == null || d == null || numOfPosts <= 0 || t == null)
			throw new IllegalArgumentException();
		Collections.sort(allPosts, new Comparator<Post>(){
			  @Override public int compare(Post p1, Post p2){
				return d.distance(p1.stackTrace, t) > d.distance(p2.stackTrace, t) ? 1
						: d.distance(p1.stackTrace, t) < d.distance(p2.stackTrace, t) ? -1 : 0;
			}
			});
		return allPosts.subList(0, numOfPosts);
	}

	@Override
	public List<Post> getMostRelevantPosts(String stackTrace, int numOfPosts) throws GeneralDBException {
		if(stackTrace == null || numOfPosts <= 0)
			throw new IllegalArgumentException();
		StackTrace st = new StackTrace(stackTrace);
		List<Post> allPosts = new ArrayList<>();
		try {
			allPosts = connector.getAllQuestionsWithTheException(st.getException());
		} catch(Exception e) {
			System.out.println((e + ""));
			throw new GeneralDBException("General db error: " + e.getMessage());
		}
		return GeneralStackTraceRetriever.getMostRelevantStackTraces(allPosts, st, d, numOfPosts);
	
	}
}

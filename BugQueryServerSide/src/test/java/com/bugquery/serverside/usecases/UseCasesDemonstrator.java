package com.bugquery.serverside.usecases;

import java.util.*;
import com.bugquery.serverside.entities.Post;
import com.bugquery.serverside.stacktrace.distance.*;
import com.bugquery.serverside.stacktrace.distance.levenshtein.LevenshteinSTDistancer;

/**
 * Class used to demonstrate different use cases, 
 * and seeing the result returned from the server 
 * using the different distancers 
 * @author yonzarecki
 * @since 8.5.2017
 */
public class UseCasesDemonstrator {
	private static List<Post> getRelevantPosts(StackTraceDistancer d, String stackTrace) {
		return new ArrayList<Post>();
	}
	
	private static List<StackTraceDistancer> getDistancerList() {
		List<StackTraceDistancer> allDistancers = new ArrayList<StackTraceDistancer>();
		allDistancers.add(new JaccardSTDistancer());
		allDistancers.add(new LevenshteinSTDistancer());
		return allDistancers;
	}
	
	private static List<String> getStackTracesList() {
		List<String> sampleStackTraces = new ArrayList<String>();
		String s1 = "Exception in thread \"main\" java.lang.NullPointerException\n" + 
				"        at com.example.myproject.Book.getTitle(Book.java:16)\n" + 
				"        at com.example.myproject.Author.getBookTitles(Author.java:25)\n" + 
				"        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)";
		sampleStackTraces.add(s1);
		return sampleStackTraces;
	}
	
	public static void main(String[] args) {
		List<StackTraceDistancer> allDistancers = getDistancerList();
		List<String> sampleStackTraces = getStackTracesList();
		List<Post> result;
		for(StackTraceDistancer d: allDistancers)
			for (String s : sampleStackTraces)
				result = getRelevantPosts(d, s);
	}
}

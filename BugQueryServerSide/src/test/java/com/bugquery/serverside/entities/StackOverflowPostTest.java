package com.bugquery.serverside.entities;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author zivizhar
 */
@SuppressWarnings("static-method")
public class StackOverflowPostTest {

	@Test
	public void testCreateStackOverflowPost() {
		String question = "Sometimes when I run my application it gives me an error that looks like:\n" + 
				"\n" + 
				"Exception in thread \"main\" java.lang.NullPointerException\n" + 
				"        at com.example.myproject.Book.getTitle(Book.java:16)\n" + 
				"        at com.example.myproject.Author.getBookTitles(Author.java:25)\n" + 
				"        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)\n" + 
				"People have referred to this as a \"stack trace\". What is a stack trace? What can it tell me about the error that's happening in my program?";
		String trace = "Exception in thread \"main\" java.lang.NullPointerException\n" + 
				"        at com.example.myproject.Book.getTitle(Book.java:16)\n" + 
				"        at com.example.myproject.Author.getBookTitles(Author.java:25)\n" + 
				"        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)";
		StackOverflowPost post = new StackOverflowPost(null,1, null, null, 0, question, "help", null, 5);
		assertEquals(trace,post.stackTrace.getString());
		assertEquals(trace,post + "");
	}

}

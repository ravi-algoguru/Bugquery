package com.bugquery.serverside.stacktrace;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * This test class is based on the answers in 
 * http://stackoverflow.com/questions/3988788/what-is-a-stack-trace-and-how-can-i-use-it-to-debug-my-application-errors
 * @author rodedzats
 *
 */
public class StackTraceExtractorTest {
	
	private List<String> getTrimmedListOfTraces(String query) {
		List<String> $ = new ArrayList<>();
		for(String ¢: StackTraceExtractor.extract(query))
			$.add(¢.trim());
		return $;
	}
	
	@Test
	public void emptyListIsReturnedForNullStringAndEmptyString() {
		assertEquals(new ArrayList<String>(), StackTraceExtractor.extract(null));
		assertEquals(new ArrayList<String>(), StackTraceExtractor.extract(""));
	}
	
	/*
	 * Checking that for a random stackoverflow question which doesn't contain
	 * exception stack trace, the list returned is empty. using the contents
	 * of this question: http://stackoverflow.com/questions/8938235/java-sort-an-array
	 */
	@Test
	public void emptyListIsReturnedForStringWithoutStackTrace() {
		assertEquals(new ArrayList<String>(), StackTraceExtractor.extract(
				("I'm trying to make a program that consists of an array of 10 integers which all has a random value, so far so good.\n"
						+ "\n"
						+ "However, now I need to sort them in order from lowest to highest value and then print it onto the screen, how would I go about doing so?\n"
						+ "\n"
						+ "(Sorry for having so much code for a program that small, I ain't that good with loops, just started working with Java)\n"
						+ "\n" + "public static void main(String args[])\n" + "{\n"
						+ "    int [] array = new int[10];\n" + "\n" + "    array[0] = ((int)(Math.random()*100+1));\n"
						+ "    array[1] = ((int)(Math.random()*100+1));\n"
						+ "    array[2] = ((int)(Math.random()*100+1));\n"
						+ "    array[3] = ((int)(Math.random()*100+1));\n"
						+ "    array[4] = ((int)(Math.random()*100+1));\n"
						+ "    array[5] = ((int)(Math.random()*100+1));\n"
						+ "    array[6] = ((int)(Math.random()*100+1));\n"
						+ "    array[7] = ((int)(Math.random()*100+1));\n"
						+ "    array[8] = ((int)(Math.random()*100+1));\n"
						+ "    array[9] = ((int)(Math.random()*100+1));\n" + "\n"
						+ "    System.out.println(array[0] +\" \" + array[1] +\" \" + array[2] +\" \" + array[3]\n"
						+ "    +\" \" + array[4] +\" \" + array[5]+\" \" + array[6]+\" \" + array[7]+\" \" \n"
						+ "    + array[8]+\" \" + array[9] );        \n" + "\n" + "}")));
	}
	
	/*
	 * The string in this test case contains only the stack trace without the whole question
	 */
	@Test
	public void listWithCorrectStackTraceIsReturnedForSimpleTraceWithoutQuestion() {
		String trace = "Exception in thread \"main\" java.lang.NullPointerException\n" + 
				"        at com.example.myproject.Book.getTitle(Book.java:16)\n" + 
				"        at com.example.myproject.Author.getBookTitles(Author.java:25)\n" + 
				"        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)";
		List<String> l = new ArrayList<>();
		l.add(trace);
		assertEquals(l,StackTraceExtractor.extract(trace));
	}
	
	/*
	 * The string in this test case contains the stackoverflow question with the stack trace
	 */
	@Test
	public void listWithCorrectStackTraceIsReturnedForSimpleTraceWithQuestion() {
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
		List<String> l = new ArrayList<>();
		l.add(trace);
		assertEquals(l, getTrimmedListOfTraces(question));
	}
	
	
	@Test
	public void listWithCorrectStackTracesIsReturnedForAnswerWithMultipleTraces() {
		String answer = "In simple terms, a stack trace is a list of the method calls that the application was in the middle of when an Exception was thrown.\n" + 
				"\n" + 
				"Simple Example\n" + 
				"\n" + 
				"With the example given in the question, we can determine exactly where the exception was thrown in the application. Let's have a look at the stack trace:\n" + 
				"\n" + 
				"Exception in thread \"main\" java.lang.NullPointerException\n" + 
				"        at com.example.myproject.Book.getTitle(Book.java:16)\n" + 
				"        at com.example.myproject.Author.getBookTitles(Author.java:25)\n" + 
				"        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)\n" + 
				"This is a very simple stack trace. If we start at the beginning of the list of \"at ...\", we can tell where our error happened. What we're looking for is the topmost method call that is part of our application. In this case, it's:\n" + 
				"\n" + 
				"at com.example.myproject.Book.getTitle(Book.java:16)\n" + 
				"To debug this, we can open up Book.java and look at line 16, which is:\n" + 
				"\n" + 
				"public String getTitle() {\n" + 
				"    System.out.println(title.toString()); <-- line 16\n" + 
				"    return title;\n" + 
				"}\n" + 
				"This would indicate that something (probably title) is null in the above code.\n" + 
				"\n" + 
				"Example with a chain of exceptions\n" + 
				"\n" + 
				"Sometimes applications will catch an Exception and re-throw it as the cause of another Exception. This typically looks like:\n" + 
				"\n" + 
				"try {\n" + 
				"....\n" + 
				"} catch (NullPointerException e) {\n" + 
				"  throw new IllegalStateException(\"A book has a null property\", e)\n" + 
				"}\n" + 
				"This might give you a stack trace that looks like:\n" + 
				"\n" + 
				"Exception in thread \"main\" java.lang.IllegalStateException: A book has a null property\n" + 
				"        at com.example.myproject.Author.getBookIds(Author.java:38)\n" + 
				"        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)\n" + 
				"Caused by: java.lang.NullPointerException\n" + 
				"        at com.example.myproject.Book.getId(Book.java:22)\n" + 
				"        at com.example.myproject.Author.getBookIds(Author.java:35)\n" + 
				"        ... 1 more\n" + 
				"What's different about this one is the \"Caused by\". Sometimes exceptions will have multiple \"Caused by\" sections. For these, you typically want to find the \"root cause\", which will be one of the lowest \"Caused by\" sections in the stack trace. In our case, it's:\n" + 
				"\n" + 
				"Caused by: java.lang.NullPointerException <-- root cause\n" + 
				"        at com.example.myproject.Book.getId(Book.java:22) <-- important line\n" + 
				"Again, with this exception we'd want to look at line 22 of Book.java to see what might cause the NullPointerException here.\n" + 
				"\n" + 
				"More daunting example with library code\n" + 
				"\n" + 
				"Usually stack traces are much more complex than the two examples above. Here's an example (it's a long one, but demonstrates several levels of chained exceptions):\n" + 
				"\n" + 
				"javax.servlet.ServletException: Something bad happened\n" + 
				"    at com.example.myproject.OpenSessionInViewFilter.doFilter(OpenSessionInViewFilter.java:60)\n" + 
				"    at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157)\n" + 
				"    at com.example.myproject.ExceptionHandlerFilter.doFilter(ExceptionHandlerFilter.java:28)\n" + 
				"    at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157)\n" + 
				"    at com.example.myproject.OutputBufferFilter.doFilter(OutputBufferFilter.java:33)\n" + 
				"    at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157)\n" + 
				"    at org.mortbay.jetty.servlet.ServletHandler.handle(ServletHandler.java:388)\n" + 
				"    at org.mortbay.jetty.security.SecurityHandler.handle(SecurityHandler.java:216)\n" + 
				"    at org.mortbay.jetty.servlet.SessionHandler.handle(SessionHandler.java:182)\n" + 
				"    at org.mortbay.jetty.handler.ContextHandler.handle(ContextHandler.java:765)\n" + 
				"    at org.mortbay.jetty.webapp.WebAppContext.handle(WebAppContext.java:418)\n" + 
				"    at org.mortbay.jetty.handler.HandlerWrapper.handle(HandlerWrapper.java:152)\n" + 
				"    at org.mortbay.jetty.Server.handle(Server.java:326)\n" + 
				"    at org.mortbay.jetty.HttpConnection.handleRequest(HttpConnection.java:542)\n" + 
				"    at org.mortbay.jetty.HttpConnection$RequestHandler.content(HttpConnection.java:943)\n" + 
				"    at org.mortbay.jetty.HttpParser.parseNext(HttpParser.java:756)\n" + 
				"    at org.mortbay.jetty.HttpParser.parseAvailable(HttpParser.java:218)\n" + 
				"    at org.mortbay.jetty.HttpConnection.handle(HttpConnection.java:404)\n" + 
				"    at org.mortbay.jetty.bio.SocketConnector$Connection.run(SocketConnector.java:228)\n" + 
				"    at org.mortbay.thread.QueuedThreadPool$PoolThread.run(QueuedThreadPool.java:582)\n" + 
				"Caused by: com.example.myproject.MyProjectServletException\n" + 
				"    at com.example.myproject.MyServlet.doPost(MyServlet.java:169)\n" + 
				"    at javax.servlet.http.HttpServlet.service(HttpServlet.java:727)\n" + 
				"    at javax.servlet.http.HttpServlet.service(HttpServlet.java:820)\n" + 
				"    at org.mortbay.jetty.servlet.ServletHolder.handle(ServletHolder.java:511)\n" + 
				"    at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1166)\n" + 
				"    at com.example.myproject.OpenSessionInViewFilter.doFilter(OpenSessionInViewFilter.java:30)\n" + 
				"    ... 27 more\n" + 
				"Caused by: org.hibernate.exception.ConstraintViolationException: could not insert: [com.example.myproject.MyEntity]\n" + 
				"    at org.hibernate.exception.SQLStateConverter.convert(SQLStateConverter.java:96)\n" + 
				"    at org.hibernate.exception.JDBCExceptionHelper.convert(JDBCExceptionHelper.java:66)\n" + 
				"    at org.hibernate.id.insert.AbstractSelectingDelegate.performInsert(AbstractSelectingDelegate.java:64)\n" + 
				"    at org.hibernate.persister.entity.AbstractEntityPersister.insert(AbstractEntityPersister.java:2329)\n" + 
				"    at org.hibernate.persister.entity.AbstractEntityPersister.insert(AbstractEntityPersister.java:2822)\n" + 
				"    at org.hibernate.action.EntityIdentityInsertAction.execute(EntityIdentityInsertAction.java:71)\n" + 
				"    at org.hibernate.engine.ActionQueue.execute(ActionQueue.java:268)\n" + 
				"    at org.hibernate.event.def.AbstractSaveEventListener.performSaveOrReplicate(AbstractSaveEventListener.java:321)\n" + 
				"    at org.hibernate.event.def.AbstractSaveEventListener.performSave(AbstractSaveEventListener.java:204)\n" + 
				"    at org.hibernate.event.def.AbstractSaveEventListener.saveWithGeneratedId(AbstractSaveEventListener.java:130)\n" + 
				"    at org.hibernate.event.def.DefaultSaveOrUpdateEventListener.saveWithGeneratedOrRequestedId(DefaultSaveOrUpdateEventListener.java:210)\n" + 
				"    at org.hibernate.event.def.DefaultSaveEventListener.saveWithGeneratedOrRequestedId(DefaultSaveEventListener.java:56)\n" + 
				"    at org.hibernate.event.def.DefaultSaveOrUpdateEventListener.entityIsTransient(DefaultSaveOrUpdateEventListener.java:195)\n" + 
				"    at org.hibernate.event.def.DefaultSaveEventListener.performSaveOrUpdate(DefaultSaveEventListener.java:50)\n" + 
				"    at org.hibernate.event.def.DefaultSaveOrUpdateEventListener.onSaveOrUpdate(DefaultSaveOrUpdateEventListener.java:93)\n" + 
				"    at org.hibernate.impl.SessionImpl.fireSave(SessionImpl.java:705)\n" + 
				"    at org.hibernate.impl.SessionImpl.save(SessionImpl.java:693)\n" + 
				"    at org.hibernate.impl.SessionImpl.save(SessionImpl.java:689)\n" + 
				"    at sun.reflect.GeneratedMethodAccessor5.invoke(Unknown Source)\n" + 
				"    at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)\n" + 
				"    at java.lang.reflect.Method.invoke(Method.java:597)\n" + 
				"    at org.hibernate.context.ThreadLocalSessionContext$TransactionProtectionWrapper.invoke(ThreadLocalSessionContext.java:344)\n" + 
				"    at $Proxy19.save(Unknown Source)\n" + 
				"    at com.example.myproject.MyEntityService.save(MyEntityService.java:59) <-- relevant call (see notes below)\n" + 
				"    at com.example.myproject.MyServlet.doPost(MyServlet.java:164)\n" + 
				"    ... 32 more\n" + 
				"Caused by: java.sql.SQLException: Violation of unique constraint MY_ENTITY_UK_1: duplicate value(s) for column(s) MY_COLUMN in statement [...]\n" + 
				"    at org.hsqldb.jdbc.Util.throwError(Unknown Source)\n" + 
				"    at org.hsqldb.jdbc.jdbcPreparedStatement.executeUpdate(Unknown Source)\n" + 
				"    at com.mchange.v2.c3p0.impl.NewProxyPreparedStatement.executeUpdate(NewProxyPreparedStatement.java:105)\n" + 
				"    at org.hibernate.id.insert.AbstractSelectingDelegate.performInsert(AbstractSelectingDelegate.java:57)\n" + 
				"    ... 54 more\n" + 
				"In this example, there's a lot more. What we're mostly concerned about is looking for methods that are from our code, which would be anything in the com.example.myproject package. From the second example (above), we'd first want to look down for the root cause, which is:\n" + 
				"\n" + 
				"Caused by: java.sql.SQLException\n" + 
				"However, all the method calls under that are library code. So we'll move up to the \"Caused by\" above it, and look for the first method call originating from our code, which is:\n" + 
				"\n" + 
				"at com.example.myproject.MyEntityService.save(MyEntityService.java:59)\n" + 
				"Like in previous examples, we should look at MyEntityService.java on line 59, because that's where this error originated (this one's a bit obvious what went wrong, since the SQLException states the error, but the debugging procedure is what we're after).";
		String trace1 = "Exception in thread \"main\" java.lang.NullPointerException\n" + 
				"        at com.example.myproject.Book.getTitle(Book.java:16)\n" + 
				"        at com.example.myproject.Author.getBookTitles(Author.java:25)\n" + 
				"        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)";
		String trace2 = "Exception in thread \"main\" java.lang.IllegalStateException: A book has a null property\n" + 
				"        at com.example.myproject.Author.getBookIds(Author.java:38)\n" + 
				"        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)\n" + 
				"Caused by: java.lang.NullPointerException\n" + 
				"        at com.example.myproject.Book.getId(Book.java:22)\n" + 
				"        at com.example.myproject.Author.getBookIds(Author.java:35)\n" + 
				"        ... 1 more";
		String trace3 = "Exception <-- root cause\n" + 
				"        at com.example.myproject.Book.getId(Book.java:22) <-- important line";
		String trace4 = "Exception: Something bad happened\n" + 
				"    at com.example.myproject.OpenSessionInViewFilter.doFilter(OpenSessionInViewFilter.java:60)\n" + 
				"    at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157)\n" + 
				"    at com.example.myproject.ExceptionHandlerFilter.doFilter(ExceptionHandlerFilter.java:28)\n" + 
				"    at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157)\n" + 
				"    at com.example.myproject.OutputBufferFilter.doFilter(OutputBufferFilter.java:33)\n" + 
				"    at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157)\n" + 
				"    at org.mortbay.jetty.servlet.ServletHandler.handle(ServletHandler.java:388)\n" + 
				"    at org.mortbay.jetty.security.SecurityHandler.handle(SecurityHandler.java:216)\n" + 
				"    at org.mortbay.jetty.servlet.SessionHandler.handle(SessionHandler.java:182)\n" + 
				"    at org.mortbay.jetty.handler.ContextHandler.handle(ContextHandler.java:765)\n" + 
				"    at org.mortbay.jetty.webapp.WebAppContext.handle(WebAppContext.java:418)\n" + 
				"    at org.mortbay.jetty.handler.HandlerWrapper.handle(HandlerWrapper.java:152)\n" + 
				"    at org.mortbay.jetty.Server.handle(Server.java:326)\n" + 
				"    at org.mortbay.jetty.HttpConnection.handleRequest(HttpConnection.java:542)\n" + 
				"    at org.mortbay.jetty.HttpConnection$RequestHandler.content(HttpConnection.java:943)\n" + 
				"    at org.mortbay.jetty.HttpParser.parseNext(HttpParser.java:756)\n" + 
				"    at org.mortbay.jetty.HttpParser.parseAvailable(HttpParser.java:218)\n" + 
				"    at org.mortbay.jetty.HttpConnection.handle(HttpConnection.java:404)\n" + 
				"    at org.mortbay.jetty.bio.SocketConnector$Connection.run(SocketConnector.java:228)\n" + 
				"    at org.mortbay.thread.QueuedThreadPool$PoolThread.run(QueuedThreadPool.java:582)\n" + 
				"Caused by: com.example.myproject.MyProjectServletException\n" + 
				"    at com.example.myproject.MyServlet.doPost(MyServlet.java:169)\n" + 
				"    at javax.servlet.http.HttpServlet.service(HttpServlet.java:727)\n" + 
				"    at javax.servlet.http.HttpServlet.service(HttpServlet.java:820)\n" + 
				"    at org.mortbay.jetty.servlet.ServletHolder.handle(ServletHolder.java:511)\n" + 
				"    at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1166)\n" + 
				"    at com.example.myproject.OpenSessionInViewFilter.doFilter(OpenSessionInViewFilter.java:30)\n" + 
				"    ... 27 more\n" + 
				"Caused by: org.hibernate.exception.ConstraintViolationException: could not insert: [com.example.myproject.MyEntity]\n" + 
				"    at org.hibernate.exception.SQLStateConverter.convert(SQLStateConverter.java:96)\n" + 
				"    at org.hibernate.exception.JDBCExceptionHelper.convert(JDBCExceptionHelper.java:66)\n" + 
				"    at org.hibernate.id.insert.AbstractSelectingDelegate.performInsert(AbstractSelectingDelegate.java:64)\n" + 
				"    at org.hibernate.persister.entity.AbstractEntityPersister.insert(AbstractEntityPersister.java:2329)\n" + 
				"    at org.hibernate.persister.entity.AbstractEntityPersister.insert(AbstractEntityPersister.java:2822)\n" + 
				"    at org.hibernate.action.EntityIdentityInsertAction.execute(EntityIdentityInsertAction.java:71)\n" + 
				"    at org.hibernate.engine.ActionQueue.execute(ActionQueue.java:268)\n" + 
				"    at org.hibernate.event.def.AbstractSaveEventListener.performSaveOrReplicate(AbstractSaveEventListener.java:321)\n" + 
				"    at org.hibernate.event.def.AbstractSaveEventListener.performSave(AbstractSaveEventListener.java:204)\n" + 
				"    at org.hibernate.event.def.AbstractSaveEventListener.saveWithGeneratedId(AbstractSaveEventListener.java:130)\n" + 
				"    at org.hibernate.event.def.DefaultSaveOrUpdateEventListener.saveWithGeneratedOrRequestedId(DefaultSaveOrUpdateEventListener.java:210)\n" + 
				"    at org.hibernate.event.def.DefaultSaveEventListener.saveWithGeneratedOrRequestedId(DefaultSaveEventListener.java:56)\n" + 
				"    at org.hibernate.event.def.DefaultSaveOrUpdateEventListener.entityIsTransient(DefaultSaveOrUpdateEventListener.java:195)\n" + 
				"    at org.hibernate.event.def.DefaultSaveEventListener.performSaveOrUpdate(DefaultSaveEventListener.java:50)\n" + 
				"    at org.hibernate.event.def.DefaultSaveOrUpdateEventListener.onSaveOrUpdate(DefaultSaveOrUpdateEventListener.java:93)\n" + 
				"    at org.hibernate.impl.SessionImpl.fireSave(SessionImpl.java:705)\n" + 
				"    at org.hibernate.impl.SessionImpl.save(SessionImpl.java:693)\n" + 
				"    at org.hibernate.impl.SessionImpl.save(SessionImpl.java:689)\n" + 
				"    at sun.reflect.GeneratedMethodAccessor5.invoke(Unknown Source)\n" + 
				"    at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)\n" + 
				"    at java.lang.reflect.Method.invoke(Method.java:597)\n" + 
				"    at org.hibernate.context.ThreadLocalSessionContext$TransactionProtectionWrapper.invoke(ThreadLocalSessionContext.java:344)\n" + 
				"    at $Proxy19.save(Unknown Source)\n" + 
				"    at com.example.myproject.MyEntityService.save(MyEntityService.java:59) <-- relevant call (see notes below)\n" + 
				"    at com.example.myproject.MyServlet.doPost(MyServlet.java:164)\n" + 
				"    ... 32 more\n" + 
				"Caused by: java.sql.SQLException: Violation of unique constraint MY_ENTITY_UK_1: duplicate value(s) for column(s) MY_COLUMN in statement [...]\n" + 
				"    at org.hsqldb.jdbc.Util.throwError(Unknown Source)\n" + 
				"    at org.hsqldb.jdbc.jdbcPreparedStatement.executeUpdate(Unknown Source)\n" + 
				"    at com.mchange.v2.c3p0.impl.NewProxyPreparedStatement.executeUpdate(NewProxyPreparedStatement.java:105)\n" + 
				"    at org.hibernate.id.insert.AbstractSelectingDelegate.performInsert(AbstractSelectingDelegate.java:57)\n" + 
				"    ... 54 more";
		List<String> l = new ArrayList<>();
		l.add(trace1);
		l.add(trace2);
		l.add(trace3);
		l.add(trace4);
		assertEquals(l, getTrimmedListOfTraces(answer));	
	}
}
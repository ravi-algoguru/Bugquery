<html>
<head>
<title>BugQuery - Search Results</title>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="bugquery-style.css">
</head>
<%@ page import="com.bugquery.serverside.website.InputResource"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="java.util.List"%>
<body>
	<div class="bugquery-header">
		<div class="wrapper">
			<ul>
				<li><a id="bugquery-icon" href="index.jsp"> <svg
							xmlns="http://www.w3.org/2000/svg" width="50" height="31.002">
							  <path
								d="M25 .12c-4.873 0-8.824 3.95-8.824 8.824 0 4.37 3.182 7.993 7.353 8.694v1.6c-2.942 0-7.354-2.942-14.706-4.412-7.353-1.47-8.824 0-8.824 1.47s2.94 2.942 5.882 4.412c2.942 1.47 4.412 0 4.412 0 2.94 1.47 3.677-1.47 12.5-.735-5.147 2.206-2.206-1.47-12.5 2.206 0 1.47 2.94 2.94 5.882 1.47 2.942 0 2.942-1.47 7.353-2.942v8.824c0 .812.658 1.47 1.47 1.47.812 0 1.47-.658 1.47-1.47v-8.824c4.412 1.47 4.412 2.94 7.354 2.94 2.94 1.472 5.882 0 5.882-1.47-10.294-3.676-7.353 0-12.5-2.205 8.823-.735 9.56 2.206 12.5.735 0 0 1.47 1.47 4.412 0 2.94-1.47 5.882-2.94 5.882-4.412 0-1.47-1.47-2.94-8.824-1.47-7.352 1.47-11.764 4.412-14.705 4.412v-1.6c4.172-.7 7.354-4.324 7.354-8.694C33.824 4.07 29.874.12 25 .12zm0 2.206c3.655 0 6.618 2.963 6.618 6.618 0 3.654-2.963 6.617-6.618 6.617-3.655 0-6.618-2.962-6.618-6.616 0-3.655 2.963-6.618 6.618-6.618z" />
							</svg>
				</a></li>
				<li><a class="button" href="guide.html">User Guide</a></li>
				<li><a class="button" href="submit.html">Submit Your Trace</a></li>
				<li style="float: right; margin: 0"><a class="button"
					href="https://github.com/TechnionYP5777/Bugquery">BugQuery on
						GitHub</a></li>
			</ul>
		</div>
	</div>
	<div class="wrapper">
		<% List<Object> results = new InputResource().getResults(); %>
		<h3>
			BugQuery Results, explore
			<%=results.size() %>
			results found:
		</h3>
		<ul>
			<% for (Object obj : results) { %>
			<li><%=obj%></li>
			<% } %>
		</ul>
		<div class="code-wrapper">
			<% 
					String[] trace = request.getParameterValues("q");
					if (trace != null && trace.length != 0) {
				%>
			<code><%=trace[0] %></code>
			<% } %>
		</div>
		<div class="bugquery-result">
			<a class="bugquery-result-label"
				href="http://stackoverflow.com/questions/3988788/what-is-a-stack-trace-and-how-can-i-use-it-to-debug-my-application-errors">Matched
				Trace, Original Post Title</a>
			<div class="code-wrapper">
				<code class="matched-trace">
					Exception in thread "main" java.lang.IllegalStateException: A book
					has a null property<br>&#9;at
					com.example.myproject.Author.getBookIds(Author.java:38)<br>&#9;at
					com.example.myproject.Bootstrap.main(Bootstrap.java:14)<br>Caused
					by: java.lang.NullPointerException<br>&#9;at
					com.example.myproject.Book.getId(Book.java:22)<br>&#9;at
					com.example.myproject.Author.getBookIds(Author.java:36)<br>&#9;...
					1 more
				</code>
			</div>
			<p>What's different about this one is the "Caused by". Sometimes
				exceptions will have multiple "Caused by" sections. For these, you
				typically want to find the "root cause", which will be one of the
				lowest "Caused by" sections in the stack trace. In our case, it's.</p>
		</div>
		<div class="bugquery-result">
			<a class="bugquery-result-label"
				href="http://stackoverflow.com/questions/3988788/what-is-a-stack-trace-and-how-can-i-use-it-to-debug-my-application-errors">Replace
				with the original post title of the Second Matched Trace, which has
				multiple lines, just for web design testing purposes</a>
			<div class="code-wrapper">
				<code class="matched-trace">
					javax.servlet.ServletException: Something bad happened<br>&#9;at
					com.example.myproject.OpenSessionInViewFilter.doFilter(OpenSessionInViewFilter.java:60)<br>&#9;at
					org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157)<br>&#9;at
					com.example.myproject.ExceptionHandlerFilter.doFilter(ExceptionHandlerFilter.java:28)<br>&#9;at
					org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157)<br>&#9;at
					com.example.myproject.OutputBufferFilter.doFilter(OutputBufferFilter.java:33)<br>&#9;at
					org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157)<br>&#9;at
					org.mortbay.jetty.servlet.ServletHandler.handle(ServletHandler.java:388)<br>&#9;at
					org.mortbay.jetty.security.SecurityHandler.handle(SecurityHandler.java:216)<br>&#9;at
					org.mortbay.jetty.servlet.SessionHandler.handle(SessionHandler.java:182)<br>&#9;at
					org.mortbay.jetty.handler.ContextHandler.handle(ContextHandler.java:765)<br>&#9;at
					org.mortbay.jetty.webapp.WebAppContext.handle(WebAppContext.java:418)<br>&#9;at
					org.mortbay.jetty.handler.HandlerWrapper.handle(HandlerWrapper.java:152)<br>&#9;at
					org.mortbay.jetty.Server.handle(Server.java:326)<br>&#9;at
					org.mortbay.jetty.HttpConnection.handleRequest(HttpConnection.java:542)<br>&#9;at
					org.mortbay.jetty.HttpConnection$RequestHandler.content(HttpConnection.java:943)<br>&#9;at
					org.mortbay.jetty.HttpParser.parseNext(HttpParser.java:756)<br>&#9;at
					org.mortbay.jetty.HttpParser.parseAvailable(HttpParser.java:218)<br>&#9;at
					org.mortbay.jetty.HttpConnection.handle(HttpConnection.java:404)<br>&#9;at
					org.mortbay.jetty.bio.SocketConnector$Connection.run(SocketConnector.java:228)<br>&#9;at
					org.mortbay.thread.QueuedThreadPool$PoolThread.run(QueuedThreadPool.java:582)<br>Caused
					by: com.example.myproject.MyProjectServletException<br>&#9;at
					com.example.myproject.MyServlet.doPost(MyServlet.java:169)<br>&#9;at
					javax.servlet.http.HttpServlet.service(HttpServlet.java:727)<br>&#9;at
					javax.servlet.http.HttpServlet.service(HttpServlet.java:820)<br>&#9;at
					org.mortbay.jetty.servlet.ServletHolder.handle(ServletHolder.java:511)<br>&#9;at
					org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1166)<br>&#9;at
					com.example.myproject.OpenSessionInViewFilter.doFilter(OpenSessionInViewFilter.java:30)<br>&#9;...
					54 more
				</code>
			</div>
			<p>A stacktrace is a very helpful debugging tool. It shows you
				the call stack (meaning, the stack of functions that were called up
				to that point) at the time an uncaught exception was thrown (or the
				time the stacktrace was generated manually). This is very useful
				because it doesn't only show you where the error happened, but also
				how the program ended up in that place of the code.</p>
		</div>
	</div>
	<div class="bugquery-footer">
		<div class="wrapper">
			<p>
				© 2016 BugQuery.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="index.jsp">Home</a>&nbsp;-&nbsp;<a
					href="guide.html">User Guide</a>&nbsp;-&nbsp;<a href="submit.html">Submit
					Your Trace</a>&nbsp;-&nbsp;<a
					href="https://github.com/TechnionYP5777/Bugquery">BugQuery on
					GitHub</a>
			</p>
		</div>
	</div>
</body>
</html>

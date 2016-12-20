package com.bugquery.serverside.entities;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author rodedzats
 *
 */
public class StackTraceTest {

	
	@Test
	public void correctExceptionForSimpleStackTrace() {
		assertEquals("java.lang.NullPointerException",
				(new StackTrace(("Exception in thread \"main\" java.lang.NullPointerException\n"
						+ "        at com.example.myproject.Book.getTitle(Book.java:16)\n"
						+ "        at com.example.myproject.Author.getBookTitles(Author.java:25)\n"
						+ "        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)"))).getException());
	}
	
	@Test
	public void correctExceptionForMediumComplexityStackTrace() {
		assertEquals("java.lang.NullPointerException",
				(new StackTrace(
						("Exception in thread \"main\" java.lang.IllegalStateException: A book has a null property\n"
								+ "        at com.example.myproject.Author.getBookIds(Author.java:38)\n"
								+ "        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)\n"
								+ "Caused by: java.lang.NullPointerException\n"
								+ "        at com.example.myproject.Book.getId(Book.java:22)\n"
								+ "        at com.example.myproject.Author.getBookIds(Author.java:35)\n"
								+ "        ... 1 more"))).getException());
	}
	
	@Test
	public void correctExceptionForComplexStackTrace() {
		assertEquals("java.sql.SQLException",
				(new StackTrace(("Exception: Something bad happened\n"
						+ "    at com.example.myproject.OpenSessionInViewFilter.doFilter(OpenSessionInViewFilter.java:60)\n"
						+ "    at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157)\n"
						+ "    at com.example.myproject.ExceptionHandlerFilter.doFilter(ExceptionHandlerFilter.java:28)\n"
						+ "    at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157)\n"
						+ "    at com.example.myproject.OutputBufferFilter.doFilter(OutputBufferFilter.java:33)\n"
						+ "    at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157)\n"
						+ "    at org.mortbay.jetty.servlet.ServletHandler.handle(ServletHandler.java:388)\n"
						+ "    at org.mortbay.jetty.security.SecurityHandler.handle(SecurityHandler.java:216)\n"
						+ "    at org.mortbay.jetty.servlet.SessionHandler.handle(SessionHandler.java:182)\n"
						+ "    at org.mortbay.jetty.handler.ContextHandler.handle(ContextHandler.java:765)\n"
						+ "    at org.mortbay.jetty.webapp.WebAppContext.handle(WebAppContext.java:418)\n"
						+ "    at org.mortbay.jetty.handler.HandlerWrapper.handle(HandlerWrapper.java:152)\n"
						+ "    at org.mortbay.jetty.Server.handle(Server.java:326)\n"
						+ "    at org.mortbay.jetty.HttpConnection.handleRequest(HttpConnection.java:542)\n"
						+ "    at org.mortbay.jetty.HttpConnection$RequestHandler.content(HttpConnection.java:943)\n"
						+ "    at org.mortbay.jetty.HttpParser.parseNext(HttpParser.java:756)\n"
						+ "    at org.mortbay.jetty.HttpParser.parseAvailable(HttpParser.java:218)\n"
						+ "    at org.mortbay.jetty.HttpConnection.handle(HttpConnection.java:404)\n"
						+ "    at org.mortbay.jetty.bio.SocketConnector$Connection.run(SocketConnector.java:228)\n"
						+ "    at org.mortbay.thread.QueuedThreadPool$PoolThread.run(QueuedThreadPool.java:582)\n"
						+ "Caused by: com.example.myproject.MyProjectServletException\n"
						+ "    at com.example.myproject.MyServlet.doPost(MyServlet.java:169)\n"
						+ "    at javax.servlet.http.HttpServlet.service(HttpServlet.java:727)\n"
						+ "    at javax.servlet.http.HttpServlet.service(HttpServlet.java:820)\n"
						+ "    at org.mortbay.jetty.servlet.ServletHolder.handle(ServletHolder.java:511)\n"
						+ "    at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1166)\n"
						+ "    at com.example.myproject.OpenSessionInViewFilter.doFilter(OpenSessionInViewFilter.java:30)\n"
						+ "    ... 27 more\n"
						+ "Caused by: org.hibernate.exception.ConstraintViolationException: could not insert: [com.example.myproject.MyEntity]\n"
						+ "    at org.hibernate.exception.SQLStateConverter.convert(SQLStateConverter.java:96)\n"
						+ "    at org.hibernate.exception.JDBCExceptionHelper.convert(JDBCExceptionHelper.java:66)\n"
						+ "    at org.hibernate.id.insert.AbstractSelectingDelegate.performInsert(AbstractSelectingDelegate.java:64)\n"
						+ "    at org.hibernate.persister.entity.AbstractEntityPersister.insert(AbstractEntityPersister.java:2329)\n"
						+ "    at org.hibernate.persister.entity.AbstractEntityPersister.insert(AbstractEntityPersister.java:2822)\n"
						+ "    at org.hibernate.action.EntityIdentityInsertAction.execute(EntityIdentityInsertAction.java:71)\n"
						+ "    at org.hibernate.engine.ActionQueue.execute(ActionQueue.java:268)\n"
						+ "    at org.hibernate.event.def.AbstractSaveEventListener.performSaveOrReplicate(AbstractSaveEventListener.java:321)\n"
						+ "    at org.hibernate.event.def.AbstractSaveEventListener.performSave(AbstractSaveEventListener.java:204)\n"
						+ "    at org.hibernate.event.def.AbstractSaveEventListener.saveWithGeneratedId(AbstractSaveEventListener.java:130)\n"
						+ "    at org.hibernate.event.def.DefaultSaveOrUpdateEventListener.saveWithGeneratedOrRequestedId(DefaultSaveOrUpdateEventListener.java:210)\n"
						+ "    at org.hibernate.event.def.DefaultSaveEventListener.saveWithGeneratedOrRequestedId(DefaultSaveEventListener.java:56)\n"
						+ "    at org.hibernate.event.def.DefaultSaveOrUpdateEventListener.entityIsTransient(DefaultSaveOrUpdateEventListener.java:195)\n"
						+ "    at org.hibernate.event.def.DefaultSaveEventListener.performSaveOrUpdate(DefaultSaveEventListener.java:50)\n"
						+ "    at org.hibernate.event.def.DefaultSaveOrUpdateEventListener.onSaveOrUpdate(DefaultSaveOrUpdateEventListener.java:93)\n"
						+ "    at org.hibernate.impl.SessionImpl.fireSave(SessionImpl.java:705)\n"
						+ "    at org.hibernate.impl.SessionImpl.save(SessionImpl.java:693)\n"
						+ "    at org.hibernate.impl.SessionImpl.save(SessionImpl.java:689)\n"
						+ "    at sun.reflect.GeneratedMethodAccessor5.invoke(Unknown Source)\n"
						+ "    at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)\n"
						+ "    at java.lang.reflect.Method.invoke(Method.java:597)\n"
						+ "    at org.hibernate.context.ThreadLocalSessionContext$TransactionProtectionWrapper.invoke(ThreadLocalSessionContext.java:344)\n"
						+ "    at $Proxy19.save(Unknown Source)\n"
						+ "    at com.example.myproject.MyEntityService.save(MyEntityService.java:59) <-- relevant call (see notes below)\n"
						+ "    at com.example.myproject.MyServlet.doPost(MyServlet.java:164)\n" + "    ... 32 more\n"
						+ "Caused by: java.sql.SQLException: Violation of unique constraint MY_ENTITY_UK_1: duplicate value(s) for column(s) MY_COLUMN in statement [...]\n"
						+ "    at org.hsqldb.jdbc.Util.throwError(Unknown Source)\n"
						+ "    at org.hsqldb.jdbc.jdbcPreparedStatement.executeUpdate(Unknown Source)\n"
						+ "    at com.mchange.v2.c3p0.impl.NewProxyPreparedStatement.executeUpdate(NewProxyPreparedStatement.java:105)\n"
						+ "    at org.hibernate.id.insert.AbstractSelectingDelegate.performInsert(AbstractSelectingDelegate.java:57)\n"
						+ "    ... 54 more"))).getException());
	}
	
	@Test
	public void correctExceptionForIssue37_2() {
		assertEquals("java.lang.UnsatisfiedLinkError",
				new StackTrace(
						("Exception in thread \"main\" java.lang.UnsatisfiedLinkError: C:\\Program Files\\Java\\jre1.6.0_05\\bin\\awt.dll: The specified procedure could not be found\n"
								+ "at java.lang.ClassLoader$NativeLibrary.load(Native Method)\n"
								+ "    at java.lang.ClassLoader.loadLibrary0(Unknown Source)\n"
								+ "    at java.lang.ClassLoader.loadLibrary(Unknown Source)\n"
								+ "    at java.lang.Runtime.loadLibrary0(Unknown Source)\n"
								+ "    at java.lang.System.loadLibrary(Unknown Source)\n"
								+ "    at sun.security.action.LoadLibraryAction.run(Unknown Source)\n"
								+ "    at java.security.AccessController.doPrivileged(Native Method)\n"
								+ "    at sun.awt.NativeLibLoader.loadLibraries(Unknown Source)\n"
								+ "    at sun.awt.DebugHelper.<clinit>(Unknown Source)\n"
								+ "    at java.awt.EventQueue.<clinit>(Unknown Source)\n"
								+ "    at javax.swing.SwingUtilities.invokeLater(Unknown Source)\n"
								+ "    at ui.sequencer.test.WindowTest.main(WindowTest.java:136)")).getException());
	}
	
	@Test
	public void correctExceptionForIssue37_1() {
		assertEquals("java.lang.IllegalArgumentException",
				new StackTrace(("Caused by: java.lang.IllegalArgumentException: Class file name must end with .class\n"
						+ "at org.eclipse.jdt.internal.core.PackageFragment.getClassFile(PackageFragment.java:182)\n"
						+ "at org.eclipse.jdt.internal.core.util.HandleFactory.createOpenable(HandleFactory.java:109)\n"
						+ "at org.eclipse.jdt.internal.core.search.matching.MatchLocator.locateMatches(MatchLocator.java:1177)\n"
						+ "at org.eclipse.jdt.internal.core.search.JavaSearchParticipant.locateMatches(JavaSearchParticipant.java:94)\n"
						+ "at org.eclipse.jdt.internal.core.search.BasicSearchEngine.findMatches(BasicSearchEngine.java:223)\n"
						+ "at org.eclipse.jdt.internal.core.search.BasicSearchEngine.search(BasicSearchEngine.java:506)\n"
						+ "at org.eclipse.jdt.core.search.SearchEngine.search(SearchEngine.java:551)\n"
						+ "at org.eclipse.jdt.internal.corext.refactoring.RefactoringSearchEngine.internalSearch(RefactoringSearchEngine.java:142)\n"
						+ "at org.eclipse.jdt.internal.corext.refactoring.RefactoringSearchEngine.search(RefactoringSearchEngine.java:129)\n"
						+ "at org.eclipse.jdt.internal.corext.refactoring.rename.RenameTypeProcessor.initializeReferences(RenameTypeProcessor.java:594)\n"
						+ "at org.eclipse.jdt.internal.corext.refactoring.rename.RenameTypeProcessor.doCheckFinalConditions(RenameTypeProcessor.java:522)\n"
						+ "at org.eclipse.jdt.internal.corext.refactoring.rename.JavaRenameProcessor.checkFinalConditions(JavaRenameProcessor.java:45)\n"
						+ "at org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring.checkFinalConditions(ProcessorBasedRefactoring.java:225)\n"
						+ "at org.eclipse.ltk.core.refactoring.Refactoring.checkAllConditions(Refactoring.java:160)\n"
						+ "at org.eclipse.jdt.internal.ui.refactoring.RefactoringExecutionHelper$Operation.run(RefactoringExecutionHelper.java:77)\n"
						+ "at org.eclipse.jdt.internal.core.BatchOperation.executeOperation(BatchOperation.java:39)\n"
						+ "at org.eclipse.jdt.internal.core.JavaModelOperation.run(JavaModelOperation.java:709)\n"
						+ "at org.eclipse.core.internal.resources.Workspace.run(Workspace.java:1800)\n"
						+ "at org.eclipse.jdt.core.JavaCore.run(JavaCore.java:4650)\n"
						+ "at org.eclipse.jdt.internal.ui.actions.WorkbenchRunnableAdapter.run(WorkbenchRunnableAdapter.java:92)\n"
						+ "at org.eclipse.jface.operation.ModalContext$ModalContextThread.run(ModalContext.java:121)"))
								.getException());
	}

}
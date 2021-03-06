package com.bugquery.console;

import org.eclipse.jface.action.*;
import org.eclipse.ui.console.*;
import org.eclipse.ui.part.IPageBookViewPage;

/**
 * Adds BugQuery button to TextConsole toolbar on initialization
 * 
 * @author Yosef
 * @since May 9, 2017
 */
public class ConsoleExtender implements IConsolePageParticipant {

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return null;
	}
	
	/**
	 * initializing the ConsoleExtender and adding the button to the tool bar 
	 */
	@Override
	public void init(IPageBookViewPage p, IConsole c) {
		TextConsole textConsole = (TextConsole) c;
		Action action = new ConsoleQuery(textConsole);
		IToolBarManager toolBarManager = p.getSite().getActionBars()
				.getToolBarManager();
		toolBarManager.appendToGroup(IConsoleConstants.OUTPUT_GROUP,
				new Separator());
		toolBarManager.appendToGroup(IConsoleConstants.OUTPUT_GROUP, action);
	}

	@Override
	public void dispose() {

	}

	@Override
	public void activated() {

	}

	@Override
	public void deactivated() {
	}

}
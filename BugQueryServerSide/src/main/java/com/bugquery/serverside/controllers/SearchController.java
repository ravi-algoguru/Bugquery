package com.bugquery.serverside.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bugquery.serverside.entities.Post;
import com.bugquery.serverside.entities.StackSearch;
import com.bugquery.serverside.entities.StackSearch.Status;
import com.bugquery.serverside.exceptions.InvalidStackTraceException;
import com.bugquery.serverside.exceptions.ResourceNotFoundException;
import com.bugquery.serverside.repositories.StackSearchRepository;
import com.bugquery.serverside.stacktrace.StackTraceRetriever;

/**
 * Hook to handle {@link #REQUEST_FORMAT}
 * 
 * @author AmitOhayon
 * @since 24.12.2016
 * Controller for stack searches
 * 
 */
@Controller
public class SearchController {

	public static final String REQUEST_FORMAT = "/stacks/{id}";
	@Autowired
	private StackSearchRepository repository;

	@Autowired
	private StackTraceRetriever retriever;

	@RequestMapping(value = REQUEST_FORMAT, method = RequestMethod.GET)
	public String getSearchResults(@PathVariable Long id, Model m) throws InvalidStackTraceException {
		StackSearch ss = repository.findOne(id);
		if (ss == null)
			throw new ResourceNotFoundException("Search id \"" + id + "\" could not be found.");
		if (ss.status == Status.NEW) {
			ss.getReady(retriever);
			repository.save(ss);
		}
		m.addAttribute("trace", ss.getTrace());
		m.addAttribute("results", getResults(ss));
		return "result";
	}

	@RequestMapping(value = "/stacks", method = RequestMethod.POST)
	public String addStackSearch(@RequestBody String input, Model m) throws InvalidStackTraceException {
		String trace = input.substring("trace=".length());
		m.addAttribute("stackId", repository.save(new StackSearch(trace)).getId());
		return "loading";
	}

	public List<Post> getResults(StackSearch s) {
		return retriever.getPostsByIds(s.getRelatedPostsIds());
	}
}

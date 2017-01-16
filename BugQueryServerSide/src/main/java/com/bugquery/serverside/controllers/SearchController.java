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
import com.bugquery.serverside.exceptions.GeneralDBException;
import com.bugquery.serverside.exceptions.InternalServerException;
import com.bugquery.serverside.exceptions.InvalidStackTraceException;
import com.bugquery.serverside.exceptions.ResourceNotFoundException;
import com.bugquery.serverside.repositories.StackSearchRepository;
import com.bugquery.serverside.stacktrace.GeneralStackTraceRetriever;

@Controller
public class SearchController {

	@Autowired
	private StackSearchRepository repository;

	@RequestMapping(value = "/stacks/{id}", method = RequestMethod.GET)
	public String getSearchResults(@PathVariable Long id, Model m) {
		StackSearch ss = repository.findOne(id);
		if (ss == null)
			throw new ResourceNotFoundException("Search id \"" + id + "\" could not be found.");
		String trace = ss.getTrace();

		List<Post> $;
		try {
			$ = getResults(trace);
		} catch (GeneralDBException | InvalidStackTraceException ¢) {
			throw new InternalServerException(¢);
		}

		m.addAttribute("trace", trace);
		m.addAttribute("results", $);
		return "result";
	}

	@RequestMapping(value = "/stacks", method = RequestMethod.POST)
	public String addStackSearch(@RequestBody String input) {
		String trace = input.substring("trace=".length());
		StackSearch $ = new StackSearch(trace);
		repository.save($);
		return "redirect:stacks/" + $.getId();
	}

	public static List<Post> getResults(String trace) throws GeneralDBException, InvalidStackTraceException {
		return new GeneralStackTraceRetriever().getMostRelevantPosts(trace, 10);
	}

	public void setRepository(StackSearchRepository ¢) {
		this.repository = ¢;
	}
}

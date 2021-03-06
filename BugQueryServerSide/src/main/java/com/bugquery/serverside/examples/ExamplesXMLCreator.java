package com.bugquery.serverside.examples;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.bugquery.serverside.Application;
import com.bugquery.serverside.entities.Post;
import com.bugquery.serverside.exceptions.GeneralDBException;
import com.bugquery.serverside.exceptions.InternalServerException;
import com.bugquery.serverside.exceptions.InvalidStackTraceException;
import com.bugquery.serverside.stacktrace.StackTraceExtractor;
import com.bugquery.serverside.stacktrace.StackTraceRetriever;


@Service
public class ExamplesXMLCreator {
	private StackTraceRetriever retriever;
	
	@Autowired
	ExamplesXMLCreator(ApplicationArguments args, StackTraceRetriever retriever) {
		this.retriever = retriever;
		if (args.containsOption("createExamples"))
			createExamplesXML();
	}
	
	public void createExamplesXML(){	
		for (String exceptionType : new ExamplesParser().getExceptionTypes())
			createXML(exceptionType);
	}
	
	private void createXML(String exceptionType){
		String stackTrace = new ExamplesParser().getStackTraceByExceptionType(exceptionType);
		List<Post> $;
		try {
			$ = getResults(stackTrace);
		} catch (GeneralDBException | InvalidStackTraceException ¢) {
			throw new InternalServerException(¢);
		}
		createAndSaveXMLFile(exceptionType,$);
	}
	
	private static void createAndSaveXMLFile(String exceptionType, List<Post> $) {
		  try {

				// root element
				Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
				Element rootElement = doc.createElement("posts");
				doc.appendChild(rootElement);
				
				for (Post p : $){
					// post element
					Element post = doc.createElement("post");
					rootElement.appendChild(post);
					
					// set stacktrace
					Attr stacktrace = doc.createAttribute("stacktrace");
					stacktrace.setValue(StackTraceExtractor.removeHtmlTags(p.getStackTrace().getContent()));
					post.setAttributeNode(stacktrace);
					
					// set question
					Attr question = doc.createAttribute("question");
					question.setValue(StackTraceExtractor.removeHtmlTags(p.getQuestion()));
					post.setAttributeNode(question);
					
					// set answer
					Attr answer = doc.createAttribute("answer");
					answer.setValue(StackTraceExtractor.removeHtmlTags(p.getAnswerId()));
					post.setAttributeNode(answer);
				}
				
				// write the content into xml file
				LoggerFactory.getLogger(Application.class).info(exceptionType);
				String filePath = new ExamplesParser().getLocation(exceptionType);
				LoggerFactory.getLogger(Application.class).info(filePath);
				TransformerFactory.newInstance().newTransformer().transform(new DOMSource(doc),
						new StreamResult(new File(filePath)));

		  } catch (ParserConfigurationException pce) {
				pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }	
	}

	public List<Post> getResults(String trace) throws GeneralDBException, InvalidStackTraceException {
		return retriever.getMostRelevantPosts(trace, 10);
	}
}

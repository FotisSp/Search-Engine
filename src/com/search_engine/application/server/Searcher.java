package com.search_engine.application.server;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;

import org.apache.lucene.document.Document;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Searcher 
{
	private IndexSearcher searcher = null;
	private IndexSearcher reviewSearcher = null;
	private Path indexPath;
	private Path reviewPath;
    private QueryParser parser = null;
    private Query query;
    private DirectoryReader indexReader;
    private DirectoryReader reviewReader;


    /** Creates a new instance of SearchEngine */
    public Searcher() throws IOException {
    	indexPath = Paths.get("index");
    	Directory indexDirectory = FSDirectory.open(new File(indexPath.toString()).toPath());
	    indexReader = DirectoryReader.open(indexDirectory);
        searcher = new IndexSearcher(indexReader);
        parser = new QueryParser("content", new StandardAnalyzer());
    }
    
    public Searcher(String businessId) throws IOException
    {
    	reviewPath = Paths.get("index/reviews");
    	Directory indexDirectory = FSDirectory.open(new File(reviewPath.toString()).toPath());
    	reviewReader = DirectoryReader.open(indexDirectory);
    	reviewSearcher = new IndexSearcher(reviewReader);
        parser = new QueryParser("Business Id", new StandardAnalyzer());
    }

    public TopDocs performSearch(String queryString)
    throws IOException, ParseException {
        query = parser.parse(queryString);
        return searcher.search(query, indexReader.numDocs());
    }
    
    public TopDocs performReviewSearch(String queryString)
    throws IOException, ParseException {
        query = parser.parse(queryString);
        TopDocs doc = reviewSearcher.search(query, reviewReader.numDocs());
        if(doc.totalHits == 0)
        {
        	return null;
        }
        return doc;
    }

    public Document getDocument(int docId)
    throws IOException {
        return searcher.doc(docId);
    }
    
    public Document getReviewDocument(int docId)
    throws IOException {
        return reviewSearcher.doc(docId);
    }
}

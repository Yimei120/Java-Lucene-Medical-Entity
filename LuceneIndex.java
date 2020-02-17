package LuceneFirst;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.util.Version;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class LuceneIndex {
	public LuceneIndex(String dataPath, String indexPath) throws IOException, ParseException {

		StandardAnalyzer analyzer = new StandardAnalyzer();

		// 1. create the index
		Directory index = new MMapDirectory(Paths.get(indexPath));

		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter w = new IndexWriter(index, config);

		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(dataPath));
			String line = reader.readLine();

			while (reader.readLine() != null) {
				line = reader.readLine();
				String[] eachLine = line.split("\\|");

				if (eachLine[1].equals("ENG")) {
					addDoc(w, eachLine[14], eachLine[7], eachLine[0]);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				w.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	private static void addDoc(IndexWriter w, String STR, String AUI, String CUI) throws IOException {
		Document doc = new Document();
		doc.add(new TextField("STR", STR, Field.Store.YES));
		doc.add(new StringField("CUI", CUI, Field.Store.YES));
		doc.add(new StringField("AUI", AUI, Field.Store.YES));

		w.addDocument(doc);
		System.out.println(doc);
	}

	public static void main(String[] args) throws IOException, ParseException {

		if (args == null || args.length < 2) {
			System.err.println("Usage: java -jar LuceneIndex.jar DATA_PATH INDEX_PATH");
			System.exit(1);
		} else {
			new LuceneIndex(args[0], args[1]);
		}
	}
}

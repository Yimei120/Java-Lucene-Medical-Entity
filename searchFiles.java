package LuceneFirst;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.*;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.util.Scanner;

public class searchFiles {

	/*String index = null;
	String query = null;
	String num = null;
	
	public searchFiles(String[] args) throws IOException {
		
		for (int i = 0; i < args.length; i++) {
			if ("-index".equals(args[i])) {
				index = args[i + 1];
				i++;
			} else if ("-query".equals(args[i])) {
				query = args[i + 1];
				i++;
			}else if("-searchNum".equals(args[i])) {
				num = args[i+1];
			}
			
		}
		if(index == null){
			System.out.println("Please provide the index path.");
			System.exit(1);
		}
		
		indexSearch(index, query, num);
	
	}
	*/
	public static Version luceneVersion = Version.LATEST;

	public searchFiles(String indexPath, String query2, String k)throws IOException{
        String res = "";
        DirectoryReader reader = null;
        try{

             Directory directory = FSDirectory.open(Paths.get(indexPath));//"E:/medical ontology/MRCONSO/indexfull"

             reader = DirectoryReader.open(directory);

             IndexSearcher searcher =  new IndexSearcher(reader);

             QueryParser parser = new QueryParser("STR",new StandardAnalyzer());//content表示搜索的域或者说字段
             Query query = parser.parse(query2);//被搜索的内容

             TopDocs tds = searcher.search(query, Integer.parseInt(k));//查询20条记录
//            根据TopDocs获取ScoreDoc
             ScoreDoc[] sds = tds.scoreDocs;
             
//            7、根据Searcher和ScoreDoc获取搜索到的document对象
             System.out.println("Found " + sds.length + " hits.");
             
             int cou = 0;
             for (ScoreDoc sd:sds) {
            	cou++;
            	Document d = searcher.doc(sd.doc);
            	res += cou + ". "+ d.get("AUI") +"\t"+ d.get("CUI")+ "\t" +d.get("STR")+ "\t"+ query2+ " Score: "+ sd.score +"\n" ;
            	
             }
             System.out.println(res);
 
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
          
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       
    }
                
 
 	public static void main(String[] args) throws IOException {

		if (args == null || args.length < 3) {
			System.err.println("Usage: java -jar searchFiles.jar INDEX_PATH QUERY NUMBER");
			System.exit(1);
		}else{
			new searchFiles(args[0],args[1], args[2]);
		}
	}


 	}

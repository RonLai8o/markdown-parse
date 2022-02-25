import static org.junit.Assert.*;
import org.junit.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class MarkdownParseTest {
    @Test
    public void addition() {
        assertEquals(2, 1 + 1);
    }
    
    @Test
    public void parse() throws IOException{
        ArrayList<String>[] list = new ArrayList[8];
        list[0] = new ArrayList();
        list[1] = new ArrayList();
        list[2] = new ArrayList();
        list[3] = new ArrayList();
        list[4] = new ArrayList();
        list[5] = new ArrayList();
        list[6] = new ArrayList();
        list[7] = new ArrayList();

        list[0].add("http://something.com");
        list[0].add("http://somemorething.com");
        list[1].add("https://something.com");
        list[1].add("some-page.html");
        list[2].add("www.something.com");  
        list[2].add("www.something.com");
        list[3].add("https://something.com");
        list[3].add("some-page.html");
        list[4].add("www.something.com");
        list[4].add("www.something.com/(VSCODE)/something");
        list[5].add("`google.com");
        list[5].add("google.com");
        list[5].add("ucsd.edu");
        list[6].add("a.com");
        list[6].add("a.com(())");
        list[6].add("example.com");
        list[7].add("https://www.twitter.com");
        list[7].add("https://ucsd-cse15l-w22.github.io/");
        list[7].add("https://cse.ucsd.edu/");
    

        for(int i = 0; i<5; i++){
            String file = load("Markdown" + (i+1) + ".md");
            assertEquals(list[i], MarkdownParse.getLinks(file));
        }
        for(int j = 5; j<8;j++){
            String file = load("Snippet" + (j-4) + ".md");
            assertEquals(list[j], MarkdownParse.getLinks(file));
        }
    }

    private String load(String words) throws IOException{
		Path fileName = Path.of(words);
	    String contents = Files.readString(fileName);
        return contents;
    }
}

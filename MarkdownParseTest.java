import static org.junit.Assert.*;
import org.junit.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class MarkdownParseTest {
    List<String> strList;
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

        for(int i = 0; i<5; i++){
            String file = load("Markdown" + (i+1) + ".md");
            assertEquals(list[i], MarkdownParse.getLinks(file));
        }
    }

    private String load(String words) throws IOException{
		Path fileName = Path.of(words);
	    String contents = Files.readString(fileName);
        return contents;
    }
    @Test
    public void snippet1() throws IOException{
        Path fileName = Path.of("Snippet1.md");
        String contents = Files.readString(fileName);
        this.strList = MarkdownParse.getLinks(contents);

        assertEquals(List.of("%60google.com","google.com","ucsd.edu"), this.strList);

    }
    
    @Test
    public void snippet2() throws IOException{
        Path fileName = Path.of("Snippet2.md");
        String contents = Files.readString(fileName);
        this.strList = MarkdownParse.getLinks(contents);

        assertEquals(List.of("a.com","a.com(())","example.com"), this.strList);

    }

    @Test
    public void snippet3() throws IOException{
        Path fileName = Path.of("Snippet3.md");
        String contents = Files.readString(fileName);
        this.strList = MarkdownParse.getLinks(contents);

        assertEquals(List.of("https://www.twitter.com","https://ucsd-cse15l-w22.github.io/","https://cse.ucsd.edu/"), this.strList);

    }

}

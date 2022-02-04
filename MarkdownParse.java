// File reading code from https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MarkdownParse {
    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        // find the next [, then find the ], then find the (, then take up to
        // the next )                     
        int currentIndex = 0;
        while(currentIndex < markdown.length()) {
            int nextOpenBracket = markdown.indexOf("[", currentIndex);
            int nextCloseBracket = markdown.indexOf("]", nextOpenBracket);
            int openParen = markdown.indexOf("(", nextCloseBracket);
            int closeParen = markdown.indexOf(")", openParen);

            // first fix: avoid infinite loop
            if (closeParen == -1 || openParen == -1 || nextCloseBracket == -1 || nextOpenBracket == -1){
                break;
            }else{
                //third fix: avoid symbol [] and () which is not for link
                if(!(markdown.substring(nextCloseBracket+1, nextCloseBracket+2).equals("("))){
                    currentIndex = nextCloseBracket + 1;
                    continue;
                }
                //end third fix

                //fourth fix: avoid missing parenmeter ) and fifth fix: allows the link to have parentheses
                String partLink = markdown.substring(openParen+1, closeParen); //find ( before closeParen and after openParen
                int moreParen = partLink.indexOf("("); //find if any open paren
                boolean missingClosingParen = false; //if there is no closing paren
                while(moreParen!=-1){ // if there is another open paren, then this closingParen should be pair with it
                    moreParen = openParen + moreParen +1; //get the another open paren position
                    closeParen = markdown.indexOf(")", closeParen+1); // find the another closeing paren
                    if (closeParen == -1){ //if closing paren is missing, then it is not a link
                        missingClosingParen = true;
                        break;
                    }else{ //there is another closing paren, but need to avoid that is a part of link again
                        partLink = markdown.substring(moreParen+1, closeParen);
                        moreParen = partLink.indexOf("(");
                    }
                }//end of while loop
                if(missingClosingParen){ //this is not a link, so go back to search again
                    currentIndex = openParen + 1;
                    continue;
                }
                // fourth fix and fifth fix end 

                //second fix: avoid image
                if (nextOpenBracket != 0 && markdown.substring(nextOpenBracket-1, nextOpenBracket).equals("!")){
                    currentIndex = closeParen + 1;
                    continue;
                }
                // end of second fix


                toReturn.add(markdown.substring(openParen + 1, closeParen));
                currentIndex = closeParen + 1;
            }
        }
        return toReturn;
    }
    public static void main(String[] args) throws IOException {
		Path fileName = Path.of(args[0]);
	    String contents = Files.readString(fileName);
        ArrayList<String> links = getLinks(contents);
        System.out.println(links);
    }
}
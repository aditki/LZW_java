/*
	Name: Sai Aditya Varma Mudunuri
	#ID: 800986990
*/

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Compressor {

	public static String readFileData(String fileNAME){
		Path path = Paths.get(fileNAME);
		String output = "";
		try (Stream<String> lines = Files.lines(path)) {
		    output = lines.collect(Collectors.joining(""));
		} catch (FileNotFoundException e) {
	          e.printStackTrace();
	      } catch (IOException e) {
	          e.printStackTrace();
	      }
		
		return output;
	}
	
	public static void writeToFile(String fileNAME, ArrayList<Integer> codes){
		
		try{
			String outputFileNAME = fileNAME.substring(0, fileNAME.lastIndexOf(".")) + ".lzw";
			FileOutputStream stream = new FileOutputStream(outputFileNAME);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream, Charset.forName("UTF-16BE")));
			
			for(int code : codes){
				writer.write(code);
			}
			
			writer.flush();
			writer.close();
			
		} catch (FileNotFoundException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }
		
	}
	
	public static void main(String[] args) throws IOException {
		
		/* command line arguments validation*/
        if (args.length != 2) {
            System.out.println("Entered format is invalid. Try with expected format :java Compressor <filename> <number of bits>");
            return;
        }
		
		String FILEPATH = args[0];
		int BIT_SIZE;

		/* Number of bits validations*/
		try{
			BIT_SIZE = Integer.parseInt(args[1]);
		} catch(NumberFormatException e){
			System.out.println("Entered number of bits is invalid");
			return;
		}
		
		int MAX_SIZE = (int) Math.pow(2, BIT_SIZE);

		/* Creating a dictionary with character and its ascii */
		HashMap<String, Integer> DICTIONARY = new HashMap<String, Integer>(); 
		
		for(int i = 0; i < 256; i++){
			DICTIONARY.put(Character.toString((char) i), i);
		}
		
		String STRING = "";
		String content = readFileData(FILEPATH);
		
		ArrayList<Integer> codes = new ArrayList<>();
		
		for(int i = 0; i < content.length(); i++){
			char c = content.charAt(i);
			if(DICTIONARY.containsKey(STRING + c)){
				STRING = STRING + c;
			} else{
				
				codes.add(DICTIONARY.get(STRING));

				if(DICTIONARY.size() < MAX_SIZE){
					/* Adding new entries to the dictionary */
					DICTIONARY.put(STRING + c, DICTIONARY.size());
				}
				
				STRING = Character.toString(c);
			}
		}
		
		codes.add(DICTIONARY.get(STRING));
		
		writeToFile(FILEPATH, codes);
		
	}

}
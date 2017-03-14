/*
	Name: Sai Aditya Varma Mudunuri
	#ID: 800986990
*/

import java.io.FileInputStream;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Decompressor {
	
	public static ArrayList<Integer> readCodesFromFile(String filepath){
		
		ArrayList<Integer> codes = new ArrayList<>();
		
		try{
			Reader r = new InputStreamReader(new FileInputStream(filepath), Charset.forName("UTF-16BE"));
			int code;
			while ((code = r.read()) != -1) {
				codes.add(code);
			}
			
			r.close();
		}catch (FileNotFoundException e) {
	          e.printStackTrace();
	      } catch (IOException e) {
	          e.printStackTrace();
	      }
		
		return codes;
	}
	
	public static void writeToFile(String filepath, String output){
		try{
			String outputFilepath = filepath.substring(0, filepath.lastIndexOf(".")) + "_decoded.txt";
			
			Path path = Paths.get(outputFilepath);
			BufferedWriter w = Files.newBufferedWriter(path);
			w.write(output);
			w.close();
			
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

		int BIT_LENGTH;

		/* Number of bits validations*/
		try{
			BIT_LENGTH = Integer.parseInt(args[1]);
		} catch(NumberFormatException e){
			System.out.println("Entered number of bits is invalid");
			return;
		}

		int MAX_TABLE_SIZE = (int) Math.pow(2, BIT_LENGTH);
		
		/* Creating a dictionary with character and its ascii */
		HashMap<Integer, String> DICTIONARY = new HashMap<Integer, String>(); 
		
		for(int i = 0; i < 256; i++) {
			DICTIONARY.put(i, Character.toString((char) i));
		}
		
		ArrayList<Integer> codes = readCodesFromFile(FILEPATH);
		
		String STRING = ""; 
		String OLD_STRING = DICTIONARY.get(codes.get(0));
		String OUTPUT = DICTIONARY.get(codes.get(0));
		
		for(int i = 1; i < codes.size(); i++){
			if(DICTIONARY.containsKey(codes.get(i))){
				STRING = DICTIONARY.get(codes.get(i));
			} else{
				STRING = OLD_STRING + OLD_STRING.charAt(0);
			}
			
			OUTPUT = OUTPUT + STRING;
			
			/* check upper limit of DICTIONARY size */
           	if(DICTIONARY.size() < MAX_TABLE_SIZE){
           		/* store new codes to dictionary */
        		DICTIONARY.put(DICTIONARY.size(), OLD_STRING + STRING.charAt(0));
           	}
           
           	OLD_STRING = STRING;
		}
	
		writeToFile(FILEPATH, OUTPUT);

	}

}
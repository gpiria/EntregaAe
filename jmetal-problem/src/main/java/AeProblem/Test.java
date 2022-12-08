package AeProblem;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Test {
	public static void main(String[] args) {
			 String filename = "C:\\Users\\gpiri\\Desktop\\AE\\AE\\Jsons\\distancias_plazas_ninos_inse_menor_5\\Matrix.json";
			 JSONParser parser = new JSONParser();
			 try {
				 
			 Object obj = parser.parse(new FileReader(filename));
			 
			 JSONArray matrix = (JSONArray) obj;
			 
			 int[] arr = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			 int counterHotspots= 0;
			int totalDistance = 0;
			 int children = ((JSONArray) matrix.get(0)).size();
				for(int i = 0; i < children; i++) {
					int minDistance = 1500;
					for (int j = 0; j < arr.length; j++) {
						if(arr[j] == 1) {					
							JSONArray row = (JSONArray) matrix.get(j);
							int distance = ((Long) row.get(i)).intValue();
							if(distance != -1 && distance < minDistance) {
								minDistance = distance;
							}
							if(i==0) {
								System.out.println(j);
								counterHotspots++;						
							}
						}
					}
					totalDistance += minDistance;
				}
				System.out.println(counterHotspots);
				System.out.println(totalDistance);
			 } catch (FileNotFoundException e) {
				 e.printStackTrace();
			 } catch (IOException e) {
				 e.printStackTrace();
			 } catch (ParseException e) {
				 e.printStackTrace();
			 }

	}
}

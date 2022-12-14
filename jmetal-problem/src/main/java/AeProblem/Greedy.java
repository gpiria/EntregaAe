package AeProblem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

public class Greedy {
	public static boolean isCloser(Long dist1, Long dist2) {
		if(dist1 != -1 && (dist1 < dist2 || dist2 == -1)) {
			return true;
		} else {
			return false;
		}
	}
	public static void main(String[] args) throws Exception {
		String filename;
		if(args.length == 1) {
			String filePath = new File("").getAbsolutePath();
			filename = filePath.concat("\\").concat(args[0]);
		}
		else {	
			filename = "C:\\Users\\gpiri\\Desktop\\AE\\AE\\Jsons\\distancias_plazas_ninos_inse_menor_5\\Matrix.json";
		}
		
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(filename));
			JSONArray matrix = (JSONArray) obj;
			
			JSONArray plaza = (JSONArray) matrix.get(0);
			int plazasQuant = matrix.size();
			int childrenQuant = plaza.size();
			int[] solution = new int[plazasQuant];
			
			int minDistance = 0;
			int position = 0;
			JSONArray currentArray = (JSONArray) matrix.get(0);
			for (int i = 0; i < childrenQuant; i++) {
				if((Long) plaza.get(i) == -1) {
					minDistance += 1500;
				} else {					
					minDistance += (Long) plaza.get(i);
				}
			}
			for(int i = 1; i < plazasQuant; i++) {
				int distance = 0;
				plaza = (JSONArray) matrix.get(i);
				for (int j = 0; j < childrenQuant; j++) {
					if((Long) plaza.get(j) == -1) {
						distance += 1500;
					} else {						
					distance += (Long) plaza.get(j);
					}
				}
				if (distance < minDistance) {
					minDistance = distance;
					position = i;
					currentArray = plaza;
				}
			}
			solution[position] = 1;
			
			JSONArray minArray = (JSONArray) currentArray.clone();
			if(solution[0] != 1) {
				plaza = (JSONArray) matrix.get(0);
				int distance = 0;
				for(int j = 0; j < childrenQuant; j++ ) {
					if(isCloser((Long) plaza.get(j), (Long) currentArray.get(j))) {
						minArray.set(j, plaza.get(j));
						if((Long) plaza.get(j) == -1) {
							distance += 1500;
						} else {							
						distance += (Long) plaza.get(j);
						}
					} else {
						if((Long) currentArray.get(j) == -1) {
							distance += 1500;
						} else {							
						distance += (Long) currentArray.get(j);
						}
					}
				}
				if(distance > minDistance) {
					minArray = currentArray;
				} else {
					minDistance = distance;
				}
			} else {
				plaza = (JSONArray) matrix.get(1);
				int distance = 0;
				for(int j = 0; j < childrenQuant; j++ ) {
					if(isCloser((Long) plaza.get(j), (Long) currentArray.get(j))) {
						minArray.set(j, plaza.get(j));
						if((Long) plaza.get(j) == -1) {
							distance += 1500;
						} else {							
						distance += (Long) plaza.get(j);
						}
					} else {
						if((Long) currentArray.get(j) == -1) {
							distance += 1500;
						} else {							
						distance += (Long) currentArray.get(j);
						}
					}
				}
				if(distance > minDistance) {
					minArray = currentArray;
				} else {
					minDistance = distance;
				}
			}
			for (int total = 0; total < plazasQuant - 1; total++) {	
				for(int i = 0; i < plazasQuant; i++) {
					if(solution[i] != 1) {
						int distance = 0;
						plaza = (JSONArray) matrix.get(i);
						JSONArray distanceArray = (JSONArray) currentArray.clone();
						for(int j = 0; j < childrenQuant; j++ ) {
							if(isCloser((Long) plaza.get(j), (Long) distanceArray.get(j))) {
								distanceArray.set(j, plaza.get(j));
								if((Long) plaza.get(j) == -1) {
									distance += 1500;
								} else {				
									distance += (Long) plaza.get(j);
								}
							} else {
								if((Long) currentArray.get(j) == -1) {
									distance += 1500;
								} else {							
								distance += (Long) currentArray.get(j);
								}
							}
						}
						if(distance <= minDistance) {
							position = i;
							minArray = (JSONArray) distanceArray.clone();
							minDistance = distance;
						}
					}
				}
				currentArray = minArray;
				solution[position] = 1;
				//System.out.println("--------------------------------");
				System.out.print(total + 2);
				System.out.print(",");
				System.out.print((float) minDistance / childrenQuant);
				System.out.println();
				//System.out.println(Arrays.toString(solution));
				//System.out.println("--------------------------------");
			}
		} catch (FileNotFoundException e) {
			 e.printStackTrace();
		 } catch (IOException e) {
			 e.printStackTrace();
		 } catch (ParseException e) {
			 e.printStackTrace();
		 }
	}
}

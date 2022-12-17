package AeProblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.BitSet;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.uma.jmetal.problem.binaryproblem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.AbstractSolution;
import org.uma.jmetal.solution.binarysolution.BinarySolution;
import org.uma.jmetal.solution.binarysolution.impl.DefaultBinarySolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.binarySet.BinarySet;

@SuppressWarnings("serial")
public class AeProblem extends AbstractBinaryProblem {
	private int bits;
	private int numberOfStudents;
	private JSONArray matrix;
	private List<Integer[]> seeds = new ArrayList<Integer[]>(); 
	private int seedsUsed = 0;
	private int maxSeeds = 25;
	
	public AeProblem(String filename) throws Exception {
		setNumberOfVariables(1);
		setNumberOfObjectives(2);
	    setName("AeProblem");
		 JSONParser parser = new JSONParser();
		 try {

		 String filePath = new File("").getAbsolutePath();
		 filePath = filePath.concat("\\").concat(filename);
		 Object obj = parser.parse(new FileReader(filePath));
		 
		 matrix = (JSONArray) obj;
		 bits = matrix.size();
		 JSONArray students = (JSONArray) matrix.get(0);
		 numberOfStudents = students.size();
		 greedy();
		 } catch (FileNotFoundException e) {
			 e.printStackTrace();
		 } catch (IOException e) {
			 e.printStackTrace();
		 } catch (ParseException e) {
			 e.printStackTrace();
		 }
	}
	
	public BinarySolution createSolution() {
		int interval = bits / (maxSeeds + 2);
		if(seedsUsed <= maxSeeds) {
			DefaultBinarySolution seed = new DefaultBinarySolution(Arrays.asList(bits), getNumberOfObjectives());
			BinarySet bitSet = new BinarySet(bits) ;
			int s = 0;
		    for (int i = 0; i < bits; i++) {
		      if (seeds.get((seedsUsed + 1) * interval)[i] == 1) {
		        bitSet.set(i);
		        s++;
		      } else {
		        bitSet.clear(i);
		      }
		    }
			seed.setVariable(0, bitSet);
			seedsUsed = seedsUsed + 1;
			return seed;
		}
		else {
			DefaultBinarySolution sol = new DefaultBinarySolution(Arrays.asList(bits), getNumberOfObjectives());
		return sol;
		}
	}
	
	
	 public int getBitsFromVariable(int index) {
		if (index != 0) {
			throw new JMetalException("Problem AeProblem has only a variable. Index = " + index) ;
		}
	  	return bits ;
	 }
	 
	 public List<Integer> getListOfBitsPerVariable() {
	    return Arrays.asList(bits) ;
	 }
	
	
	public void evaluate(BinarySolution solution) {
		int counterHotspots= 0;
		int totalDistance = 0;
		BitSet bitset = solution.getVariable(0);
		int children = ((JSONArray) matrix.get(0)).size();
		for(int i = 0; i < children; i++) {
			int minDistance = 1500;
			for (int j = 0; j < bitset.length(); j++) {
				if(bitset.get(j)) {					
					JSONArray row = (JSONArray) matrix.get(j);
					int distance = ((Long) row.get(i)).intValue();
					if(distance != -1 && distance < minDistance) {
						minDistance = distance;
					}
					if(i==0) {
						counterHotspots++;						
					}
				}
			}
			totalDistance += minDistance;
		}
		solution.setObjective(0, counterHotspots);
		solution.setObjective(1, (float) totalDistance / numberOfStudents);
	}
	
	public boolean isCloser(Long dist1, Long dist2) {
		if(dist1 != -1 && (dist1 < dist2 || dist2 == -1)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void greedy() throws Exception {
			
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
				seeds.add(Arrays.stream( solution ).boxed().toArray( Integer[]::new ));
			}
			

	}
}

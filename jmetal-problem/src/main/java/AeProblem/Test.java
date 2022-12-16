package AeProblem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.uma.jmetal.qualityindicator.impl.InvertedGenerationalDistancePlus;
import org.uma.jmetal.qualityindicator.impl.Spread;
import org.uma.jmetal.qualityindicator.impl.hypervolume.impl.PISAHypervolume;
import org.uma.jmetal.solution.binarysolution.BinarySolution;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.impl.ArrayFront;
import org.uma.jmetal.util.front.util.FrontNormalizer;
import org.uma.jmetal.util.front.util.FrontUtils;
import org.uma.jmetal.util.point.PointSolution;

public class Test {
	public static void main(String[] args) throws FileNotFoundException {
		if(args[0] != "" && args [1] != "") {
			String filePath = new File("").getAbsolutePath();
			String filename = args[0];
			String referenceParetoFront = filePath.concat("\\").concat(filename);
			filename = args[1];
			String greedyFront = filePath.concat("\\").concat(filename);
			
			Front referenceFront = new ArrayFront(referenceParetoFront);
		    FrontNormalizer frontNormalizer = new FrontNormalizer(referenceFront);
		    Front normalizedReferenceFront = frontNormalizer.normalize(referenceFront);
		    Front normalizedFront = frontNormalizer.normalize(new ArrayFront(greedyFront));
		    List<PointSolution> normalizedPopulation = FrontUtils
		            .convertFrontToSolutionList(normalizedFront);
		    List<PointSolution> population = FrontUtils.convertFrontToSolutionList(new ArrayFront(greedyFront));
		    String outputString = "\n" ;
		    outputString += "Hypervolume     : " +
		      new PISAHypervolume<PointSolution>(normalizedReferenceFront).evaluate(normalizedPopulation) + "\n";
		    outputString += "IGD+            : " +
		          new InvertedGenerationalDistancePlus<PointSolution>(referenceFront).evaluate(population) + "\n";
		    outputString += "Spread          : " +
		          new Spread<PointSolution>(referenceFront).evaluate(population) + "\n";
		    System.out.print(outputString);
		
		}
	}
}

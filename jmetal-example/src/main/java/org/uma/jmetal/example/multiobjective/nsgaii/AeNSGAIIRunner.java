package org.uma.jmetal.example.multiobjective.nsgaii;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.example.AlgorithmRunner;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.crossover.impl.HUXCrossover;
import org.uma.jmetal.operator.crossover.impl.SinglePointCrossover;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.operator.mutation.impl.BitFlipMutation;
import org.uma.jmetal.operator.selection.SelectionOperator;
import org.uma.jmetal.operator.selection.impl.BinaryTournamentSelection;
import org.uma.jmetal.problem.binaryproblem.BinaryProblem;
import org.uma.jmetal.qualityindicator.impl.InvertedGenerationalDistancePlus;
import org.uma.jmetal.qualityindicator.impl.NormalizedHypervolume;
import org.uma.jmetal.qualityindicator.impl.Spread;
import org.uma.jmetal.qualityindicator.impl.hypervolume.impl.PISAHypervolume;
import org.uma.jmetal.solution.binarysolution.BinarySolution;
import org.uma.jmetal.util.AbstractAlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.impl.ArrayFront;
import org.uma.jmetal.util.front.util.FrontNormalizer;
import org.uma.jmetal.util.front.util.FrontUtils;
import org.uma.jmetal.util.point.PointSolution;
import org.uma.jmetal.qualityindicator.impl.NormalizedHypervolume;
import org.uma.jmetal.solution.Solution;

import AeProblem.AeProblem;

public class AeNSGAIIRunner extends AbstractAlgorithmRunner {

	public static void main(String[] args) throws Exception {
		String filename;
		String referenceParetoFront;
		String filePath = new File("").getAbsolutePath();
		if(args.length == 1) {
			filename = args[0];
			referenceParetoFront = filePath.concat("\\").concat("pareto.csv");
		} else if(args.length == 2) {
			filename = args[0];
			referenceParetoFront = filePath.concat("\\").concat(args[1]);
		} else {

			referenceParetoFront = filePath.concat("\\").concat("pareto.csv");
			filename = "Matrix_combined.json";
		}
		BinaryProblem problem;
		Algorithm<List<BinarySolution>> algorithm;
	    CrossoverOperator<BinarySolution> crossover;
	    MutationOperator<BinarySolution> mutation;
	    BinaryTournamentSelection<BinarySolution> selection;
	    
	    
	    
	    problem = (BinaryProblem) new AeProblem(filename);
	    double crossoverProbability = 0.75;
	    crossover = new HUXCrossover(crossoverProbability);
	    double mutationProbability = 0.01;
	    mutation = new BitFlipMutation(mutationProbability);

	    selection = new BinaryTournamentSelection<BinarySolution>();
	    
	    int populationSize = 80;
	    algorithm = new NSGAIIBuilder<BinarySolution>(problem, crossover, mutation, populationSize)
	            .setSelectionOperator(selection)
	            .setMaxEvaluations(20000)
	            .build() ;

	    AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
	            .execute() ;

	    List<BinarySolution> population = algorithm.getResult();
	    long computingTime = algorithmRunner.getComputingTime();
	    
	    JMetalLogger.logger.info("Total execution time: " + computingTime );

	    printFinalSolutionSet(population);
	    Front referenceFront = new ArrayFront(referenceParetoFront);
	    FrontNormalizer frontNormalizer = new FrontNormalizer(referenceFront);

	    Front normalizedReferenceFront = frontNormalizer.normalize(referenceFront);
	    Front normalizedFront = frontNormalizer.normalize(new ArrayFront(population));
	    List<PointSolution> normalizedPopulation = FrontUtils
	            .convertFrontToSolutionList(normalizedFront);
	    String outputString = "\n" ;
	    //outputString += "Hypervolume     : " +
	      //  new PISAHypervolume<PointSolution>(normalizedReferenceFront).evaluate(normalizedPopulation) + "\n";
	    //outputString += "IGD+            : " +
	      //      new InvertedGenerationalDistancePlus<BinarySolution>(referenceFront).evaluate(population) + "\n";
	    //outputString += "Spread          : " +
	      //      new Spread<BinarySolution>(referenceFront).evaluate(population) + "\n";
	    outputString += 		        new PISAHypervolume<PointSolution>(normalizedReferenceFront).evaluate(normalizedPopulation) + "\n";
		outputString += 	            new InvertedGenerationalDistancePlus<BinarySolution>(referenceFront).evaluate(population) + "\n";
		outputString += 	            new Spread<BinarySolution>(referenceFront).evaluate(population) + "\n";
	    System.out.print(outputString);
	}
}

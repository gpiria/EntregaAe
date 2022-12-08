package org.uma.jmetal.example.multiobjective.nsgaii;

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
import org.uma.jmetal.solution.binarysolution.BinarySolution;
import org.uma.jmetal.util.AbstractAlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;

import AeProblem.AeProblem;

public class AeNSGAIIRunner extends AbstractAlgorithmRunner {

	public static void main(String[] args) throws Exception {
		String filename;
		String referenceParetoFront;
		if(args.length == 1) {
			filename = args[0];
			referenceParetoFront = "C:\\Users\\gpiri\\Desktop\\AE\\AE\\Jsons\\pareto.csv";
		} else if(args.length == 2) {
			filename = args[0];
			referenceParetoFront = args[1];
		} else {
			referenceParetoFront = "C:\\Users\\gpiri\\Desktop\\AE\\AE\\Jsons\\pareto.csv";
			filename = "Matrix_combined.json";
		}
		BinaryProblem problem;
		Algorithm<List<BinarySolution>> algorithm;
	    CrossoverOperator<BinarySolution> crossover;
	    MutationOperator<BinarySolution> mutation;
	    BinaryTournamentSelection<BinarySolution> selection;
	    
	    
	    
	    problem = (BinaryProblem) new AeProblem(filename);
	    double crossoverProbability = 0.75;
	    crossover = new HUXCrossover(crossoverProbability) ;
	    double mutationProbability = 0.001;
	    mutation = new BitFlipMutation(mutationProbability) ;

	    selection = new BinaryTournamentSelection<BinarySolution>() ;
	    
	    int populationSize = 80;
	    algorithm = new NSGAIIBuilder<BinarySolution>(problem, crossover, mutation, populationSize)
	            .setSelectionOperator(selection)
	            .setMaxEvaluations(10000)
	            .build() ;

	    AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
	            .execute() ;

	    List<BinarySolution> population = algorithm.getResult();
	    long computingTime = algorithmRunner.getComputingTime();
	    
	    JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");

	    printFinalSolutionSet(population);
	    if (!referenceParetoFront.equals("")) {
	      printQualityIndicators(population, referenceParetoFront) ;
	    }
	}
}

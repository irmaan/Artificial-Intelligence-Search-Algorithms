import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 
 */


public class PuzzleProgram {

	/**
	 * Programa recebe o tipo de puzzle, um filepath do arquivo com a definicao do problema da Regua Puzzle ou Travessia da Ponte,
	 * a sigla do ALGORITMO-DE-BUSCA e escolha de uma heurística opcional;
	 * 
	 * As siglas podem ser: BL, BP, BPL, BPI, BCU, A*, IDA*, e RBFS.
	 * 
	 * As heurísticas podem ser h1 ou h2 para o régua-puzzle e h1, h2 e h3 para o travessia da ponte.
	 * 
	 * @param args (regua-puzzle|travessia-da-ponte) entrada.txt ALGORITMO-DE-BUSCA
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception{
		printHello();
		
		run(args);
		//runTests();
	}

	private static void run(String[] args) throws IOException, Exception {
		if(args.length < 3) {
			 System.err.println("[ERRO] O nome do puzzle (regua-puzzle ou travessia-da-ponte), "
			 		+ "o caminho do arquivo e a sigla do algoritmo de busca "
			 		+ "são parâmetros esperados!");
			 System.out.println("Exemplo: regua-puzzle C:/testes/entrada.txt BP");
			 System.exit(1);
		}
		
		String puzzleParam = args[0];
		File f = new File(args[1]);
		String searchParam = args[2];
		String heuristics = args.length > 3? args[3] : "";
		
		List<String> lines = readFile(f);
		
		IPuzzleProblem problem = getPuzzleProblem(puzzleParam, heuristics, lines);
		int limit = 100;
		
		System.out.println(Arrays.toString(args));
		
		ISearch search = SearchFactory.getSearch(searchParam, problem, limit);
		ISolution solution = search.solve();
		if(solution.solved()){
			solution.printSolution();
		}
		else{
			System.out.println("no solution found!");
		}
		System.out.println("");
	}
	
	private static IPuzzleProblem getPuzzleProblem(String puzzleParam, String heuristics, List<String> lines) throws Exception
	{
		IPuzzleProblem puzzleProblem;
		String h = "h1";
		if(heuristics != null && !heuristics.isEmpty()){
			h = heuristics;
		}
		
		switch (puzzleParam.toUpperCase()) {
		case "REGUA-PUZZLE":
			puzzleProblem = new ReguaPuzzleProblem(lines, h);
			break;
		case "TRAVESSIA-DA-PONTE":
			puzzleProblem = new BridgeCrossingProblem(lines, h);
		break;
		default:
			throw new Exception("Problem not found: [" + puzzleParam + "]");
		}
		return puzzleProblem;
	}

	private static void printHello() {
		System.out.println("EP 1 - IA - Algoritmos de Busca");
		System.out.println("Thiago Araujo");
		System.out.println("O programa deve receber 3 parâmetros: (regua-puzzle|travessia-da-ponte) entrada.txt ALGORITMO-DE-BUSCA (h1|h2|h3)");
		System.out.println("Exemplo: regua-puzzle C:/testes/entrada.txt BP");
		System.out.println("");
	}

	private static List<String> readFile(File fin) throws IOException {
		// Construct BufferedReader from FileReader
		BufferedReader br = new BufferedReader(new FileReader(fin));
	    List<String> lines = new ArrayList<String>();
		String line = null;
		while ((line = br.readLine()) != null) {
			lines.add(line);
		}
		br.close();
		
		return lines;
	}
	
	private static void runTests() throws Exception {
		
		//BL, BP, BPL, BPI, BCU, A*, IDA*, e RBFS.
		String algorithm = " " + "RBFS" + " " + "h1";
		
		run(("regua-puzzle ../testes/regua-puzzle-n2-alternado.txt" 	+ algorithm).split(" "));
		run(("regua-puzzle ../testes/regua-puzzle-n2-falta1.txt" 		+ algorithm).split(" "));
		run(("regua-puzzle ../testes/regua-puzzle-n2-invertido.txt" 	+ algorithm).split(" "));
		
		run(("regua-puzzle ../testes/regua-puzzle-n3-alternado.txt" 	+ algorithm).split(" "));
		run(("regua-puzzle ../testes/regua-puzzle-n3-falta1.txt" 		+ algorithm).split(" "));
		run(("regua-puzzle ../testes/regua-puzzle-n3-invertido.txt" 	+ algorithm).split(" "));
		
		run(("regua-puzzle ../testes/regua-puzzle-n4-alternado.txt" 	+ algorithm).split(" "));
		run(("regua-puzzle ../testes/regua-puzzle-n4-falta1.txt" 		+ algorithm).split(" "));
		run(("regua-puzzle ../testes/regua-puzzle-n4-invertido.txt" 	+ algorithm).split(" "));
		
		run(("travessia-da-ponte ../testes/travessia-da-ponte-n4.txt" 	+ algorithm).split(" "));
		
	}
}

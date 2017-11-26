import java.util.ArrayList;
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class Automaton 
{
	// class variables
	
	// the filename that contains the automaton description
	String filename;
	// the number of states of the automaton
	int numOfStates;
	// the initial state of the automaton
	int initialState;
	// the number of final states of the automaton
	int numOfFinalStates;
	// the number of the transitions of the automaton
	int numOfTransitions;
	// the states of the automaton
	ArrayList<Integer> states;
	// the final states of the automaton
	ArrayList<Integer> finalStates;
	// the transitions of the automaton
	HashMap<String, Integer> transitions;
	// the current state of the automaton
	int currentState;
	
	// class methods
	
	// constructor
	public Automaton(String filename_)
	{
		states = new ArrayList<Integer>();
		finalStates = new ArrayList<Integer>();
		transitions = new HashMap<String, Integer>();
		
		filename = filename_;
		String line = null;
		
		try
		{
			FileReader fileReader = new FileReader(filename);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			Boolean firstLine = true;
			Boolean secondLine = true;
			Boolean thirdLine = true;
			Boolean fourthLine = true;
			Boolean fifthLine = true;
			// read the file
			while((line = bufferedReader.readLine()) != null)
			{
				if (firstLine)
				{
					firstLine = false;
					numOfStates = Integer.parseInt(line);
					for (int i = 1; i <= numOfStates; i++)
					{
						states.add(i);
					}
				}
				else if (secondLine)
				{
					secondLine = false;
					initialState = Integer.parseInt(line);
				}
				else if (thirdLine)
				{
					thirdLine = false;
					numOfFinalStates = Integer.parseInt(line);
				}
				else if (fourthLine)
				{
					fourthLine = false;
					String finalStatesStr[] = line.split(" ");
					for (String finalState : finalStatesStr)
					{
						finalStates.add(Integer.parseInt(finalState));
					}
				}
				else if (fifthLine)
				{
					fifthLine = false;
					numOfTransitions = Integer.parseInt(line);
				}
				else
				{
					String transitionsStr[] = line.split(" ");
					int currentState = Integer.parseInt(transitionsStr[0]);
					char symbol = transitionsStr[1].charAt(0);;
					int nextState = Integer.parseInt(transitionsStr[2]);
					String key = String.valueOf(currentState) + String.valueOf(symbol);
					transitions.put(key, nextState);
				}
			}
			
			// close file
			bufferedReader.close();
			
		}
		catch(FileNotFoundException ex)
		{
			System.out.println("The file '" + filename +"' was not found.");
		}
		catch(IOException ioex)
		{
			ioex.printStackTrace();
		}
	}
	
	// set the first current state of the automaton to initial state
	public void Initialize()
	{
		currentState = initialState;
	}
	
	// Setters - Getters
	public void SetFilename(String filename_)
	{
		filename = filename_;
	}
	
	public String GetFilename()
	{
		return filename;
	}
	
	public void SetNumOfStates(int numOfStates_)
	{
		numOfStates = numOfStates_;
	}
	
	public int GetNumOfStates()
	{
		return numOfStates;
	}
	
	public void SetInitialState(int initialState_)
	{
		initialState = initialState_;
	}
	
	public int GetInitialState()
	{
		return initialState;
	}
	
	public void SetNumOfFinalStates(int numOfFinalStates_)
	{
		numOfFinalStates = numOfFinalStates_;
	}
	
	public int GetNumOfFinalStates()
	{
		return numOfFinalStates;
	}
	
	public void SetNumOfTransitions(int numOfTransitions_)
	{
		numOfTransitions = numOfTransitions_;
	}
	
	public int GetNumOfTransitions()
	{
		return numOfTransitions;
	}
	
	// run the automaton
	public void Run(String input)
	{
		Boolean terminatedInFinalState = false;
		char terminatedSymbol = 'x';
		// int terminatedCurrentState = -1;
		int terminatedFinalState = -1;
		for (int i = 0; i < input.length(); i++)
		{
			System.out.print("Iteration " + String.valueOf(i) + ": ");
			char symbol = input.charAt(i);
			String key = String.valueOf(currentState) + String.valueOf(symbol);
			if (transitions.containsKey(key))
			{
				int nextState = transitions.get(key);
				System.out.println(String.valueOf(currentState) + "->(" + String.valueOf(symbol) + ")->" + String.valueOf(nextState));
				if (finalStates.contains(nextState))
				{
					terminatedInFinalState = true;
					terminatedSymbol = symbol;
					//terminatedCurrentState = currentState;
					terminatedFinalState = nextState;
					break;
				}
				currentState = nextState;
			}
			else
			{
				System.out.println(String.valueOf(currentState) + "->(" + String.valueOf(symbol) + ")-> -- ");
			}
		}
		if (terminatedInFinalState)
		{
			System.out.println("The automaton terminated in a final state (" + String.valueOf(terminatedFinalState) + "), at the symbol " + String.valueOf(terminatedSymbol));
		}
		else
		{
			System.out.println("The automaton terminated in a non-final state");
		}
	}
	
	// main
	public static void main(String[] args)
	{
		// Ask for the automaton description filename
		System.out.println("Give the filename containing the automaton description");
		Scanner sc = new Scanner(System.in);
		String filename = sc.nextLine();
		
		// Create automaton
		Automaton aut = new Automaton(filename);
		// Initialize automaton
		aut.Initialize();
		
		// The user gives input to the automaton as a String of characters
		System.out.println("Give input for the automaton");
		String input = sc.nextLine();
		
		// Run the automaton
		aut.Run(input);
	}
}

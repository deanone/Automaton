
public class Pair
{
	int currentState;
	char symbol;
	
	public Pair(int currentState_, char symbol_)
	{
		currentState = currentState_;
		symbol = symbol_;
	}
	
	public int GetCurrentState()
	{
		return currentState;
	}
	
	public char GetSymbol()
	{
		return symbol;
	}
}

public class ASTNeg implements ASTNode {

ASTNode exp;

	public int eval()
	{
		return - exp.eval(); 
	}
    
	public ASTNeg(ASTNode exp)
	{
		this.exp = exp;
	}
}


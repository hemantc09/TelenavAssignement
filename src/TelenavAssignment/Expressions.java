/**
 * @author Hemantc09
 * Date: April 16 2016 Saturday 
 * Problem: Based Samples of inputs and targeted number generate the expression 
 * Output: and outputs:
i. [1, 2, 3, 4], target = 3, output: (2 + 4) * 1 – 3 = 3

ii. [3, 3, 2, 8], target = 2, output: (3 – 3) * 8 + 2 = 2

iii. [2, 1, 7], target = 12, output: 2 * (7 – 1) = 12

iv. [3, 5, 5, 3], target = 1, output: (5 - (5 – 3)) / 3 = 1

Contact:
Email: hemantc09@gmail.com
Phone no: +1( 669 ) 243 - 0277
 */

package TelenavAssignment;
import java.util.*;
public class Expressions {
	
	static String operators = "+-/*";
    public static int count=0;
    public static int target=0;// this is the targeted number
    public static int dupflag=0;//flag for duplicate characters
    static Stack<String> outputstack=new Stack<String>(); //this stack contain only valid output expressions
    static Stack<String> outputstacknone=new Stack<String>(); // this stack contains "none" i.e. if the inputs doens't meet s the requirements.
    public static  String translatePostfixtoInfix(String postfix) { // this method used for to get the infix expression from the postfix expression
    	int returnflag; 
    	String exp;
        Stack<String> expr = new Stack<String>(); //created stack for to store the string expr 
        Scanner sc = new Scanner(postfix);
        while (sc.hasNext()) {
            String t = sc.next();
            if (operators.indexOf(t) == -1) { // it checks the 'operators' sets that are already predefined "+-/*" in the class Expression
                expr.push(t);
            } else {
                expr.push(" ( " + expr.pop() +" "+ t +" "+ expr.pop() + " ) "); // it will push the infix expression into the stack expr
            }
        }
        exp=expr.pop(); // it get the infix expression from the stack
        //  System.out.print("postfix:"+postfix+"\t"+"exp"+exp+"\n");
        postfixevaluation(postfix,exp); // will get the postfix evaluation result in result which is double type
        								//also here I am passing the infix  string for get the postfix evaluation of the string
        return exp;  
    }

  
    private static void postfixevaluation(String postfixstring, String exp) { // this method used for postfix evaluation
		double result;
		Stack postfixstack=new Stack(); // crested the postfix stack for the postfix evaluation
		StringTokenizer parser=new StringTokenizer(postfixstring); // it will gives the tokens
		while(parser.hasMoreTokens())
		{
			char c;
			String token=parser.nextToken();
			c=token.charAt(0); // from the token we are getting the characters and based on character type perform the calculations
			if(isOperator(c))
			{
				double x=((Double)postfixstack.pop()).doubleValue(); // get the first pop value from stack
 				double y=((Double)postfixstack.pop()).doubleValue(); //get the second pop value stacl
				switch(c)
				{
				case '+':
					postfixstack.push(x+y); //if its '+' perform addition and push it back in to the stack
					break;
				case '-':
					postfixstack.push(x-y);//if its '-' perform subtraction and push it back in to the stack
					break;
				case '*':
					postfixstack.push(x*y); //if its '*' perform multiplication and push it back in to the stack
					break;
				case '/':
					postfixstack.push(x/y); //if its '/' perform division and push it back in to the stack
				}
			}
			else if(!isWhiteSpace(c))
			{
				postfixstack.push(Double.valueOf(token)); //if the character is a white space type category  then perform this
			}
		}
		
		result=((Double) postfixstack.pop()).doubleValue(); // result is the variable which check the targeted value
		if(result==target) // if the target is equal to result then push the expression in to the outputstack.
		{
			{
				outputstack.push(exp+" = "+" "+result); // push the expression and result in to the  outputstack	
			}
		}
		else
		{
			outputstacknone.push("none"); // if the condition doesn't meet then push none in to the another stack i.e. outputstacknone
		}
		
	 
	}
		
    private static boolean isOperator(char c) { // this will check what type of character is e.g. char c is '+' or '-' or '*' or '/'
		// TODO Auto-generated method stub	
    	
		return ((c=='+')||(c=='-')||(c=='*')||(c=='/'));
	}

	private static boolean isWhiteSpace(char c) { // this will check what type of character is e.g. ' ' or '\n' or '\t' or '\r'
		// TODO Auto-generated method stub
		
		return ((c==' ')||(c=='\n')||(c=='\t')||(c=='\r'));
	}



	public static  void brute(Integer[] numbers, int stackHeight, String eq) { //get the parameters i.e. command line input , stack and equation
    	
        if (stackHeight >= 2) {
            for (char op : operators.toCharArray()) {
                brute(numbers, stackHeight - 1, eq + " " + op);
            }
        }
        boolean allUsedUp = true;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] != null) {
                allUsedUp = false;
                Integer n = numbers[i];
                numbers[i] = null;
                brute(numbers, stackHeight + 1, eq + " " + n); //call the brute recursively to generate the expressions from input parameters
                numbers[i] = n;
            }
        }
        if (allUsedUp && stackHeight == 1) {
        	
        	translatePostfixtoInfix(eq); // passing the postfix to translate it into the infix
           
        }
    }
	
	static void expression(Integer[] ipArr) { // Here I am collecting the input arguments i.e. command line input argument
		Integer numbers[]=new Integer[ipArr.length];
		for(int j=1;j<ipArr.length;j++)
		{
			target=ipArr[0];//this is the targeted element i.e. first element from the command line arguments
			numbers[j]=ipArr[j];
		}
        brute(numbers, 0, ""); // pass the arguments the method to generate the expressions
    }
	
	private static int findDuplicateChars(String str) { // this method checks whether the expressions uses input set exactly once.   
		        Map<Character, Integer> dupMap = new HashMap<Character, Integer>(); 
		        char[] chrs = str.toCharArray();
		        for(Character ch:chrs){
		            if(dupMap.containsKey(ch)){
		                dupMap.put(ch, dupMap.get(ch)+1);
		            } else {
		                dupMap.put(ch, 1);
		            }
		        }
		        Set<Character> keys = dupMap.keySet();
		        for(Character ch:keys){
		            if(dupMap.get(ch) > 1){
		                if(ch=='+'||ch=='-'||ch=='*'||ch=='/') // if the generated expression has used the input set more time then simply set the dupflag to 1
		                	{
		                	dupflag=1; //if dupflag is one there are duplicate characters
		                	}
		                else
		                {
		                	dupflag=0; //set the dupflag =0 
		                }
		            }
		        }
		        return dupflag; // it will return the dupflag status based on input sets used one time or more that one times i.e. 0 or 1
		 
	}
    public static void main(String args[]) { //main method
    
    	Integer[] inputArray=null; 
    	System.out.print("Enter command line arguments from the run->arguments tab\n");
		inputArray=new Integer[args.length]; // created integer type array to store the input arguments
		
		for (int i = 0; i < args.length; i++) {
			inputArray[i]=Integer.parseInt(args[i]); // copy the arguments in to the input array
        }
        
        expression(inputArray); // pass the input array to the expression  
        if(outputstack.empty())  //check if the outputstack is empty. for reference go up and check the outputstack operations
        {
        	System.out.print("none");
        }
        else if(!outputstack.empty()) //if outputstack is not empty 
        {
        	while(!outputstack.empty()) // while loop until stack is not empty
        	{
        	 String sr=(String) outputstack.pop(); // get the output stack string untill the outputstack is not empty
        	 int df=0;//return from duplicated chareacters
        	 df=findDuplicateChars(sr); 
        	 if(df==1)
             {
             	//Do nothing
             }
        	 else
        	 {
        		 System.out.print(sr+"\n"); //print the expressions from the outputstack.
        	 } 
        	}
        }
    }
}
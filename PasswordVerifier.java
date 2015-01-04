/**
 * PasswordVerifier
 * Liu, Jeremy
 * February 17, 2014
 * Use a 5 rule criteria to test the strength of passwords
 */ 


import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;

public class PasswordVerifier extends JFrame
{
	//Instantiate all the components of the JFrame
	private Container contents;
	private JTextArea instructions, brokenRules;
	private JLabel strength;
	private JTextField usrPWord;
	
	/**
	 * The PasswordVerifier class builds the JFrame GUI and places all the
	 * components into the content pane.
	 */
	public PasswordVerifier()
	{
		//Set the layout of the JFrame
		super("Password Strength Tester");
		contents = getContentPane();
		contents.setLayout( new FlowLayout(FlowLayout.LEFT) );

		
		instructions = new JTextArea("Please type your desired password.\n"
			+ "\nYour Password must follow these conditions:\n"
			+ "Rule1: It must have at least 8 characters, and it must not contain any space character.\n"
			+ "Rule2: It must contain at least one special character, which is not a letter or digit.\n"
			+ "Rule3: It must contain at least one upper-case letter.\n"
			+ "Rule4: It must contain at least one lower-case letter.\n"
			+ "Rule5: It must contain at least one digit.");
		instructions.setOpaque(false);
		instructions.setEditable(false);
		
		//This will explain how strong the password entered is.
		strength = new JLabel("");
		
		//This TextArea will display the rules that the password broke.
		brokenRules = new JTextArea("");
		brokenRules.setEditable(false);
		brokenRules.setOpaque(false);
		brokenRules.setForeground( Color.RED);
		
		usrPWord = new JTextField( "", 15 );
		
		contents.add( instructions );
		contents.add( usrPWord );
		contents.add( strength );
		contents.add( brokenRules );
		
		//Create a textFieldHandler to take in user inputs
		TextFieldHandler tfh = new TextFieldHandler();
		
		usrPWord.addActionListener( tfh );	
		
		setSize( 600, 300 );
		setVisible( true );	
	}
	
	/**
	 * The TextFieldHandler defines how the program will respond to different
	 * user inputs into the JTextField.
	 */
	private class TextFieldHandler implements ActionListener
	{
		public void actionPerformed( ActionEvent e )
		{
			/** 
			 * Calls on functions to validate each rule
			 * if the password followed the rule, the function would return 1
			 * if not, the function would return 0.
			 */
			String password = usrPWord.getText();
			int length, spChar, lowCase, upCase, digit;
			length = lengthChecker(password);
			spChar = specialCharacter(password);
			upCase = upperCase(password);
			lowCase = lowerCase(password);
			digit = digitize(password);
			
			//Initiate pStrength, and add 1 or 0 depending on what the function returned.
			//the pStrength is the strength out of 5 for the password. 
			int pStrength = 0;
			pStrength = length + spChar + upCase + lowCase + digit;
			
	
			strength.setText("Your password has a strength of: " + pStrength
				+ " out of 5.");
			
			//Define various outcomes for different strengths.
			if (pStrength == 5)
			{
				brokenRules.setForeground( Color.BLUE );
				brokenRules.setText("Congratulations, you are a password pro!");
			}

			else if (pStrength == 0 )
			{
				brokenRules.setForeground( Color.RED );
				brokenRules.setText("Are you serious?! you violated every single rule!");
			}
			else
			{
				brokenRules.setForeground( Color.RED );
				brokenRules.setText("Your password violated the following rule(s):\n");
				
				/**
				 * These rules will appened to the list of brokenRules if any of the functions
				 * returned 0. making it possible for multiple rules to be broken, while also 
				 * allowing any possible combination of broken rules.
				 */
				if (length == 0)
				{
					brokenRules.append("  Rule1: It must have at least 8 characters, "
						+ "and it must not contain any space character.\n");
				}
				if (spChar == 0)
				{
					brokenRules.append("  Rule2: It must contain at least one special character, "
						+ "which is not a letter or digit.\n");
				}
				if (upCase == 0)
					brokenRules.append("  Rule3: It must contain at least one upper-case letter.\n");
				if (lowCase == 0)
					brokenRules.append("  Rule4: It must contain at least one lower-case letter.\n");
				if (digit == 0)
					brokenRules.append("  Rule5: It must contain at least one digit.\n");
			}	
		}
	}
	
	/**
	 * This is the main function that calls on the frame that we created to display a user-
	 * friendly window to the user.
	 */
	public static void main( String [] args )
	{
		PasswordVerifier passVerifier = new PasswordVerifier( );
		passVerifier.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
	}
	
	/** 
	 * It should be noted that all of the following functions will return a 1 or a
	 * 0 depending on if the password followed the rule or not. 
	 * If the password followed the rule, a 1 will be returned adding to the strength
	 * of the password.
	 * If the password failed to fulfill a rule, a 0 will be returned, not adding
	 * any strength to the password. 
	 */
	
	/**
	 * This function checks if there are spaces in the password.
	 * It also checks if the password matches the required minimum of 8 characters.
	 */
	public static int lengthChecker(String s)
	{
		if (s.contains(" "))
		{
			return 0;
		}
		else 
		{
			if (s.length() >= 8)
				return 1;
			else
				return 0;
		}	
	}
	
	/**
	 * This function checks if the password entered has a special character in it.
	 * I made sure that a space does not count as a special character.
	 */
	public static int specialCharacter(String s)
	{
		/**
		 * The regex pattern I created for special characters is basically any 
		 * character that is not an alphabetical letter, a number, or a space.
		 */
		Pattern spChar = Pattern.compile("[^A-Za-z0-9 ]");
		Matcher m = spChar.matcher(s);
		if (m.find())
			return 1;
		else 
			return 0;
		
	}	
	
	/**
	 * This function uses a regex expression [A-Z] that checks to see if there is an 
	 * uppercase letter in the password.
	 */
	public static int upperCase(String s)
	{
		Pattern upper = Pattern.compile("[A-Z]");
		Matcher m = upper.matcher(s);
		if (m.find())
			return 1;
		else 
			return 0;
	}
	
	/**
	 * This function uses a regex expression [a-z] that checks to see if there is a 
	 * lowercase letter in the password.
	 */
	public static int lowerCase(String s)
	{
		Pattern lower = Pattern.compile("[a-z]");
		Matcher m = lower.matcher(s);
		if (m.find())
			return 1;
		else
			return 0;
	}
	
	/**
	 * This function uses a regex expression [0-9] that checks to see if there 
	 * is a number in the password.
	 */
	public static int digitize(String s)
	{
		Pattern digit = Pattern.compile("[0-9]");
		Matcher m = digit.matcher(s);
		if (m.find())
			return 1;
		else
			return 0;
	}
}
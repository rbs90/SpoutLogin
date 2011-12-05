package de.rbs90.bukkit.plugins.spoutlogin.guielements;

import java.util.HashMap;
import java.util.Map;

import org.getspout.spoutapi.gui.*;

import de.rbs90.bukkit.plugins.spoutlogin.MainAuthentificate;
import de.rbs90.bukkit.plugins.spoutlogin.crypting.UserData;

public class RegisterPopup extends GenericPopup{
	
	private Map<String, GenericLabel> labels = new HashMap<String, GenericLabel>();
	private Map<String, GenericTextField> textFields = new HashMap<String, GenericTextField>();
	private String[] order = {"name", "email", "pass1", "pass2"};
	
	private GenericButton regButton;
	private final MainAuthentificate main;
	private GenericLabel errorLabel;
	
	private UserData data;
	
	public RegisterPopup(MainAuthentificate main) {
		this.main = main;
		labels.put("name", new GenericLabel("Name (ingame):"));
		labels.put("email", new GenericLabel("email address (should be valid, only used for password reset):"));
		labels.put("pass1", new GenericLabel("password:"));
		labels.put("pass2", new GenericLabel("retype password:"));
		//labels.put("birth", new GenericLabel("birthday (optional):"));
		//labels.put("", new GenericLabel("age (optional):"));
		
		textFields.put("name", new GenericTextField());
		textFields.put("email", new GenericTextField());
		textFields.put("pass1", new GenericTextField());
		textFields.put("pass2", new GenericTextField());
		
		regButton = new GenericButton("Send Registration");
		
		errorLabel = new GenericLabel();
		
		draw();
	}
	
	private void draw(){
		
		int count = 0;
		for (String elem: order)
		{
			labels.get(elem).setX(5);
			labels.get(elem).setY(count*35 + 5);
			labels.get(elem).setWidth(getMaxWidth() - 10);
			labels.get(elem).setHeight(10);
			
			textFields.get(elem).setX(5);
			textFields.get(elem).setY(count*35 + 20);
			textFields.get(elem).setWidth(getMaxWidth() - 10);
			textFields.get(elem).setHeight(15);
			textFields.get(elem).setMaximumCharacters(40);
			if (elem.startsWith("pass"))
				textFields.get(elem).setPasswordField(true);
			
			attachWidget(getPlugin(), labels.get(elem));
			attachWidget(getPlugin(), textFields.get(elem));
			
			count ++;
		}
		
		regButton.setX(10);
		regButton.setY(getMaxHeight() - 25);
		regButton.setWidth(getMaxWidth() - 20);
		regButton.setHeight(15);
		attachWidget(getPlugin(), regButton);
		
		errorLabel.setX(10);
		errorLabel.setY(getMaxHeight() - 35);
		errorLabel.setWidth(getMaxWidth() - 20);
		errorLabel.setHeight(15);
		errorLabel.setVisible(false);
		attachWidget(getPlugin(), regButton);
	}

	public boolean proveData() {
		
		resetErrorFields();
		
		String name = textFields.get("name").getText();
		String email = textFields.get("email").getText();
		String pass1 = textFields.get("pass1").getText();
		String pass2 = textFields.get("pass2").getText();
		data = new UserData(name, email, pass1, false);
		
		
		String error = null; String error_elem = null;
		if(!main.loginChecker.isNameAvailable(name)){
			error = "Username already taken.";
			error_elem = "name";
		}
		else if (!isValidMail(email)){
			error = "No valid email adress.";
			error_elem = "email";
		}
		else if (!pass1.equals(pass2)){
			error = "passwords are not equal.";
			error_elem = "pass2";
		}
			
		
		if (error == null)
			return true;
		else{
			textFields.get(error_elem).setBorderColor(new Color(255, 0, 0));
			textFields.get(error_elem).setText("");
			if (error_elem.equals("pass2"))
				textFields.get("pass1").setText("");
			
			errorLabel.setText(error);
			errorLabel.setVisible(true);
			return false;
		}
	}
	
	private boolean isValidMail(String mail){
		String[] split = mail.split("@");
		if (split.length < 2)
			return false;
		if (!split[1].contains("."))
			return false;
		
		return true;
	}
	
	private void resetErrorFields()
	{
		for (GenericTextField field : textFields.values())
			field.setBorderColor(new GenericTextField().getBorderColor());
	}

	public UserData getUserData() {
		return data;
	}

	
	

}

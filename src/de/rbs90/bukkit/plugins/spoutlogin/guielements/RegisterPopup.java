package de.rbs90.bukkit.plugins.spoutlogin.guielements;

import java.util.ArrayList;

import org.bukkit.configuration.ConfigurationSection;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTextField;

import de.rbs90.bukkit.plugins.spoutlogin.MainAuthentificate;
import de.rbs90.bukkit.plugins.spoutlogin.crypting.UserData;

public class RegisterPopup extends GenericPopup{
	
	private ArrayList <GenericLabel> labels = new ArrayList<GenericLabel>();
	private ArrayList<GenericTextField> textFields = new ArrayList<GenericTextField>();
	private GenericButton regButton;
	private final MainAuthentificate main;
	private GenericLabel errorLabel;
	
	private UserData data;
	private ConfigurationSection serverPassSettings;
	
	public RegisterPopup(MainAuthentificate main) {
		
		this.main = main;
		serverPassSettings = MainAuthentificate.settings.getServerPassSettings();
		labels.add(new GenericLabel("Name (ingame):"));
		labels.add(new GenericLabel("email address (should be valid, only used for password reset):"));
		labels.add(new GenericLabel("password:"));
		labels.add(new GenericLabel("retype password:"));
		System.out.println("servpass=" + serverPassSettings.getBoolean("enabled"));
		if (serverPassSettings.getBoolean("enabled"))
			labels.add(new GenericLabel("Server password:"));
		//labels.put("birth", new GenericLabel("birthday (optional):"));
		//labels.put("", new GenericLabel("age (optional):"));
		
		textFields.add(new GenericTextField());
		textFields.add(new GenericTextField());
		textFields.add(new GenericTextField());
		textFields.add(new GenericTextField());
		if (serverPassSettings.getBoolean("enabled"))
			textFields.add(new GenericTextField());
		
		regButton = new GenericButton("Send Registration");
		errorLabel = new GenericLabel();
		
		draw();
	}
	
	private void draw(){
		
		int count = 0;
		for (int i = 0; i < labels.size(); i++)
		{
			labels.get(i).setX(5);
			labels.get(i).setY(count*35 + 5);
			labels.get(i).setWidth(getMaxWidth() - 10);
			labels.get(i).setHeight(10);
			
			textFields.get(i).setX(5);
			textFields.get(i).setY(count*35 + 20);
			textFields.get(i).setWidth(getMaxWidth() - 10);
			textFields.get(i).setHeight(15);
			textFields.get(i).setMaximumCharacters(40);
			if (i > 1)
				textFields.get(i).setPasswordField(true);
			
			attachWidget(getPlugin(), labels.get(i));
			attachWidget(getPlugin(), textFields.get(i));
			
			count ++;
		}
		
		regButton.setX(getMaxWidth() / 2 - 50 );
		regButton.setY(getMaxHeight() - 25);
		regButton.setWidth(100);
		regButton.setHeight(15);
		attachWidget(getPlugin(), regButton);
		
		errorLabel.setX(10);
		errorLabel.setY(getMaxHeight() - 35);
		errorLabel.setWidth(getMaxWidth() - 20);
		errorLabel.setHeight(15);
		errorLabel.setVisible(false);
		attachWidget(getPlugin(), regButton);
		attachWidget(getPlugin(), errorLabel);
	}

	public boolean proveData() {
		
		resetErrorFields();
		
		String name = textFields.get(0).getText();
		String email = textFields.get(1).getText();
		String pass1 = textFields.get(2).getText();
		String pass2 = textFields.get(3).getText();
		
		
		
		data = new UserData(name, email, pass1, false);
		
		
		String error = null; int error_elem = -1;
		if(!main.loginChecker.isNameAvailable(name)){
			error = "Username already taken.";
			error_elem = 0;
		}
		else if (!isValidMail(email)){
			error = "No valid email adress.";
			error_elem = 1;
		}
		else if (!pass1.equals(pass2)){
			error = "passwords are not equal.";
			error_elem = 3;
		}
		if (serverPassSettings.getBoolean("enabled"))
			if(!textFields.get(4).getText().equals(serverPassSettings.getString("password").trim())){
				System.out.println("should be: " + serverPassSettings.get("password") + " is: " + textFields.get(4).getText() + "end");
				error = "Server password incorrect!";
				error_elem = 4;
			}
		if (error == null){
			return true;
		}
		else{
			textFields.get(error_elem).setBorderColor(new Color(255, 0, 0));
			textFields.get(error_elem).setText("");
			if (error_elem == 3)
				textFields.get(2).setText("");
			
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
		for (GenericTextField field : textFields)
			field.setBorderColor(new GenericTextField().getBorderColor());
	}

	public UserData getUserData() {
		return data;
	}

	
	

}

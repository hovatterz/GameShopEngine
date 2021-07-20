package com.lyndenjayevans.gameshopllc.gameshopengine;

import java.util.ArrayList;

public class GameShopShader {

	public ArrayList<String> arguments;
	public GameShopShader(String... args) {
		
		arguments = new ArrayList<String>();
		
		for (String s : args) {
			arguments.add(s);
		}
	}
	
	public String shader() {
		
		return "";
	}
}

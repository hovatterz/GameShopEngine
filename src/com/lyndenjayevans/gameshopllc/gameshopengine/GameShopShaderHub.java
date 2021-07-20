package com.lyndenjayevans.gameshopllc.gameshopengine;

import java.util.ArrayList;

public class GameShopShaderHub {

	private static GameShopShaderHub _instance;
	public ArrayList<GameShopShaderProgram> gShopShaders;
	
	private GameShopShaderHub() {
		gShopShaders = new ArrayList<GameShopShaderProgram>();
		
	}
	
	public static GameShopShaderHub getInstance() {
		
		if (_instance == null) {
			_instance = new GameShopShaderHub();
		}
		
		return _instance;
	}
	
	public GameShopShaderProgram getShader(String name) {
		
		for (GameShopShaderProgram js: gShopShaders) {
			if (js.name == name) {
				return js;
			}
		}
		return null;
		
	}
}

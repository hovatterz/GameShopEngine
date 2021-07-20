package com.lyndenjayevans.gameshopllc.gameshopengine;

//import java.util.ArrayList;
//
//import org.joml.Vector3f;

public class GameShopGLNumStack {

	private static GameShopGLNumStack _instance;
	private int numstack;
	
	private GameShopGLNumStack() {
		numstack = 0;
		}
	
	public static GameShopGLNumStack getInstance() {
		if (_instance == null) {
			_instance = new GameShopGLNumStack();
		}
		return _instance;
	}
	
	public int getNewNum() {
		int i = numstack;
		numstack++;
		return i;
	}
}

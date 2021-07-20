package com.lyndenjayevans.gameshopllc.gameshopengine;

import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class GameShopLightingHub {

	private static GameShopLightingHub _instance;
	public ArrayList<GameShopPointLight> gshopPointLight;
	
	private GameShopLightingHub() {
		gshopPointLight = new ArrayList<GameShopPointLight>();
	}
	
	public static GameShopLightingHub getInstance() {
		
		if( _instance == null) {
			_instance = new GameShopLightingHub();
		}
		return _instance;
	}
	
	public ArrayList<Vector3f> getLightPositions(){
		ArrayList<Vector3f> lightPositions = new ArrayList<Vector3f>();
		for (GameShopPointLight j: gshopPointLight) {
			lightPositions.add(j.lightPositions);
		}
		return lightPositions;
	}
	
	public ArrayList<Vector2f> getlightIntensitiesandDistances(){
		ArrayList<Vector2f> lightIntensities = new ArrayList<Vector2f>();
		for (GameShopPointLight j: gshopPointLight) {
			lightIntensities.add(j.lightIntensityAndDistance);
		}
		return lightIntensities;
	}
}

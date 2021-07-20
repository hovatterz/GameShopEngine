package com.lyndenjayevans.gameshopllc.gameshopengine;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class GameShopPointLight {

	public Vector3f lightPositions;
	public Vector2f lightIntensityAndDistance;
	
	public GameShopPointLight() {
		this.lightPositions = new Vector3f();
		this.lightIntensityAndDistance = new Vector2f();
	}
	
	public GameShopPointLight(Vector3f lightPositions, Vector2f lightIntensityAndDistance) {
		
		this.lightPositions = new Vector3f();
		this.lightIntensityAndDistance = new Vector2f();
		this.lightPositions = lightPositions;
		this.lightIntensityAndDistance = lightIntensityAndDistance;
		
	}
}

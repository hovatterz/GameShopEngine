package com.lyndenjayevans.gameshopllc.gameshopengine;

import java.util.ArrayList;

import org.joml.Vector3f;

public class GameShopCameraHub {

	private static GameShopCameraHub _instance;
	public ArrayList<GameShopCamera> gShopCameras;
	public GameShopCamera mainCamera;
	
	private GameShopCameraHub() {
		gShopCameras = new ArrayList<GameShopCamera>();
		mainCamera = new GameShopCamera("placeholder", new Vector3f(), new Vector3f(), new Vector3f());
	}
	
	public static GameShopCameraHub getInstance() {
		if (_instance == null) {
			_instance = new GameShopCameraHub();
		}
		return _instance;
	}
	
	public GameShopCamera getCamera(String name) {
		for (GameShopCamera j: gShopCameras) {
		if (j.name.equals(name)) {
			return j;
		}
	}
		return null;
	}
	
//	public void setMainCamera(String name) {
//		
//		for (JynxCamera j: jynxCameras) {
//			if (j.name.equals(name)) {
//				mainCamera = j;
//			}
//		}
//		
//	}
}

package com.lyndenjayevans.gameshopllc.gameshopengine;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import java.util.Timer;
import java.util.TimerTask;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
//import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.joml.*;
import org.joml.Math;

public class GameShopRuntime {

	
	private GameShopWindow window;
	
	private Timer timer;
	private int frames;
	private float fps;
	
	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");
		//timer = new Timer();
		
		window = new GameShopWindow(1280, 720, "Hello GameShop");
//		JynxObject redSquare = new JynxObject();
//		redSquare.jynxPolys.add(new JynxPoly(0));
//		window.jynxObjects.add(redSquare);
		//new JynxWindow(1280, 720, "Hello Dude"); you can only have 1 window at a time
	}
	
	
	
}

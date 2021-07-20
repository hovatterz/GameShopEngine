package com.lyndenjayevans.gameshopllc.gameshopengine;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.nuklear.NkRect;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glGetFloat;
import static org.lwjgl.opengl.GL30.GL_MAJOR_VERSION;
//import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.joml.*;
import org.joml.Math;

public class GameShopWindow {

	private long window;
	private int width;
	private int height;
	private String name;
	public ArrayList<GameShopObject> gshopObjects;
	
	public GameShopWindow(int width, int height, String name) {
		this.width = width;
		this.height = height;
		this.name = new String(name);
		this.gshopObjects = new ArrayList<GameShopObject>();
		init();
		loop();
		terminate();
	}

	private void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
		// before window creation
		glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);
		// Create the window
		window = glfwCreateWindow(width, height, name, NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
		
		//OpenOnce
		GameShopImageFileReader fileReader = new GameShopImageFileReader();
		fileReader.readFile();
	}
	private void terminate() {
		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
		
	}

	

	private void loop() {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();
		System.out.println(glGetFloat(GL_MAJOR_VERSION));
		// Set the clear color
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glEnable(GL_DEPTH_TEST);
//		timer.scheduleAtFixedRate(new TimerTask() {
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				fps = frames;
//				System.out.println("FPS: " + fps);
//				frames = 0;
//			}
//			
//			
//		}, 0, 1000);

		GameShopCameraHub.getInstance().gShopCameras.add(new GameShopCamera("main", new Vector3f(0.0f, 0.0f, 5.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.0f, 1.0f, 0.0f)));
		
		GameShopShader vertex_shader = new GameShopShader() {
			
			public String shader() {
				return new String(
						"#version 400\n" +
						"in vec3 vp;" +
						"uniform mat4 mvp;" +
						"out vec3 out_vp;"+
						"void main() {" +
						"  vec3 _vp = vp;"+
						"  out_vp = _vp;"+
						"  gl_Position = mvp * vec4(vp, 1.0);" +
						"}");
			}
		};
		
		GameShopShader fragment_shader = new GameShopShader("1", "1") {
			
			public String shader() {
				
				return new String(new String(
						"#version 400\n" +
						"in vec3 out_vp;" +
						"uniform vec4 mycol;"+				
						"uniform vec3 lightPositions[" + this.arguments.get(0)+"];"+
						"uniform vec2 lDistXlIntensityY[" + this.arguments.get(1)+"];"+
						//"" +
						"out vec4 frag_colour;" +
						
						"void main() {" +
						
						"vec4 final_color[lightPositions.length()];" +
						"vec4 render_color = vec4(0.0,0.0,0.0,0.0);" +
						"for(int i = 0; i < lightPositions.length(); i++){"+
					
						"	if(distance(lightPositions[i], out_vp) < lDistXlIntensityY[i].x){"+
						"	float dist = lDistXlIntensityY[i].x - distance(lightPositions[i], out_vp);"+
						"	float intensity = lDistXlIntensityY[i].y;" +
						"	final_color[i] = mycol * dist * intensity;"+		
						"}"+
						" else {" +
						"	final_color[i] =  vec4(0.0,0.0,0.0,1.0);" +
						"}" +
						"}"+
						"for(int i = 0; i < lightPositions.length(); i++){"+
						
						
						"	render_color = max(render_color, final_color[i]);"+
								
						
						"}"+
						"" +
//						"if (gl_Position.z  < 0.1f){" +
//							"  frag_colour = vec4(0.0,0.0,0.0,0.0);"+
//							"}" +
						"  frag_colour = render_color;"+
						"}"));
			}
		};
		
		GameShopShaderHub.getInstance().gShopShaders.add(new GameShopShaderProgram("standard", vertex_shader, fragment_shader));
		//JynxCameraHub.getInstance().setMainCamera("main");
		
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ((key == GLFW_KEY_W && action == GLFW_PRESS) || (key == GLFW_KEY_W && action == GLFW_REPEAT) ) {
				GameShopCameraHub.getInstance().getCamera("main").translateAxial(new Vector3f(0f, 0f, -1f));
				initPolys();
			}
			if ((key == GLFW_KEY_S && action == GLFW_PRESS) || (key == GLFW_KEY_S && action == GLFW_REPEAT) ) {
				GameShopCameraHub.getInstance().getCamera("main").translateAxial(new Vector3f(0f, 0f, 1f));
				initPolys();
			}
			if ((key == GLFW_KEY_A && action == GLFW_PRESS) || (key == GLFW_KEY_A && action == GLFW_REPEAT) ) {
				GameShopCameraHub.getInstance().getCamera("main").rotate(new Vector3f(0f, -1f, 0f));
				initPolys();
			}	
			if ((key == GLFW_KEY_D && action == GLFW_PRESS) || (key == GLFW_KEY_D && action == GLFW_REPEAT) ) {
				GameShopCameraHub.getInstance().getCamera("main").rotate(new Vector3f(0f, 1f, 0f));
				initPolys();
			}	
			
			if ((key == GLFW_KEY_Q && action == GLFW_PRESS) || (key == GLFW_KEY_Q && action == GLFW_REPEAT) ) {
				GameShopCameraHub.getInstance().getCamera("main").rotate(new Vector3f(1f, 0f, 0f));
				initPolys();
			}	
			
			if ((key == GLFW_KEY_E && action == GLFW_PRESS) || (key == GLFW_KEY_E && action == GLFW_REPEAT) ) {
				GameShopCameraHub.getInstance().getCamera("main").rotate(new Vector3f(-1f, 0f, 0f));
				initPolys();
			}	
		});
		
		GameShopLightingHub.getInstance().gshopPointLight.add(new GameShopPointLight(new Vector3f(0f,0f,0f), new Vector2f(10f, 1f)));
		GameShopObject bluCube = new GameShopObject();
		bluCube.makeCube();
//		for (JShopPoly p : bluCube.jshopPolys) {
//			p.init();
//		}
		gshopObjects.add(bluCube);
		
//		for(JShopPoly j: jshopObjects.get(0).jshopPolys) {
//			j.initialize();
//		}
		initPolys();
		
		
		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		//boolean isDirty = true;
		while ( !glfwWindowShouldClose(window) ) {
			//System.gc();
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
			//try (MemoryStack stack = stackPush()) {
			//draw(stack);
			//gravityEffect();
			//if (isDirty) {
		//	genObjects();
			draw();
			
			//isDirty = false;
			//}
			//drawWithMatrixAndLights();
			//drawBezierSurface();
			//frames++;
			glfwSwapBuffers(window); // swap the color buffers
			//}
			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
		}
	}
	

	
	public void initPolys() {
		
		for (GameShopObject jo: gshopObjects) {
				for (GameShopPoly j : jo.gshopPolys) {
					j.init();
			
				}
		}
	}
	private void gravityEffect() 
	{
		Vector3f gravPoint = new Vector3f(-3f,-0f,-3f);
		for (GameShopObject j : gshopObjects) {
			//j.center = new Vector3f();
			//float remainingDistance = Vector3f.distance(j.center.x, j.center.y, j.center.z, gravPoint.x, gravPoint.y, gravPoint.z);
			Vector3f directionNormal = new Vector3f(gravPoint.x - j.center.x, gravPoint.y - j.center.y, gravPoint.z - j.center.z);
			float highestVal = Math.abs(directionNormal.x);
			if (highestVal < directionNormal.y) {
				highestVal = Math.abs(directionNormal.y);
			}
			
			if (highestVal < directionNormal.z) {
				highestVal = Math.abs(directionNormal.z);
			}
			
			if (highestVal > .2f) {
				directionNormal = new Vector3f(directionNormal.x / highestVal, directionNormal.y/highestVal, directionNormal.z/highestVal);
				j.center = new Vector3f(j.center.x + directionNormal.x * (1f/6f),j.center.y + directionNormal.y * (1f/6f),j.center.z + directionNormal.z * (1f/6f));
			} else {
				continue;
			}
			
			
		}
		
	}
	private void draw() {
		
		for (GameShopObject jo : gshopObjects) {
			for (GameShopPoly p: jo.gshopPolys) {
				
				p.draw();
			}

		}
	}
}

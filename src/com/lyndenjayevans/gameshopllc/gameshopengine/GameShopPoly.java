package com.lyndenjayevans.gameshopllc.gameshopengine;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform2fv;
import static org.lwjgl.opengl.GL20.glUniform3fv;
import static org.lwjgl.opengl.GL20.glUniform4fv;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL46;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class GameShopPoly {

	int type;
	GameShopObject sender;
	public ArrayList<GameShopVertexRegistry> vertexRegistry;
	public Vector3f center;
//	int shader_programme;
//	Vector3f[] lightPositions;// = new Vector3f[JShopLightingHub.getInstance().getLightPositions().size()]; 
//	Vector2f[] lDistXlIntensityY;// = new Vector2f[JShopLightingHub.getInstance().getLightPositions().size()];
//	int vertexCount;
//	float[] positions;
//	int[] indices;
	
	public float[] positions;
	
		    int[] indices = new int[]{
		        0, 1, 2
		    };
		    Vector3f[] lightPositions; 
		    Vector2f[] lDistXlIntensityY;
		    int shader_programme;	
		    
		    int vaoId  = -1;
			  int vboId = -1;
			  int idxVboId = -1;
			  int vertexCount;
			  FloatBuffer verticesBuffer = null;
			  IntBuffer indicesBuffer = null;
			  FloatBuffer mvpBuffer= null;
			  FloatBuffer colorBuffer = null;
				 
				 FloatBuffer lightPositionsBuffer = null;
				FloatBuffer intensityBuffer = null;
				
				FloatBuffer distanceBuffer = null;
				
				FloatBuffer lDistXlIntensityYBuffer = null;
				
				FloatBuffer worldBuffer = null;
				int buffId;
				Vector4f color;
	public GameShopPoly(GameShopObject sender, int type) {
		this.type = type;
		this.sender = sender;
		this.vertexRegistry = new ArrayList<GameShopVertexRegistry>();
	}
	
	public GameShopPoly(ArrayList<GameShopVertexRegistry> vertexRegistry, Vector4f color) {
		this.vertexRegistry = new ArrayList<GameShopVertexRegistry>();
		this.vertexRegistry.addAll(vertexRegistry);
		lightPositions = new Vector3f[GameShopLightingHub.getInstance().getLightPositions().size()]; 
	    lDistXlIntensityY = new Vector2f[GameShopLightingHub.getInstance().getLightPositions().size()];
	    GameShopShaderHub.getInstance().getShader("standard").fragment_shader.arguments.set(0, "" + lightPositions.length);
		  GameShopShaderHub.getInstance().getShader("standard").fragment_shader.arguments.set(1, "" + lightPositions.length);
		  shader_programme = GameShopShaderHub.getInstance().getShader("standard").compileShader();
		  positions =  new float[]{
					this.vertexRegistry.get(0).vertex.x, this.vertexRegistry.get(0).vertex.y, this.vertexRegistry.get(0).vertex.z,
					this.vertexRegistry.get(1).vertex.x, this.vertexRegistry.get(1).vertex.y, this.vertexRegistry.get(1).vertex.z,
					this.vertexRegistry.get(2).vertex.x, this.vertexRegistry.get(2).vertex.y, this.vertexRegistry.get(2).vertex.z
					
				    };
		  this.color = color;
	}
	
	public void calculateCenter() {
		
//		float x = (this.vertexRegistry.get(0).vertex.x + this.vertexRegistry.get(1).vertex.x +this.vertexRegistry.get(2).vertex.x) / 3f;
//		float y = (this.vertexRegistry.get(0).vertex.y + this.vertexRegistry.get(1).vertex.y +this.vertexRegistry.get(2).vertex.y) / 3f;
//		float z = (this.vertexRegistry.get(0).vertex.z + this.vertexRegistry.get(1).vertex.z +this.vertexRegistry.get(2).vertex.z) / 3f;
//		
//	center = new Vector3f(x,y,z);	
		
		//find longest line
		//Vector3f.distance(vboId, vaoId, type, shader_programme, idxVboId, buffId)
		
		float lineA = Vector3f.distance(this.vertexRegistry.get(0).vertex.x,this.vertexRegistry.get(0).vertex.y,this.vertexRegistry.get(0).vertex.z,this.vertexRegistry.get(1).vertex.x,this.vertexRegistry.get(1).vertex.y,this.vertexRegistry.get(1).vertex.z);
		float lineB = Vector3f.distance(this.vertexRegistry.get(1).vertex.x,this.vertexRegistry.get(1).vertex.y,this.vertexRegistry.get(1).vertex.z,this.vertexRegistry.get(2).vertex.x,this.vertexRegistry.get(2).vertex.y,this.vertexRegistry.get(2).vertex.z);
		float lineC = Vector3f.distance(this.vertexRegistry.get(2).vertex.x,this.vertexRegistry.get(2).vertex.y,this.vertexRegistry.get(2).vertex.z,this.vertexRegistry.get(0).vertex.x,this.vertexRegistry.get(0).vertex.y,this.vertexRegistry.get(0).vertex.z);
		GameShopVertexRegistry[] currentLine = new GameShopVertexRegistry[2];
		
		
		if (lineA >= lineB && lineA >= lineC) {
			currentLine[0] = this.vertexRegistry.get(0);
			currentLine[1] = this.vertexRegistry.get(1);
			
		} else if (lineB >= lineC && lineB >= lineA) {
			currentLine[0] = this.vertexRegistry.get(1);
			currentLine[1] = this.vertexRegistry.get(2);
			
		} else if (lineC >= lineA && lineC >= lineB) {
			currentLine[0] = this.vertexRegistry.get(2);
			currentLine[1] = this.vertexRegistry.get(0);
			
		} 
		
		System.out.println(center);
	}
	
	public void draw() {
		try (MemoryStack stack = MemoryStack.stackPush()){
	 	verticesBuffer = stack.mallocFloat(positions.length);
        vertexCount = indices.length;
        verticesBuffer.put(positions).flip();

        if (vaoId == -1) {
        //vaoId = glGenVertexArrays();
        //System.out.println("vaoId " + vaoId);
        vaoId = GameShopGLNumStack.getInstance().getNewNum();
       }
        
        glBindVertexArray(vaoId);

        if (vboId == -1) {
//        vboId = glGenBuffers();
//        System.out.println("vboId " + vboId);
        vboId = GameShopGLNumStack.getInstance().getNewNum();
        }
       
        //GL15.glgen
        //System.out.println(vboId);
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);            
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindVertexArray(vboId); 
        
        if (idxVboId == -1) {
//        idxVboId = glGenBuffers();
//        System.out.println("idxVboId " + idxVboId);
        idxVboId = GameShopGLNumStack.getInstance().getNewNum();
        }
       
        //indicesBuffer = MemoryUtil.memAllocInt(indices.length);
      //  System.out.println(idxVboId);
        indicesBuffer = stack.mallocInt(indices.length);
        indicesBuffer.put(indices).flip();
      //  System.out.println(indicesBuffer.toString());
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, idxVboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
        
        glBindVertexArray(idxVboId);
        	 
		glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        
        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
        
		}
	}
	public void init() {
		 // int vaoId;
//
//		  int vboId;
//
//		  int vertexCount;
//		  FloatBuffer verticesBuffer = null;
//		  IntBuffer indicesBuffer = null;
//		  FloatBuffer mvpBuffer= null;
//		  FloatBuffer colorBuffer = null;
//			 
//			 FloatBuffer lightPositionsBuffer = null;
//			FloatBuffer intensityBuffer = null;
//			
//			FloatBuffer distanceBuffer = null;
//			
//			FloatBuffer lDistXlIntensityYBuffer = null;
//			
//			FloatBuffer worldBuffer = null;
			  //  this.buffId = buffId;
			
			
			
			GameShopLightingHub.getInstance().getLightPositions().toArray(lightPositions);//JynxLightingHub.getInstance().jynxPointLights.
			GameShopLightingHub.getInstance().getlightIntensitiesandDistances().toArray(lDistXlIntensityY);
	
	        try (MemoryStack stack = MemoryStack.stackPush()) {
	           
	        	//verticesBuffer = MemoryUtil.memAllocFloat(positions.length);
	            //vertexCount = positions.length / 3;
	            
	       
	            
	       // }
	        //try (MemoryStack stack = MemoryStack.stackPush()){
		  
		  

		   
		   //mvpBuffer = JShopCameraHub.getInstance().getCamera("main").mvp.get(MemoryUtil.memAllocFloat(16));
		   mvpBuffer = GameShopCameraHub.getInstance().getCamera("main").mvp.get(stack.mallocFloat(16));
		   
				  glUniformMatrix4fv(GL46.glGetUniformLocation(shader_programme, "mvp"), false, mvpBuffer);
		
		   //colorBuffer = new Vector4f(0f, 0f, 1f, 1f).get(MemoryUtil.memAllocFloat(4));
				  colorBuffer = new Vector4f(this.color).get(stack.mallocFloat(4));
		  
		glUniform4fv(GL46.glGetUniformLocation(shader_programme, "mycol"), colorBuffer); //col
		 
		  //lightPositionsBuffer = MemoryUtil.memAllocFloat(lightPositions.length * 3);
		 lightPositionsBuffer = stack.mallocFloat(lightPositions.length * 3);
		  int vi = 0;
		 for (Vector3f v: lightPositions) {
			 v.get(vi, lightPositionsBuffer);
			 vi += 3;
		 }
		 glUniform3fv( GL46.glGetUniformLocation(shader_programme, "lightPositions"), lightPositionsBuffer);
		
		 
		 
		  //lDistXlIntensityYBuffer = MemoryUtil.memAllocFloat(lDistXlIntensityY.length * 2);
		  lDistXlIntensityYBuffer = stack.mallocFloat(lDistXlIntensityY.length * 2);
			
		  int di = 0;
			 for (Vector2f v: lDistXlIntensityY) {
				 v.get(di, lDistXlIntensityYBuffer);
				 di += 2;
			 }
			 glUniform2fv( GL46.glGetUniformLocation(shader_programme, "lDistXlIntensityY"), lDistXlIntensityYBuffer);
		 
		        // Draw the mesh
		        //glBindVertexArray(vaoId);
		        
		        // Restore state
		   //     glDisableVertexAttribArray(0);
		   //     glBindVertexArray(0);

		     //   glUseProgram(0);
	        } 
	        
//	         verticesBuffer = null;
//			   indicesBuffer = null;
//			   mvpBuffer= null;
//			   colorBuffer = null;
//				 
//				  lightPositionsBuffer = null;
//				 intensityBuffer = null;
//				
//				 distanceBuf)fer = null;
//				
//				 lDistXlIntensityYBuffer = null;
//				
//				 worldBuffer = null;
//	        System.gc();
	       
	}
	

}

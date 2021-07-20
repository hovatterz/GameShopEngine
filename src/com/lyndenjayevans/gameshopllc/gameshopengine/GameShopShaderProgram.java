package com.lyndenjayevans.gameshopllc.gameshopengine;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;

import org.lwjgl.opengl.GL46;

public class GameShopShaderProgram {

	public String name;
	public GameShopShader vertex_shader;
	public GameShopShader fragment_shader;
	public int shader_program_number;
	
	public GameShopShaderProgram(String name, GameShopShader vert, GameShopShader frag) {
		this.vertex_shader = vert;
		this.fragment_shader = frag;
		this.name = name;
	}
	
	public int compileShader() {
		
		int vs = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vs, this.vertex_shader.shader());
		
		glCompileShader(vs);
		int fs = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fs, this.fragment_shader.shader());
		glCompileShader(fs);
		
		int shader_programme = glCreateProgram();
		glAttachShader(shader_programme, fs);
		glAttachShader(shader_programme, vs);
		glLinkProgram(shader_programme);
		 //System.out.println("Matrix " + GL46.glGetUniformLocation(shader_programme, "mvp"));
		// System.out.println("Vector4 " + GL46.glGetUniformLocation(shader_programme, "mycol"));
		// System.out.println("lp " + GL46.glGetUniformLocation(shader_programme, "lightPositions"));
		// System.out.println("ldli " + GL46.glGetUniformLocation(shader_programme, "lDistXlIntensityY"));
		glUseProgram(shader_programme);
		  
		  this.shader_program_number = shader_programme;
		  return shader_programme;
	}
}

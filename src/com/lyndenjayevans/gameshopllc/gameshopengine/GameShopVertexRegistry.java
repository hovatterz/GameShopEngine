package com.lyndenjayevans.gameshopllc.gameshopengine;

import java.util.ArrayList;

import org.joml.Vector3f;

public class GameShopVertexRegistry {

	public String vertexRegister; //nomenclature: inst_cordx_cordy_cordz_instancenumber
	public Vector3f vertex;
	public ArrayList<String> connectedVertices;
	
	public GameShopVertexRegistry() {
		
	}
	
	public GameShopVertexRegistry(String vertexRegister, Vector3f vertex, ArrayList<String> connectedVertices) {
		this.vertexRegister = new String(vertexRegister);
		this.vertex = new Vector3f(vertex);
		this.connectedVertices = new ArrayList<String>();
		this.connectedVertices.addAll(connectedVertices);
	}
	
	public String toString() {
	
		StringBuilder sb = new StringBuilder();
		for (String s: connectedVertices) {
			sb.append(s + "\n");
		}
		
		return "vertexRegister: " + this.vertexRegister + "\n" +
		"vertex " + this.vertex.toString() + "connectedVertices: " + sb.toString();
	}
}

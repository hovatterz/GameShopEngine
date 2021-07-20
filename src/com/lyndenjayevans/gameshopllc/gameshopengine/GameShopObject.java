package com.lyndenjayevans.gameshopllc.gameshopengine;

import java.util.ArrayList;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class GameShopObject {

	public ArrayList<GameShopPoly> gshopPolys;
	//VertexRegistry
	public ArrayList<GameShopVertexRegistry> vertexRegistry;
	public Vector3f center;
	//everything about object here
	public GameShopObject() {
		gshopPolys = new ArrayList<GameShopPoly>();
		vertexRegistry = new ArrayList<GameShopVertexRegistry>();
		//makeCube();
	}
	
	
	public void makeCube() {
		ArrayList<Vector3f> startingVertices = new ArrayList<Vector3f>();
		startingVertices.add(new Vector3f(.5f,.5f,.5f));//0
		startingVertices.add(new Vector3f(-.5f,.5f,.5f));//1
		startingVertices.add(new Vector3f(.5f,-.5f,.5f));//2
		startingVertices.add(new Vector3f(-.5f,-.5f,.5f));//3
		startingVertices.add(new Vector3f(.5f,.5f, -.5f));//4
		startingVertices.add(new Vector3f(-.5f,.5f, -.5f));//5
		startingVertices.add(new Vector3f(.5f,-.5f, -.5f));//6
		startingVertices.add(new Vector3f(-.5f,-.5f, -.5f));//7
		
		ArrayList<String> names = new ArrayList<String>();
		
		for (Vector3f i: startingVertices) {
			names.add(new String("inst_" + i.x + "_" + i.y + "_" + i.z + "_" + "0"));
		}
		
		ArrayList<ArrayList<String>> connectedVertices = new ArrayList<ArrayList<String>>();
		
		for (Vector3f i: startingVertices) {
			connectedVertices.add(new ArrayList<String>());
		}
		
		for (int i = 0; i < startingVertices.size(); i++) {
			
			for (int j = 0; j < startingVertices.size(); j++) {
				
				if ((startingVertices.get(i).x == startingVertices.get(j).x && startingVertices.get(i).y == startingVertices.get(j).y) || (startingVertices.get(i).x == startingVertices.get(j).x && startingVertices.get(i).z == startingVertices.get(j).z) || (startingVertices.get(i).z == startingVertices.get(j).z && startingVertices.get(i).y == startingVertices.get(j).y)) {
					if (i != j) {
					connectedVertices.get(i).add(names.get(j));
					System.out.println(names.get(j));
					}
				}
			}
			System.out.println(" ");
		}
		
		for (int v = 0; v < startingVertices.size(); v++) {
			this.vertexRegistry.add(new GameShopVertexRegistry(names.get(v), startingVertices.get(v), connectedVertices.get(v)));
		}
		
		convertVertexRegistryToPolygons();
		
	}
	
	private void convertVertexRegistryToPolygons() {
		
		ArrayList<GameShopVertexRegistry> vertices = new ArrayList<GameShopVertexRegistry>();
		vertices.addAll(this.vertexRegistry);
		ArrayList<GameShopVertexRegistry> triangle = new ArrayList<GameShopVertexRegistry>();
		ArrayList<GameShopVertexRegistry> assessed = new ArrayList<GameShopVertexRegistry>();
		//ArrayList<JShopVertexRegistry> incomplete = new ArrayList<JShopVertexRegistry>();
		GameShopVertexRegistry prevAdd = new GameShopVertexRegistry();
		int m = 0;
		//int maxSize = vertices.size() * 3 / 2;
		boolean addAssessed = false;
		boolean lastVert = false;
		
		for (GameShopVertexRegistry v: vertices) {

		if (m == vertices.size() - 1) 
		{
			lastVert = true;
		}
			
		if (prevAdd != v) 
		{
			
			prevAdd = v;
			System.out.println("Ok");
			if (gshopPolys.size() > 1){// && jshopPolys.size() < 12) {
				addAssessed = true;
			}
		}
			if (v.connectedVertices.size() > 1) {
			for (int i = 0; i < v.connectedVertices.size(); i++) {
				boolean canAdd = true;
				triangle.clear();
				String firstVertex = "";
				String secondVertex = "";
				triangle.add(v);
			
			
			
				if (i < v.connectedVertices.size() - 1) {
					firstVertex = new String(v.connectedVertices.get(i));
					secondVertex = new String(v.connectedVertices.get(i+1));
	
				} 
				
				else {
					
					if (v.connectedVertices.size() > 2) {
						firstVertex = new String(v.connectedVertices.get(i));
						secondVertex = new String(v.connectedVertices.get(0));		
					}
					
				}
				
			
				if (!lastVert) {
				for (GameShopVertexRegistry a: assessed) {
					if (a.vertexRegister.equals(firstVertex) || a.vertexRegister.equals(secondVertex)) {
						//if (a.connectedVertices.contains(v.vertexRegister)) {
						
						canAdd = false;	
						//System.out.println("vregister: " + v.vertexRegister);
						//System.out.println("register: " + a.vertexRegister);					
					} 
				
				}	
				
				
				}
//	

			
				for (GameShopVertexRegistry v1: vertices) {
				if (v1.vertexRegister.equals(firstVertex) || v1.vertexRegister.equals(secondVertex)) {
						triangle.add(v1);

					}
				}
				
		

				
				
						
						if (canAdd) {
						System.out.println("PolyVertexCount " + triangle.size());
						GameShopPoly newTriangle = new GameShopPoly(triangle, new Vector4f(0f,0f,1f,1f));
						gshopPolys.add(newTriangle);
							}
						}
				
			
			
			System.out.println("JSPoly count " + gshopPolys.size());

			
		}

			
			if (addAssessed) {
				assessed.add(v);	
				addAssessed = false;
				
				}
				
	//
				m++;
				
		}
			
			
			System.out.println("assessed " + assessed.size());
		//	System.out.println("incomplete " + incomplete.size());
			
		
			
		
	}
	
	//calculateCenters();
	
	
//	private void calculateCenters() {
//		for (JShopPoly jp: jshopPolys) {
//			jp.calculateCenter();
//		}
//	}
	
}


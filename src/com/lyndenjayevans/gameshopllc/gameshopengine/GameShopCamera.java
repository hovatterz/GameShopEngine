package com.lyndenjayevans.gameshopllc.gameshopengine;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class GameShopCamera {

	Matrix4f mvp;
	//Matrix4f world;
	Vector3f eye;
	Vector3f center;
	Vector3f axis;
	//Vector3f angleAxis;
	Vector3f angle;
	String name;
	Vector3f diff;
	Vector3f axialTranslate;
	Vector3f currentPosition;
	
	public GameShopCamera(String name, Vector3f eye, Vector3f center, Vector3f axis) {
		
		this.eye = new Vector3f(eye.x, eye.y, eye.z);
		this.center = new Vector3f(center.x, center.y, center.z);
		this.axis = new Vector3f(axis.x, axis.y, axis.z);
		//this.angleAxis = new Vector3f(angleAxis.x, angleAxis.y, angleAxis.z);
	  diff = new Vector3f(this.center.x - this.eye.x, this.center.y - this.eye.y, this.center.z - this.eye.z);
		//System.out.println("diff: " + diff.toString());
		this.axialTranslate = new Vector3f(0f,0f,0f);
		//System.out.println("tan * diff.z: " + (float)Math.tan(Math.toRadians(angleAxis.y)) * diff.z);
		//this.center = new Vector3f(this.center.x + (angleAxis.x/180) + diff.x, this.center.y + angleAxis.y * diff.y, this.center.z + angleAxis.z * diff.z);
		
		//this.center = new Vector3f(this.center.x + (float)Math.tan(Math.toRadians(angleAxis.y)) * diff.z, this.center.y + (float)Math.tan(Math.toRadians(angleAxis.z)) * diff.z , this.center.z + (float)Math.tan(Math.toRadians(angleAxis.x)) * diff.z);	
		//angle
		
		this.angle = new Vector3f((float)(Math.toDegrees(Math.atan(diff.x))),(float)(Math.toDegrees(Math.atan(diff.y))), (float)(Math.toDegrees(Math.atan(diff.z))));
		System.out.println("angle " + this.angle);
		this.name = name;
		this.currentPosition = new Vector3f(0f,0f,0f);
		refineMatrix();
	}
	
	public void translate(Vector3f speed) {
		
		this.eye = new Vector3f(this.eye.x + speed.x, this.eye.y + speed.y, this.eye.z + speed.z);
		this.center = new Vector3f(this.center.x + speed.x, this.center.y + speed.y, this.center.z + speed.z);
		refineMatrix();
	}
	
	//Wrong function
	public void translateAxial(Vector3f speed) {
		//this.currentPosition = new Vector3f(this.currentPosition.x + speed.x * (float)Math.tan(Math.toRadians(angle.z)), this.currentPosition.y + speed.y * (float)Math.tan(Math.toRadians(angle.y)), this.currentPosition.z + speed.z * (float)Math.tan(Math.toRadians(angle.x)));
		//Vector3f diff = new Vector3f(this.center.x - this.eye.x, this.center.y - this.eye.y, this.center.z - this.eye.z);
		//Vector3f tan = new Vector3f( this.currentPosition.z,  this.currentPosition.x,  this.currentPosition.y);
	//	axialTranslate = tan;
//		this.eye = new Vector3f(this.eye.x + ((speed.z * tan.x))/10f, this.eye.y + ((speed.x * tan.y))/10f, this.eye.z + ((speed.y * tan.z))/10f);
//		this.center = new Vector3f(this.center.x + ((speed.z * tan.x))/10f, this.center.y + ((speed.x * tan.y))/10f, this.center.z + ((speed.y * tan.z))/10f);
//		
		//diff = new Vector3f(this.center.x - this.eye.x, this.center.y - this.eye.y, this.center.z - this.eye.z);
		
		this.eye = new Vector3f((float) (this.eye.x + speed.x - (Math.sin(Math.toRadians(angle.x)) * speed.z)), this.eye.y + speed.y,(float) (this.eye.z + (Math.cos(Math.toRadians(angle.x)) * speed.z)));
		this.center = new Vector3f(this.eye.x, this.eye.y, this.eye.z - 5f);
		refineMatrix();
		
		//axialTranslate = new Vector3f(0f,0f,0f);
		
	}
	
	public void rotate(Vector3f angleAxis) {
		//this.center = new Vector3f(this.center.x + (float)Math.tan(angleAxis.x), this.center.y + (float)Math.tan(angleAxis.y) * this.center.y, this.center.z + (float)Math.tan(angleAxis.z) * this.center.z ) ;
		//System.out.println("center' : " + (float)Math.tan(angleAxis.x) * this.center.x + " " + (float)Math.tan(angleAxis.y) * this.center.y + " " + (float)Math.tan(angleAxis.z) * this.center.z);
		
		//Vector3f diff = new Vector3f(this.center.x - this.eye.x, this.center.y - this.eye.y, this.center.z - this.eye.z);
		//System.out.println("diff: " + diff.toString());
		
		//System.out.println("tan * diff.z: " + (float)Math.tan(Math.toRadians(angleAxis.y)) * diff.z);
		//this.center = new Vector3f(this.center.x + (angleAxis.x/180) + diff.x, this.center.y + angleAxis.y * diff.y, this.center.z + angleAxis.z * diff.z);
		
		//this.center = new Vector3f(this.center.x + (float)Math.tan(Math.toRadians(angleAxis.y)) * diff.z, this.center.y + (float)Math.tan(Math.toRadians(angleAxis.z)) * diff.z , this.center.z + (float)Math.tan(Math.toRadians(angleAxis.x)) * diff.z);	
		//angle
		
		//Vector3f angle = new Vector3f((float)(Math.toDegrees(Math.atan(diff.x))),(float)(Math.toDegrees(Math.atan(diff.y))), (float)(Math.toDegrees(Math.atan(diff.z))));

		//		if (angle.x < -45f) {
//			 diff = new Vector3f(this.eye.x + this.center.x, this.center.y - this.eye.y, this.center.z - this.eye.z);
//		}
		
		
		
		
		
		
		this.angle = new Vector3f(this.angle.x + angleAxis.y, this.angle.y + angleAxis.z, this.angle.z + angleAxis.x);
		
		
		
		
		
		
		System.out.println("angle: " + angle.toString());
		//end angle
//		Vector3f dAngle = new Vector3f();
//		if (angle.x > -45f) {
//			dAngle = new Vector3f((float)(Math.toDegrees(Math.atan(diff.x)) + angleAxis.y ),(float)(Math.toDegrees(Math.atan(diff.y) + angleAxis.z)), (float)(Math.toDegrees(Math.atan(diff.z) + angleAxis.x)));
//		} else {
//			dAngle = new Vector3f((float)(Math.toDegrees(Math.atan(-diff.x)) + angleAxis.y ),(float)(Math.toDegrees(Math.atan(diff.y) + angleAxis.z)), (float)(Math.toDegrees(Math.atan(diff.z) + angleAxis.x)));
//			
//		}
		//Vector3f dAngle = new Vector3f((float)(Math.toDegrees(Math.atan(diff.x)) + angleAxis.y), (float)(Math.toDegrees(Math.atan(diff.y) + angleAxis.z)), (float)(Math.toDegrees(Math.atan(diff.z) + angleAxis.x)));
		
		//if (dAngle.x < -45f) {
		//	dAngle = new Vector3f((float)(Math.toDegrees(Math.atan(diff.x))),(float)(Math.toDegrees(Math.atan(diff.y + angleAxis.y))), (float)(Math.toDegrees(Math.atan(diff.z) + angleAxis.z)));
			
//		}
		//System.out.println("dAngle: " + dAngle);
		
		
		
		
		
		
		
		
		
		System.out.println("angle: " + angle);
//		float dAx = 0f;
//		float dAy = 0f;
//		float dAz = 0f;
//		
//		if (this.angle.x >= -45f) {
//			dAx = (float) (this.eye.x + (Math.tan(Math.toRadians(this.angle.x))));
//		} else {
//			float tanx = (float)((Math.tan(Math.toRadians(Math.abs((-45f -this.angle.x))))));
//			dAx = (float) (this.eye.x - 1f - lerp((float)tanx, (float)tanx * 4f, (float)((Math.tan(Math.toRadians(Math.abs((-45f - this.angle.x))))))));
//			System.out.println("tanx: " + tanx);
//			System.out.println("lerp: " + lerp((float)tanx, (float)tanx * 4f, (float)((Math.tan(Math.toRadians(Math.abs((-45f - this.angle.x))))))));
//			System.out.println(Math.abs(-45f - this.angle.x));
//			System.out.println(((Math.tan(Math.toRadians(Math.abs((-45f - this.angle.x)))))));
//		}
//		
//		if (this.angle.y >= -45f) {
//			dAy = (float) (this.eye.y + (Math.tan(Math.toRadians(this.angle.y))));
//		} else {
//			float tany = (float)((Math.tan(Math.toRadians(Math.abs((-45f -this.angle.y))))));
//			dAy = (float) (this.eye.y - 1f - lerp((float)tany, (float)tany * 4f, (float)((Math.tan(Math.toRadians(Math.abs((-45f - this.angle.y))))))));
//			System.out.println("tanx: " + tany);
//			System.out.println("lerp: " + lerp((float)tany, (float)tany * 4f, (float)((Math.tan(Math.toRadians(Math.abs((-45f - this.angle.y))))))));
//			System.out.println(Math.abs(-45f - this.angle.y));
//			System.out.println(((Math.tan(Math.toRadians(Math.abs((-45f - this.angle.y)))))));
//		}
//		
//		if (this.angle.z >= -45f) {
//			dAz = (float) (this.eye.z + (Math.tan(Math.toRadians(this.angle.z))));
//		} else {
//			float tanz = (float)((Math.tan(Math.toRadians(Math.abs((-45f -this.angle.z))))));
//			dAz = (float) (this.eye.z - 1f - lerp((float)tanz, (float)tanz * 4f, (float)((Math.tan(Math.toRadians(Math.abs((-45f - this.angle.z))))))));
//			System.out.println("tanx: " + tanz);
//			System.out.println("lerp: " + lerp((float)tanz, (float)tanz * 4f, (float)((Math.tan(Math.toRadians(Math.abs((-45f - this.angle.z))))))));
//			System.out.println(Math.abs(-45f - this.angle.z));
//			System.out.println(((Math.tan(Math.toRadians(Math.abs((-45f - this.angle.z)))))));
//		}
		//dAx = (float) (this.eye.x + (Math.tan(Math.toRadians(angle.x))));
		//dAy = (float) (this.eye.y + (Math.tan(Math.toRadians(angle.y))));
		//dAz = (float) (this.eye.z + (Math.tan(Math.toRadians(angle.z))));

		
//		float dAx = 0f;
//		float dAy = 0f;
//		float dAz = 0f;
		
//		dAx = ((float) Math.cos(Math.toRadians(this.angle.x))) * ((float) Math.cos(Math.toRadians(this.angle.z + 78.69f)));
//		dAy = (float) Math.sin(Math.toRadians(this.angle.z + 78.69f));
//		dAz = ((float) Math.sin(Math.toRadians(this.angle.x))) * ((float) Math.cos(Math.toRadians(this.angle.z + 78.69f)));
		//dAx = (float)Math.cos(Math.toRadians(this.angle.x));
		//this.center = new Vector3f(dAx, dAy, dAz);	
		
		
		
		
		
	//	mvp = new Matrix4f().perspective((float) Math.toRadians(45f), 1.0f, 0.01f, 100.0f)
		//		.lookAt(this.eye, this.center, this.axis).rotateLocal((float)Math.toRadians(this.angle.x), 0f, 1f, 0f);
		
		
		
		
		
		
		
		
		
		
		
		
		
//		float dx = 0f;
//		float dy = 0f;
//		float dz = 0f;
//		
//		if (angle.x <= 45f) {
//			dx = (float)Math.tan(Math.toRadians(angleAxis.y)) * (diff.z);
//		} else {
//			dx = (float)Math.tan(Math.toRadians(45f + angleAxis.y)) * (diff.z);
//		}
//		if (angle.y <= 45f) {
//			
//		} else {
//			
//		}
//		if (angle.z <= 45f) {
//			
//		} else {
//			
//		}
		
//		System.out.println("dAngle.x: " + (float)dAngle.x);
//		System.out.println("dAngleTan.x: " + (float)Math.tan(Math.toRadians(dAngle.x)));
//		System.out.println("diff.x: " + diff.x);
//		System.out.println("45dAngle.x " + (((float)Math.tan(Math.toRadians(Math.abs(45f - dAngle.x))))));
//		System.out.println("-1f - 45dAngle.x " + (-1f - ((float)Math.tan(Math.toRadians(Math.abs(45f - dAngle.x))))));
//		System.out.println("cx " +( this.eye.x + (-1f - ((float)Math.tan(Math.toRadians(Math.abs(45f - dAngle.x)))))));
		//this.center = new Vector3f(this.center.x + (float)Math.tan(Math.toRadians(angleAxis.y)) * (diff.z), (this.center.y + (float)Math.tan(Math.toRadians(angleAxis.z)) * diff.z), this.center.z + (float)Math.tan(Math.toRadians(angleAxis.x)) * diff.z);	
		//this.center = new Vector3f(this.center.x + (float)Math.tan(Math.toRadians(dAngle.x)) * (diff.z), (this.center.y + (float)Math.tan(Math.toRadians(angleAxis.z)) * diff.z), this.center.z + (float)Math.tan(Math.toRadians(angleAxis.x)) * diff.z);	
		//this.center = new Vector3f(this.eye.x + ((float)Math.tan(Math.toRadians(dAngle.x)) * (diff.z)), (this.center.y + (float)Math.tan(Math.toRadians(angleAxis.z)) * diff.z), this.center.z + (float)Math.tan(Math.toRadians(angleAxis.x)) * diff.z);	
//		float dx = this.eye.x + ((float)Math.tan(Math.toRadians(dAngle.x)));
//		//(this.center.y + (float)Math.tan(Math.toRadians(angleAxis.z)) * diff.z)
//		
//		this.center = new Vector3f(dx, this.center.y , this.center.z);	
//		float dz = (this.eye.z + ((float)Math.tan(Math.toRadians(dAngle.x))) * diff.z;
//		this.center = new Vector3f(this.center.x, this.center.y, dz);	
		//System.out.println("angle: " + angle);
		
		//this.center = new Vector3f((float) (dAx), (this.center.y + (float)Math.tan(Math.toRadians(angleAxis.z))), this.center.z + (float)Math.tan(Math.toRadians(angleAxis.x)));	
		//this.center = new Vector3f((float) (this.center.x + (Math.tan(Math.toRadians(angleAxis.y)))), (this.center.y + (float)Math.tan(Math.toRadians(angleAxis.z)) * diff.z), this.center.z + (float)Math.tan(Math.toRadians(angleAxis.x)) * diff.z);	
		
//		float cx = 0f;
//		float cy = this.center.y;
//		float cz = (this.eye.z + ((float)Math.tan(Math.toRadians(dAngle.z))));
//		
//		if (dAngle.x > -45f) {
//		cx = this.eye.x + ((float)Math.tan(Math.toRadians(dAngle.x)));
//		} else {
//		cx = this.eye.x + (-1f - ((float)Math.tan(Math.toRadians(Math.abs(45f + dAngle.x)))));
//		if (cx < -1f) {
//			cx = -1f;
//		}
//		}
		
//		if (Math.abs(cx) > Math.abs(diff.x) + Math.abs(diff.z)) {
//			cx = diff.x;
//		}
//	
		
		//this.center = new Vector3f(cx, this.center.y, this.center.z);
		
		refineMatrix();
	}
	float lerp(float a, float b, float f)
	{
	    return a + f * (b - a);
	}
//	public void toAngle(Vector3f angle) {
//		
//		Vector3f diff = new Vector3f(this.center.x - this.eye.x, this.center.y - this.eye.y, this.center.z - this.eye.z);
//		System.out.println(diff.toString());
//	}
	public void refineMatrix() {
		mvp = new Matrix4f().perspective((float) Math.toRadians(45f), 108f/72f, 0.01f, 100.0f).rotateXYZ((float)Math.toRadians(this.angle.z + 78.69f), (float)Math.toRadians(this.angle.x), (float)Math.toRadians(this.angle.y))
				.lookAt(this.eye, this.center, this.axis);
//		world = new Matrix4f().translate(this.diff).
//                rotateX((float)Math.toRadians(angle.z + 78.69f)).
//                rotateY((float)Math.toRadians(angle.x)).
//                rotateZ((float)Math.toRadians(angle.y )).
//                scale(1f);
		System.out.println("eye: " + this.eye.toString() + " center: " + this.center.toString() + " axis: " + this.axis.toString());
		
	}
	

}

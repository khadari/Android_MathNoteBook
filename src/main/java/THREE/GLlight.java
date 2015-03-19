package THREE;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
public class GLlight {
	private static int numOfLights=0;
	private int lightId;
	private float lightPosition[]={0,0,5,1};
	private FloatBuffer ambientBuffer=null;
	private FloatBuffer diffuseBuffer=null;
	private FloatBuffer specularBuffer=null;
	private FloatBuffer positionBuffer=null;
	private FloatBuffer directionBuffer=null;
	private float kc=1;
	private float kl=0;
	private float kq=0;
	private float[] spotDirection={0,0,1};
	private float spotcutoff=360;
	private float spotexp=0;
    private int Tow_sides_enabled=GL10.GL_FALSE;
	public GLlight(){
		lightId=numOfLights++;
		setAmbient(0, 0, 0,1);
		setDiffuse(1,1, 1, 1);
		setSpecular(1,1, 1, 1);
		setPosition(0, 0,5,1);
	}
	public void enable(GL10 gl){
		gl.glEnable(GL10.GL_LIGHT0+lightId);
		//gl.glLightModelx(GL10.GL_LIGHT_MODEL_TWO_SIDE, GL10.GL_TRUE);
		
		gl.glLightfv(GL10.GL_LIGHT0+lightId, GL10.GL_AMBIENT,ambientBuffer); 
		gl.glLightfv(GL10.GL_LIGHT0+lightId, GL10.GL_DIFFUSE,diffuseBuffer); 
		gl.glLightfv(GL10.GL_LIGHT0+lightId, GL10.GL_SPECULAR,specularBuffer);
		gl.glLightfv(GL10.GL_LIGHT0+lightId, GL10.GL_POSITION,positionBuffer);
		gl.glLightf(GL10.GL_LIGHT0+lightId, GL10.GL_CONSTANT_ATTENUATION, kc);
		gl.glLightf(GL10.GL_LIGHT0+lightId, GL10.GL_LINEAR_ATTENUATION, kl);
		gl.glLightf(GL10.GL_LIGHT0+lightId, GL10.GL_QUADRATIC_ATTENUATION, kq);
        gl.glLightModelf(GL10.GL_LIGHT_MODEL_TWO_SIDE,Tow_sides_enabled);
		if(directionBuffer!=null){
			gl.glLightfv(GL10.GL_LIGHT0+lightId, GL10.GL_SPOT_DIRECTION, directionBuffer);
			gl.glLightf(GL10.GL_LIGHT0+lightId, GL10.GL_SPOT_CUTOFF, spotcutoff);
			gl.glLightf(GL10.GL_LIGHT0+lightId, GL10.GL_SPOT_EXPONENT, spotexp);
		}
	}
	public void disable(GL10 gl){
		gl.glDisable(GL10.GL_LIGHT0+lightId);
	}
	public void disableSpot(){
		directionBuffer=null;
	}
	public void setPosition(float x,float y,float z,float w){
		lightPosition=new float[] {x,y,z,w};
		ByteBuffer bb=ByteBuffer.allocateDirect(lightPosition.length*4*6);
		bb.order(ByteOrder.nativeOrder());
		positionBuffer=bb.asFloatBuffer();
		positionBuffer.put(lightPosition);
		positionBuffer.position(0);
	}
	public void setAmbient(float x,float y,float z,float w){
		float[] lightAmbient=new float[] {x,y,z,w};
		ByteBuffer bb=ByteBuffer.allocateDirect(lightAmbient.length*4*6);
		bb.order(ByteOrder.nativeOrder());
		ambientBuffer=bb.asFloatBuffer();
		ambientBuffer.put(lightAmbient);
		ambientBuffer.position(0);
	}
	public void setDiffuse(float x,float y,float z,float w){
		float[] lightDiffuse=new float[] {x,y,z,w};
		ByteBuffer bb=ByteBuffer.allocateDirect(lightDiffuse.length*4*6);
		bb.order(ByteOrder.nativeOrder());
		diffuseBuffer=bb.asFloatBuffer();
		diffuseBuffer.put(lightDiffuse);
		diffuseBuffer.position(0);
	}
	public void setSpecular(float x,float y,float z,float w){
		float[] lightSpecular=new float[] {x,y,z,w};
		ByteBuffer bb=ByteBuffer.allocateDirect(lightSpecular.length*4*6);
		bb.order(ByteOrder.nativeOrder());
		specularBuffer=bb.asFloatBuffer();
		specularBuffer.put(lightSpecular);
		specularBuffer.position(0);
	}
	public void setSpotDirection(float x,float y,float z){
		spotDirection=new float[]{x,y,z};
		ByteBuffer bb=ByteBuffer.allocateDirect(spotDirection.length*3*6);
		bb.order(ByteOrder.nativeOrder());
		directionBuffer=bb.asFloatBuffer();
		directionBuffer.put(spotDirection);
		directionBuffer.position(0);
	}
	public float[] getPosition(){
		return lightPosition;
	}
	public static void init(){
		numOfLights=0;
	}
	public void setAttenuation(float kc,float kl,float kq){
		this.kc=kc;
		this.kl=kl;
		this.kq=kq;
	}
    public void setTwoSidesEnabled(Boolean a){
        this.Tow_sides_enabled=a?GL10.GL_TRUE:GL10.GL_FALSE;
    }
}

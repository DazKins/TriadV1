package src;

public class Frame {
	int length;
	Sprite spr;
	int sumTime = 0;
	
	public Frame(Sprite s1,int i1){
		this.length=i1;
		this.spr=s1;
	}
	
	public boolean tickFrame(){
		if(sumTime<length){
			sumTime++;
			return false;
		}else{
			sumTime=0;
			return true;
		}
	}
}

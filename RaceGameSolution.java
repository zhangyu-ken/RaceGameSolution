package test;

import java.util.Timer;
import java.util.TimerTask;

/*
 * 使用的是模拟整个行走过程的方法来解决 
 * 
 * @author        张宇
 * @date          2020年1月15日
 * @version       1.8
 */
public class RaceGameSolution {
	public static void main(String[] args) {
		//创建timer
		Timer timer = new Timer();
		//创建A、B
		People A = new People("A", 40, true); 
		People B = new People("B", 60, true);
		//创建timerTask
		Walk walkA = new Walk(A);
		Walk walkB = new Walk(B);
		//定时循环执行
		timer.schedule(walkA, 0, 1);
		timer.schedule(walkB, 0, 1);
		while (true) {
			//通过判断差值来结束timer以及timerTaks任务
			System.out.println(B.getTotalLength()-A.getTotalLength());
			if (B.getTotalLength()-A.getTotalLength()>=800) {
				walkA.cancel();
				walkB.cancel();
				timer.cancel();
				System.out.println("在走了 "+(B.getTotalTime()/60.0)+"分钟后超过800米");
				break;
			}
		}
	}
}
/*
 * Walk类继承TimerTask
 * 有参构造(People people)
 * 生成get/set
 * 重写的run方法内是每个人行走的规则
 * 
 * @parameter	people
 */
class Walk extends TimerTask {
	private People people;
	
	public Walk(People people) {
		setPeople(people);
	}

	public People getPeople() {
		return people;
	}

	public void setPeople(People people) {
		this.people = people;
	}

	@Override
	public void run() {
		//时间单位+1
		people.setTotalTime(people.getTotalTime()+1);
		//判断目前是行走状态，则累计总长以及每走200米停的距离
		if (people.isFlag()) {
			people.setTotalLength(
					people.getTotalLength()
					+(people.getSpeed()/60));
			//判断目前还没到200米无需暂停休息
			if (people.getTmpLength()+(people.getSpeed()/60)<200) {
				people.setTmpLength(
						people.getTmpLength()
						+(people.getSpeed()/60));
			//判断目前已到200米需要暂停休息
			} else if (people.getTmpLength()+(people.getSpeed()/60)>=200) {
				//更改行走状态：行走-->暂停
				people.setFlag(false);
				people.setTmpLength(
						people.getTmpLength()
						+(people.getSpeed()/60)-200);
			}
		//判断目前不是行走状态，则进入暂停的步骤
		} else if (!people.isFlag()) {
			people.setTotalPauseTime(people.getTotalPauseTime()+1);
			//判断当前暂停时间不足2分钟则继续暂停
			if (people.getTmpPauseTime()<120) {
				people.setTmpPauseTime(people.getTmpPauseTime()+1);
			//判断当前暂停时间满足2分钟则结束暂停
			} else if (people.getTmpPauseTime()>=120) {
				//更改行走状态：暂停-->行走
				people.setFlag(true);
				people.setTmpPauseTime(people.getTmpPauseTime()+1-120);
			}
		}
		//保证及时回收，否则timer线程会依旧运行
		System.gc();
	}
}
/**
 * Poeple类用于创建某个行走的人
 * 
 * @parameter     name			名字
 * @parameter     speed			速度
 * @parameter     totalLength	总共行走距离
 * @parameter     tmpLength		200米的暂停距离
 * @parameter     flag			行走状态
 * @parameter     totalPauseTime总共暂停时长
 * @parameter     tmpPauseTime	2分钟的暂时时长
 * @parameter     totalTime		总共消耗的时间单位
 */
class People {
	private String name;
	private double speed;
	private double totalLength;
	private double tmpLength;
	private boolean flag;
	private double totalPauseTime;
	private double tmpPauseTime;
	private int totalTime;
	
	public People() {
	}
	
	public People(String name, double speed, boolean flag) {
		setName(name);
		setSpeed(speed);
		setFlag(flag);
	}
	
	public int getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getTotalLength() {
		return totalLength;
	}

	public void setTotalLength(double totalLength) {
		this.totalLength = totalLength;
	}

	public double getTmpLength() {
		return tmpLength;
	}

	public void setTmpLength(double tmpLength) {
		this.tmpLength = tmpLength;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public double getTotalPauseTime() {
		return totalPauseTime;
	}

	public void setTotalPauseTime(double totalPauseTime) {
		this.totalPauseTime = totalPauseTime;
	}

	public double getTmpPauseTime() {
		return tmpPauseTime;
	}

	public void setTmpPauseTime(double tmpPauseTime) {
		this.tmpPauseTime = tmpPauseTime;
	}
	
}
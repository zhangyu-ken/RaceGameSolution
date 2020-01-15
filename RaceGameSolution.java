package test;

import java.util.Timer;
import java.util.TimerTask;

/*
 * ʹ�õ���ģ���������߹��̵ķ�������� 
 * 
 * @author        ����
 * @date          2020��1��15��
 * @version       1.8
 */
public class RaceGameSolution {
	public static void main(String[] args) {
		//����timer
		Timer timer = new Timer();
		//����A��B
		People A = new People("A", 40, true); 
		People B = new People("B", 60, true);
		//����timerTask
		Walk walkA = new Walk(A);
		Walk walkB = new Walk(B);
		//��ʱѭ��ִ��
		timer.schedule(walkA, 0, 1);
		timer.schedule(walkB, 0, 1);
		while (true) {
			//ͨ���жϲ�ֵ������timer�Լ�timerTaks����
			System.out.println(B.getTotalLength()-A.getTotalLength());
			if (B.getTotalLength()-A.getTotalLength()>=800) {
				walkA.cancel();
				walkB.cancel();
				timer.cancel();
				System.out.println("������ "+(B.getTotalTime()/60.0)+"���Ӻ󳬹�800��");
				break;
			}
		}
	}
}
/*
 * Walk��̳�TimerTask
 * �вι���(People people)
 * ����get/set
 * ��д��run��������ÿ�������ߵĹ���
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
		//ʱ�䵥λ+1
		people.setTotalTime(people.getTotalTime()+1);
		//�ж�Ŀǰ������״̬�����ۼ��ܳ��Լ�ÿ��200��ͣ�ľ���
		if (people.isFlag()) {
			people.setTotalLength(
					people.getTotalLength()
					+(people.getSpeed()/60));
			//�ж�Ŀǰ��û��200��������ͣ��Ϣ
			if (people.getTmpLength()+(people.getSpeed()/60)<200) {
				people.setTmpLength(
						people.getTmpLength()
						+(people.getSpeed()/60));
			//�ж�Ŀǰ�ѵ�200����Ҫ��ͣ��Ϣ
			} else if (people.getTmpLength()+(people.getSpeed()/60)>=200) {
				//��������״̬������-->��ͣ
				people.setFlag(false);
				people.setTmpLength(
						people.getTmpLength()
						+(people.getSpeed()/60)-200);
			}
		//�ж�Ŀǰ��������״̬���������ͣ�Ĳ���
		} else if (!people.isFlag()) {
			people.setTotalPauseTime(people.getTotalPauseTime()+1);
			//�жϵ�ǰ��ͣʱ�䲻��2�����������ͣ
			if (people.getTmpPauseTime()<120) {
				people.setTmpPauseTime(people.getTmpPauseTime()+1);
			//�жϵ�ǰ��ͣʱ������2�����������ͣ
			} else if (people.getTmpPauseTime()>=120) {
				//��������״̬����ͣ-->����
				people.setFlag(true);
				people.setTmpPauseTime(people.getTmpPauseTime()+1-120);
			}
		}
		//��֤��ʱ���գ�����timer�̻߳���������
		System.gc();
	}
}
/**
 * Poeple�����ڴ���ĳ�����ߵ���
 * 
 * @parameter     name			����
 * @parameter     speed			�ٶ�
 * @parameter     totalLength	�ܹ����߾���
 * @parameter     tmpLength		200�׵���ͣ����
 * @parameter     flag			����״̬
 * @parameter     totalPauseTime�ܹ���ͣʱ��
 * @parameter     tmpPauseTime	2���ӵ���ʱʱ��
 * @parameter     totalTime		�ܹ����ĵ�ʱ�䵥λ
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
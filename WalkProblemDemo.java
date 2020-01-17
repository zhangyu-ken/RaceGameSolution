package test;

public class WalkProblemDemo {
	public static void main(String[] args) {
		double a = 0, b = 0, t = 0;
		for (t = 0; b <= a+800; t++) {
			//A在时间(200.0/(40.0/60))秒行走,然后停120秒
			if(t%(200.0/(40.0/60)+120)<=(200.0/(40.0/60)))
				a += (40/60.0);
			//B在时间(200.0/(60.0/60))秒行走,然后停120秒
			if(t%(200.0/(60.0/60)+120)<=(200.0/(60.0/60)))
				b += (60/60.0);
		}
		System.out.println(t/60.0);
	}
}

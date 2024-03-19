package concurrency;

class Thread1{
	public void disp(int j) {
		for(int i=0; i<=j; i++) {
			System.out.println(i);
		}
	}
}

public class MyThreads {
	public static void main(String[] args) {
		
		Thread1 read=new Thread1();
		read.disp(9);
	}
	
	
}

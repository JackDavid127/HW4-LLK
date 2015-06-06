import java.util.Timer;
import java.util.TimerTask;
public class MyCounter{
	public void Display(int x) {System.out.println(x);}
	public MyCounter(){
		Timer timer=new Timer();
		timer.schedule(new TimerTask(){
			int x=4;
			public void run(){
				Display(x);
				x--;
				if(x==0) timer.cancel();
			}
		},1000,1000);
	}
	public static void main(String []args){
		MyCounter x=new MyCounter();
		//while (true) System.out.println("a");
	}
}
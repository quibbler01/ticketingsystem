package ticketingsystem;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;



public class Test {
	private int routenum=5;      //车次总数
	private int coachnum=8;      //列车的车厢数目
	private int seatnum=100;       //每节车厢的座位数
	private int stationnum=10;    //每个车次经停站的数量
	public static int ExecTimesAThread=10000;
	public static Object object=new Object();
	TicketingDS tds = new TicketingDS(routenum,coachnum,seatnum,stationnum);
	class Task implements Runnable{
		public Task() {
			// TODO Auto-generated constructor stub
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Random random =new Random();
			int count=ExecTimesAThread/10;  //栈封闭
			while(count>0){
				for(int i=0;i<6;i++){
					int route=random.nextInt(routenum)+1;
					int departure=random.nextInt(stationnum-1)+1;
					int arrival=departure+random.nextInt(stationnum-departure)+1;
					tds.inquiry(route, departure, arrival);
				}
				
				for(int i=0;i<3;i++){
					int route=random.nextInt(routenum)+1;
					int departure=random.nextInt(stationnum-1)+1;
					int arrival=departure+random.nextInt(stationnum-departure)+1;
					tds.buyTicket("passenger",route,departure,arrival);
				}				
				tds.refundTicket(tds.getSellTicket().getRandomElement(random));
				--count;
			}
		}
	}
	
//	public static void main(String args[]){
//		Test test=new Test();
//		ExecutorService exec = Executors.newFixedThreadPool(16);
//		int threadNum=16;
//		long startTime= System.currentTimeMillis();//开始时间
//	//	countDownLatch = new CountDownLatch(threadNum);
//		Future<?> []future=(Future<?>[])new Future[threadNum];
//		try {
//			for(int i=0;i<threadNum;i++)
//				future[i]=exec.submit(test.new Task());
//			for(int i=0;i<threadNum;i++)
//				future[i].get();
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		exec.shutdown(); 
//		long endTime=System.currentTimeMillis();
//		
//        System.out.println("执行总时间:"+(endTime-startTime));
//        System.out.println("吞吐率:"+threadNum*ExecTimesAThread*1000/(endTime-startTime)); 
//		
//	}
	
	public static void main(String args[]){
		Test test=new Test();
		int threadNum=16;
		long startTime= System.currentTimeMillis();//开始时间
		Thread []thread=new Thread[threadNum];
		try {
			for(int i=0;i<threadNum;i++){
				thread[i]=new Thread(test.new Task());
				thread[i].start();
			}
			for(int i=0;i<threadNum;i++)
				thread[i].join();
		} catch (Exception e) {
			// TODO: handle exception
		}
		long endTime=System.currentTimeMillis();//结束时间
		System.out.println("执行总时间:"+(endTime-startTime));
		System.out.println("吞吐率:"+threadNum*ExecTimesAThread*1000/(endTime-startTime)); 
	}
}

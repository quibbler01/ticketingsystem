package ticketingsystem;

class Ticket{
	long tid;
	String passenger;
	int route;           //列车车次
	int coach;           //车厢号
	int seat;            //座位号
	int departure;       //出发站编号
	int arrival;         //到达站编号
}

public interface TicketingSystem{
	Ticket buyTicket(String passenger,int route,int departure,int arrival);
	int inquiry(int route,int departure,int arrival); 
	boolean refundTicket(Ticket ticket);
}

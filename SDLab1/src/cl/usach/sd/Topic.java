package cl.usach.sd;

import java.awt.List;
import java.util.ArrayList;

public interface Topic{
	
	void add_subscriber(int s);
	ArrayList<Integer> getSubscribers();
	ArrayList<Message> getMessages();
	void remove_subscriber(int id);

}

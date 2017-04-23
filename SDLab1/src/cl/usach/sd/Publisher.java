package cl.usach.sd;

import java.util.ArrayList;

public interface Publisher {

	void register_publisher(int topic);
	void show_topics();
	Message publish();
	default void delete_publication(){
		
	}
	void deregister_publisher(int topic);
	ArrayList<Integer> getSubscribers();
}

package cl.usach.sd;

import java.awt.List;
import java.util.ArrayList;
/**
 * Interface que garantiza que el nodo se puede comportar como un tópico, es decir que tiene asociado 
 * subscriptores, además los mensajes que va publicando en la red, etc.
 * @author Natalia
 *
 */
public interface Topic{
	
	void add_subscriber(int s);
	void remove_subscriber(int id);
	void add_message(Message msg);
	void remove_message(Message msg);
	ArrayList<Integer> getSubscribers();
	ArrayList<Message> getMessages();
	void show_subscribers();
	

}

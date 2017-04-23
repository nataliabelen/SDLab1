package cl.usach.sd;

import java.util.ArrayList;
import java.util.Random;

import peersim.core.GeneralNode;
import peersim.core.Linkable;
import peersim.core.Network;

public class ExampleNode extends GeneralNode implements Publisher, Topic, Subscriber {
	/*Creamos un nodo que posee un valor y un tipo*/
	private boolean publisher;
	private boolean subscriber;
	private boolean topic;
	private ArrayList<Integer> topics;
	private ArrayList<Integer> subscribers;
	private ArrayList<Message> messages;

	public ExampleNode(String prefix) {
		super(prefix);
		this.setPublisher(false);
		this.setSubscriber(false);
		this.setTopic(false);
		topics = null;
		subscribers = null;
		messages=null;
	}
//actuando como publisher:
	
	public ArrayList<Integer> getSubscribers() {
		return subscribers;
	}
	public boolean isPublisher() {
		return publisher;
	}
	
	public void setPublisher(boolean publisher) {
		this.publisher = publisher;
		if (publisher){
			topics = new ArrayList<Integer>();
		}
		else topics = null;
	}
	
	public void register_publisher(int topic){
		if (topics.contains(topic)) System.out.println("El publisher "+this.getID()+" ya está registrado en el tópico: "+topic);
		else {
			topics.add(topic);
			System.out.println("Registrando al publisher "+this.getID()+" en el topico "+topic);
		}
	}
	/**
	 * Función que se encarga de crear un mensaje, que se va a publicar a un topic que esté registrado y si no está registrado, lo creará.
	 * @param message : número de mensaje que se envia
	 * @param topic número de topico al cual envia la publicación
	 */
	public Message publish(){
		int topic;
		Random random = new Random();
		if (!this.isPublisher()){
			this.setPublisher(true);
			topic =random.nextInt(Network.size());
			this.register_publisher(topic);
			if(!((ExampleNode) Network.get(topic)).isTopic()){
				((ExampleNode) Network.get(topic)).setTopic(true);
			}
		}
		else {
			topic= random.nextInt(topics.size());
			topic = topics.get(topic);
		}
		int msg = random.nextInt(1000);
		Message message = new Message(msg, topic, 100);
		message.setCreator((int)this.getID());
		System.out.println("Soy publisher "+message.getCreator()+ " y envío el mensaje "+message.getValue()+" al topico "+topic);
		return message;
	}

	
	public boolean isSubscriber() {
		return subscriber;
	}

	public void setSubscriber(boolean subscriber) {
		this.subscriber = subscriber;

	}


	public boolean isTopic() {
		return topic;
	}

	public void setTopic(boolean topic) {
		this.topic = topic;
		if(topic) {
			subscribers = new ArrayList<Integer>();
			messages =new ArrayList<Message>();
		}
		else {
			subscribers = null;
			messages=null;
		}
	}

	public void show_topics(){
		System.out.println("Soy :"+this.getID()+" y estoy asociado a los tópicos: ");
		for (int number : topics) {
			System.out.print(number+" ");
		}
		System.out.println(".");

}

	public void show_subscribers(){
		System.out.println("Soy :"+this.getID()+" y estoy asociado a los subscriptores: ");
		for (int number : subscribers) {
			System.out.print(number+" ");
		}
		System.out.println(".");

}

	public String show_all(){
		String text="Node: "+this.getID()+ "  \tGrade: " + ((Linkable) this.getProtocol(0)).degree()+"\n";
		text=text+"\t[Publisher: "+this.isPublisher()+",\tTopic: "+this.isTopic()+",\tSubscriber: "+this.isSubscriber()+"]\n\tneighbors: ";
		for (int i = 0; i < ((Linkable) this.getProtocol(0)).degree(); i++) {
			text=text+ ((Linkable) this.getProtocol(0)).getNeighbor(i).getID()+" ";
		}
		text=text+"\n";
		if(isPublisher()){
			text=text+"\ttopics: ";
			for (int number : topics) {
				text=text+number+" ";
			}
			text=text+"\n";
		}
		if(isTopic()){
			text=text+"\tsubscribers: ";
			for (int number : subscribers) {
				text=text+number+" ";
			}
			text=text+"\n\tmessages: ";
			for (Message number : messages) {
				text=text+"("+number.getCreator()+" - "+number.getValue()+") ";
			}
			text=text+"\n";
		}
		return text;
	}

	public void register_subscriber(Topic topic) {
		if (topic.getSubscribers().contains((int)this.getID())) System.out.println("--El tópico "+ ((ExampleNode)topic).getID()+" ya contiene al subscriptor: "+this.getID());
		else {
			topic.add_subscriber((int)this.getID());
			System.out.println("Registrando al tópico "+((ExampleNode) topic).getID()+" el subscriptor "+this.getID());
		}
	}
	public void deregister_subscriber(Topic topic) {

			topic.remove_subscriber((int)this.getID());
			System.out.println("Deregistrando al tópico "+((ExampleNode) topic).getID()+" el subscriptor "+this.getID());
	}
	public void add_subscriber(int sub){
		this.subscribers.add(sub);
	}
	public void remove_subscriber(int sub){
		this.subscribers.remove(sub);
	}
	public ArrayList<Message> getMessages(){
		return messages;
	}
	public void add_message(Message msg){
		messages.add(msg);
	}

	public void request_update(int message){
		System.out.println("El subscriptor "+this.getID()+" recibe el mensaje "+message);
	}

	@Override
	public void deregister_publisher(int topic) {
		// TODO Auto-generated method stub
		
	}
}
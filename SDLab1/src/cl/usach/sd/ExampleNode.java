package cl.usach.sd;

import java.util.ArrayList;
import java.util.Random;

import peersim.core.GeneralNode;
import peersim.core.Linkable;
import peersim.core.Network;
/**
 * ExampleNode se utiliza como el nodo base en esta red simulada de peer to peer.
 * @author Natalia
 *
 */
public class ExampleNode extends GeneralNode implements Publisher, Topic, Subscriber {
	private boolean publisher;
	private boolean subscriber;
	private boolean topic;
	private ArrayList<Integer> topics;
	private ArrayList<Integer> subscribers;
	private ArrayList<Message> messages;
	private ArrayList<Message> sendMessages;
/**
 * Constructor del Nodo que actuará como publisher, topic y/o subscriber.
 * Inicializa sus valores predeterminados con las listas en null y en falso a cual característica pertenece.
 * @param prefix Necesario para General Node
 */
	public ExampleNode(String prefix) {
		super(prefix);
		this.setPublisher(false);
		this.setSubscriber(false);
		this.setTopic(false);
		topics = null;
		subscribers = null;
		messages=null;
		sendMessages=null;
	}
	/**
	 * Función que retorna si es publicador o no.
	 * @return si es verdadero, es publicador.
	 */
	public boolean isPublisher() {
		return publisher;
	}
	/**
	 * Función que modifica el estado del publicador, si cambia a verdadero, entonces crea en el publicador la lista de tópicos asociados a él.
	 * @param publisher Parámetro de entrada para modificar si es publisher o no.
	 */
	public void setPublisher(boolean publisher) {
		this.publisher = publisher;
		if (publisher){
			topics = new ArrayList<Integer>();
		}
		else topics = null;
	}
	/**
	 * Función que verifica si el nodo es un subscriptor
	 * @return Parámetro de salida con el valor de subscriber
	 */
	public boolean isSubscriber() {
		return subscriber;
	}
	/**
	 * Función que modifica la acción del nodo con respecto al subscriptor.
	 * @param subscriber booleano para cambiar la apariencia de subscriber
	 */
	public void setSubscriber(boolean subscriber) {
		this.subscriber = subscriber;
	}
	/**
	 * Función que verifica si el nodo es tópico o no
	 * @return Valor booleano que indica si es tópico o no
	 */
	public boolean isTopic() {
		return topic;
	}
	/**
	 * Función que modifica al nodo con respecto a si es un tópico o no, si lo es crea la lista de mensajes de tópico y la lista de subscriptores al tópico
	 * @param topic valor booleano que indica si es tópico o no.
	 */
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
	
	/**
	 * Función asociada al nodo
	 * Captura la información asociada al nodo, como el grado del nodo si es publisher los tópicos que tiene subscrito, si es tópcio: cuales son los mensajes que tiene y subscriptores, etc.
	 * @return Retorna un String que contiene toda la información del nodo.
	 */
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
	/**
	 * Función asociada al publicador
	 * Registra un tópico en el publisher, y se cerciora que no esté anteriormente.
	 */
	public void register_publisher(int topic){
		if (topics.contains(topic)) System.out.println("El publisher "+this.getID()+" ya está registrado en el tópico: "+topic);
		else {
			topics.add(topic);
			System.out.println("Registrando al publisher "+this.getID()+" en el topico "+topic);
		}
	}
	/**
	 * Función asociada al publicador
	 * Función que se encarga de crear un mensaje, que se va a publicar a un topico que esté registrado y si no está registrado, lo creará.
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
		}else if(topics==null|| topics.size()==0){
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
		if (sendMessages==null) sendMessages=new ArrayList<Message>();
		sendMessages.add(message);
		System.out.println("Soy publisher "+message.getCreator()+ " y envío el mensaje "+message.getValue()+" al topico "+topic);
		return message;
	}

	/**
	 * Función asociada al publicador,
	 * Se encarga de deregistrar el publicador a un tópico.
	 */
	public void deregister_publisher(int topic) {
		System.out.println("El publisher "+this.getID()+" está removiendo al tpico "+topic);
		if (topics!=null&&topics.contains(topic)){
		topics.remove((Object)topic);
		System.out.println("Desregistrando al publisher "+ this.getID()+" del tópico "+topic);
		} else {
			System.out.println("El publisher "+ this.getID()+" no contiene al tópico "+ topic);
		}
	}
/**
 * Función asociada al publicador
 * Consiste en mandar a eliminar una publicación random que este haya hecho antes.
 */
	public Message delete_publication(){
		if(sendMessages==null || sendMessages.size()==0) {
			System.out.println("El publisher "+this.getID()+" no puede eliminar publicación porque aún no envía ninguna");
			return null;
		}else {
			Random random = new Random();
			int r = random.nextInt(sendMessages.size());
			Message message = sendMessages.get(r);
			message.setType(4);
			return message;
		}
	}
	/**
	 * Función asociada al publicador
	 * Más que nada es una función de ayuda para ver todos los tópicos que tiene el publicador
	 */
	public void show_topics(){
		System.out.println("Soy :"+this.getID()+" y estoy asociado a los tópicos: ");
		for (int number : topics) {
			System.out.print(number+" ");
		}
		System.out.println(".");

}
	/**
	 * Función asociada al tópico,
	 * Esta función es la encargada de agregar un nuevo subscrito a la lista de subscritos para un tópico
	 */
	public void add_subscriber(int sub){
		if (isTopic()){
		if (subscribers.contains(sub)) System.out.println("--El tópico "+this.getID()+" ya contiene al subscriptor: "+sub);
		else {
			this.subscribers.add(sub);
			System.out.println("Registrando del tópico "+ this.getID()+" al subscriptor "+sub);
		}}
		else System.out.println("El nodo "+this.getID()+" no es tópico");//porque viene del subscriber entonces no se crea como tópico
	}
	/**
	 * Función asociada al tópico,
	 * Esta función es la encargada de eliminar un subscrito a la lista de subscritos para un tópico
	 * @param sub el entero que corresponde al id del subscriptor
	 */
	public void remove_subscriber(int sub){
		if(subscribers!=null &&subscribers.contains(sub)){
			System.out.println("Desregistrando al subscriber "+sub+" del tópico "+ this.getID());
			this.subscribers.remove((Object)sub);
		} else 
			System.out.println("El subscriber "+sub+" no está subscrito al tópico"+ this.getID());
	}
	/**
	 * Función asociada al tópico
	 * Se encarga de agregar un mensaje que se publicó al tópico
	 * @param msg mensaje de la red.
	 */
	public void add_message(Message msg){
		if(!isTopic()) this.setTopic(true); //Porque viene del publisher
		messages.add(msg);
	}
	/**
	 * Función asociada al tópico
	 * Se encarga de agregar un mensaje que se publicó al tópico
	 * @param msg mensaje de la red.
	 */
	public void remove_message(Message msg){
		System.out.println("El tópico "+this.getID()+" está eliminando el mensaje "+msg.getValue());
		if(messages!= null && messages.size()!=0){
			for(int i =0; i<messages.size();i++){
				if(messages.get(i).getValue()==msg.getValue() && messages.get(i).getCreator()==msg.getCreator()){
					messages.remove(i);
					return;
				}
			}
		}
		else System.out.println("El tópico no tiene el mensaje "+ msg.getValue());
		
	}
	/**
	 * Función asociada al tópico, usando interface.
	 * Entrega la lista de los subscriptores al tópico
	 */
	public ArrayList<Integer> getSubscribers() {
		return subscribers;
	}

	/**
	 * Función asociada al tópico, 
	 * Se encarga de retornar la lista de mensajes que tiene el tópico publicados en la red.
	 */
	public ArrayList<Message> getMessages(){
		return messages;
	}
	

	/**
	 * Función asociada al tópico
	 * Básicamente consiste en mostrar por pantalla todos los subscritos al tópico
	 */
	public void show_subscribers(){
		System.out.println("Soy :"+this.getID()+" y estoy asociado a los subscriptores: ");
		for (int number : subscribers) {
			System.out.print(number+" ");
		}
		System.out.println(".");
	}

	/**
	 * Función asociada al subscriber, cuando se registra en un tópico.
	 * @param topic Ingresa un tópico al cual quiere registrar.
	 */
	public Message register_subscriber(int topic) {
		this.setSubscriber(true);
		Message message = new Message(-1, topic, 100);
		message.setCreator((int)this.getID());
		message.setType(2);
		System.out.println("Soy subscriber "+message.getCreator()+ " y me estoy subscribiendo al tópico "+topic);
		return message;
	}
	
	/**
	 * Función asociada al subscriber,
	 * El cual se desregistra del tópico. el cúal se ingresa por parámetro de entrada.
	 */
	public Message deregister_subscriber(int topic) {
		Message message = new Message(-2, topic, 100);
		message.setCreator((int)this.getID());
		message.setType(3);
		System.out.println("Soy subscriber "+message.getCreator()+ " y me estoy desinscribiendo al tópico "+topic);
		return message;
	}
	
	/**
	 * Función asociada al Subscriber
	 * Que se encarga de recibir un mensaje.
	 */
	public void request_update(int message){
		System.out.println("El subscriptor "+this.getID()+" recibe el mensaje "+message);
	}


}
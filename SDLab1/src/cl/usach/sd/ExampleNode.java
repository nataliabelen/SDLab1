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
 * Constructor del Nodo que actuar� como publisher, topic y/o subscriber.
 * Inicializa sus valores predeterminados con las listas en null y en falso a cual caracter�stica pertenece.
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
	 * Funci�n que retorna si es publicador o no.
	 * @return si es verdadero, es publicador.
	 */
	public boolean isPublisher() {
		return publisher;
	}
	/**
	 * Funci�n que modifica el estado del publicador, si cambia a verdadero, entonces crea en el publicador la lista de t�picos asociados a �l.
	 * @param publisher Par�metro de entrada para modificar si es publisher o no.
	 */
	public void setPublisher(boolean publisher) {
		this.publisher = publisher;
		if (publisher){
			topics = new ArrayList<Integer>();
		}
		else topics = null;
	}
	/**
	 * Funci�n que verifica si el nodo es un subscriptor
	 * @return Par�metro de salida con el valor de subscriber
	 */
	public boolean isSubscriber() {
		return subscriber;
	}
	/**
	 * Funci�n que modifica la acci�n del nodo con respecto al subscriptor.
	 * @param subscriber booleano para cambiar la apariencia de subscriber
	 */
	public void setSubscriber(boolean subscriber) {
		this.subscriber = subscriber;
	}
	/**
	 * Funci�n que verifica si el nodo es t�pico o no
	 * @return Valor booleano que indica si es t�pico o no
	 */
	public boolean isTopic() {
		return topic;
	}
	/**
	 * Funci�n que modifica al nodo con respecto a si es un t�pico o no, si lo es crea la lista de mensajes de t�pico y la lista de subscriptores al t�pico
	 * @param topic valor booleano que indica si es t�pico o no.
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
	 * Funci�n asociada al nodo
	 * Captura la informaci�n asociada al nodo, como el grado del nodo si es publisher los t�picos que tiene subscrito, si es t�pcio: cuales son los mensajes que tiene y subscriptores, etc.
	 * @return Retorna un String que contiene toda la informaci�n del nodo.
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
	 * Funci�n asociada al publicador
	 * Registra un t�pico en el publisher, y se cerciora que no est� anteriormente.
	 */
	public void register_publisher(int topic){
		if (topics.contains(topic)) System.out.println("El publisher "+this.getID()+" ya est� registrado en el t�pico: "+topic);
		else {
			topics.add(topic);
			System.out.println("Registrando al publisher "+this.getID()+" en el topico "+topic);
		}
	}
	/**
	 * Funci�n asociada al publicador
	 * Funci�n que se encarga de crear un mensaje, que se va a publicar a un topico que est� registrado y si no est� registrado, lo crear�.
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
		System.out.println("Soy publisher "+message.getCreator()+ " y env�o el mensaje "+message.getValue()+" al topico "+topic);
		return message;
	}

	/**
	 * Funci�n asociada al publicador,
	 * Se encarga de deregistrar el publicador a un t�pico.
	 */
	public void deregister_publisher(int topic) {
		System.out.println("El publisher "+this.getID()+" est� removiendo al tpico "+topic);
		if (topics!=null&&topics.contains(topic)){
		topics.remove((Object)topic);
		System.out.println("Desregistrando al publisher "+ this.getID()+" del t�pico "+topic);
		} else {
			System.out.println("El publisher "+ this.getID()+" no contiene al t�pico "+ topic);
		}
	}
/**
 * Funci�n asociada al publicador
 * Consiste en mandar a eliminar una publicaci�n random que este haya hecho antes.
 */
	public Message delete_publication(){
		if(sendMessages==null || sendMessages.size()==0) {
			System.out.println("El publisher "+this.getID()+" no puede eliminar publicaci�n porque a�n no env�a ninguna");
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
	 * Funci�n asociada al publicador
	 * M�s que nada es una funci�n de ayuda para ver todos los t�picos que tiene el publicador
	 */
	public void show_topics(){
		System.out.println("Soy :"+this.getID()+" y estoy asociado a los t�picos: ");
		for (int number : topics) {
			System.out.print(number+" ");
		}
		System.out.println(".");

}
	/**
	 * Funci�n asociada al t�pico,
	 * Esta funci�n es la encargada de agregar un nuevo subscrito a la lista de subscritos para un t�pico
	 */
	public void add_subscriber(int sub){
		if (isTopic()){
		if (subscribers.contains(sub)) System.out.println("--El t�pico "+this.getID()+" ya contiene al subscriptor: "+sub);
		else {
			this.subscribers.add(sub);
			System.out.println("Registrando del t�pico "+ this.getID()+" al subscriptor "+sub);
		}}
		else System.out.println("El nodo "+this.getID()+" no es t�pico");//porque viene del subscriber entonces no se crea como t�pico
	}
	/**
	 * Funci�n asociada al t�pico,
	 * Esta funci�n es la encargada de eliminar un subscrito a la lista de subscritos para un t�pico
	 * @param sub el entero que corresponde al id del subscriptor
	 */
	public void remove_subscriber(int sub){
		if(subscribers!=null &&subscribers.contains(sub)){
			System.out.println("Desregistrando al subscriber "+sub+" del t�pico "+ this.getID());
			this.subscribers.remove((Object)sub);
		} else 
			System.out.println("El subscriber "+sub+" no est� subscrito al t�pico"+ this.getID());
	}
	/**
	 * Funci�n asociada al t�pico
	 * Se encarga de agregar un mensaje que se public� al t�pico
	 * @param msg mensaje de la red.
	 */
	public void add_message(Message msg){
		if(!isTopic()) this.setTopic(true); //Porque viene del publisher
		messages.add(msg);
	}
	/**
	 * Funci�n asociada al t�pico
	 * Se encarga de agregar un mensaje que se public� al t�pico
	 * @param msg mensaje de la red.
	 */
	public void remove_message(Message msg){
		System.out.println("El t�pico "+this.getID()+" est� eliminando el mensaje "+msg.getValue());
		if(messages!= null && messages.size()!=0){
			for(int i =0; i<messages.size();i++){
				if(messages.get(i).getValue()==msg.getValue() && messages.get(i).getCreator()==msg.getCreator()){
					messages.remove(i);
					return;
				}
			}
		}
		else System.out.println("El t�pico no tiene el mensaje "+ msg.getValue());
		
	}
	/**
	 * Funci�n asociada al t�pico, usando interface.
	 * Entrega la lista de los subscriptores al t�pico
	 */
	public ArrayList<Integer> getSubscribers() {
		return subscribers;
	}

	/**
	 * Funci�n asociada al t�pico, 
	 * Se encarga de retornar la lista de mensajes que tiene el t�pico publicados en la red.
	 */
	public ArrayList<Message> getMessages(){
		return messages;
	}
	

	/**
	 * Funci�n asociada al t�pico
	 * B�sicamente consiste en mostrar por pantalla todos los subscritos al t�pico
	 */
	public void show_subscribers(){
		System.out.println("Soy :"+this.getID()+" y estoy asociado a los subscriptores: ");
		for (int number : subscribers) {
			System.out.print(number+" ");
		}
		System.out.println(".");
	}

	/**
	 * Funci�n asociada al subscriber, cuando se registra en un t�pico.
	 * @param topic Ingresa un t�pico al cual quiere registrar.
	 */
	public Message register_subscriber(int topic) {
		this.setSubscriber(true);
		Message message = new Message(-1, topic, 100);
		message.setCreator((int)this.getID());
		message.setType(2);
		System.out.println("Soy subscriber "+message.getCreator()+ " y me estoy subscribiendo al t�pico "+topic);
		return message;
	}
	
	/**
	 * Funci�n asociada al subscriber,
	 * El cual se desregistra del t�pico. el c�al se ingresa por par�metro de entrada.
	 */
	public Message deregister_subscriber(int topic) {
		Message message = new Message(-2, topic, 100);
		message.setCreator((int)this.getID());
		message.setType(3);
		System.out.println("Soy subscriber "+message.getCreator()+ " y me estoy desinscribiendo al t�pico "+topic);
		return message;
	}
	
	/**
	 * Funci�n asociada al Subscriber
	 * Que se encarga de recibir un mensaje.
	 */
	public void request_update(int message){
		System.out.println("El subscriptor "+this.getID()+" recibe el mensaje "+message);
	}


}
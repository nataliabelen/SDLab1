package cl.usach.sd;

/**
 * 
 * Clase la cual vamos a utilizar para enviar datos de un Peer a otro
 * contiene los atributos :
 * value: valor del mensaje
 * destination: id del nodo de destino
 * type: el tipo de mensaje, 
 * 		0: es un mensaje de publicar de un publisher al topic
 * 		1: es un mensaje de publicar de un topic a un subscriber
 * 		2: es un mensaje de un subscriber que quiere añadirse a un topic
 * 		3: es un mensaje de un subscriber que quiere desinscribirse de un topic
 * 		4: es un mensaje de un publisher que quiere eliminar una de sus publicaciones en un topic
 * Las demás funciones son getters y setters que no se explicarán por simplicidad.
 * @author Natalia
 */

public class Message {
	private int value;
	private int destination;
	private int type;
	private int TTL;
	private int creator;

	public int getCreator() {
		return creator;
	}

	public void setCreator(int creator) {
		this.creator = creator;
	}
	/**
	 * Constructor del mensaje, por defecto iniciará con type 0
	 * @param value será el valor que tendrá el mensaje
	 * @param destination el id del nodo del destino
	 * @param ttl time to live del mensaje
	 */
	public Message(int value,  int destination, int ttl) {

		this.setValue(value);
		this.setDestination(destination);
		this.setTTL(ttl);
		this.setType(0);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getTTL() {
		return TTL;
	}

	public void setTTL(int tTL) {
		TTL = tTL;
	}

	public int getDestination() {
		return destination;
	}

	public void setDestination(int destination) {
		this.destination = destination;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}

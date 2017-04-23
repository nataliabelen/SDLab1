package cl.usach.sd;

/**
 * Clase la cual vamos a utilizar para enviar datos de un Peer a otro
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

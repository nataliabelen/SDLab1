package cl.usach.sd;

/**
 * Interface que asegura que el nodo puede tener el comportamiento de un subscriber.
 * En el patrón de diseño Publisher-Subscriber.
 * @author Natalia
 *
 */
public interface Subscriber {

	Message register_subscriber(int topic);
	void request_update(int message);
	Message deregister_subscriber(int topic);
}

package cl.usach.sd;

import java.util.ArrayList;
/**
 * Interface que asegura que el publisher o publicador tendr� las funciones m�nimas que se requieren para este laboratorio
 * Recordemos que se est� simulando una red peer to peer donde el publisher puede publicar en un t�pico y la red debe encargarse
 * que este mensaje le llegue a todos los subscritos a ese t�pico.
 * @author Natalia
 *
 */
public interface Publisher {

	void register_publisher(int topic);
	Message publish();
	void deregister_publisher(int topic);
	Message delete_publication();
	void show_topics();
}

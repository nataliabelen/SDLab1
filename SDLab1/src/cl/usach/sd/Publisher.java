package cl.usach.sd;

import java.util.ArrayList;
/**
 * Interface que asegura que el publisher o publicador tendrá las funciones mínimas que se requieren para este laboratorio
 * Recordemos que se está simulando una red peer to peer donde el publisher puede publicar en un tópico y la red debe encargarse
 * que este mensaje le llegue a todos los subscritos a ese tópico.
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

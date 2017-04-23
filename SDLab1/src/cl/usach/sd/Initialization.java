package cl.usach.sd;

import java.util.ArrayList;
import java.util.Random;

import peersim.config.Configuration;
import peersim.core.CommonState;
import peersim.core.Control;
import peersim.core.Network;

public class Initialization implements Control {
	String prefix;

	int idLayer;
	int idTransport;

	//Valores que sacaremos del archivo de configuración	
	int publishers;
	int subscribers;

	public Initialization(String prefix) {
		this.prefix = prefix;
		/**
		 * Para obtener valores que deseamos como argumento del archivo de
		 * configuración, podemos colocar el prefijo de la inicialización en
		 * este caso "init.1statebuilder" y luego la variable de entrada
		 */
		// Configuration.getPid retornar al número de la capa
		// que corresponden esa parte del protocolo
		this.idLayer = Configuration.getPid(prefix + ".protocol");
		this.idTransport = Configuration.getPid(prefix + ".transport");
		// Configuration.getInt retorna el número del argumento
		// que se encuentra en el archivo de configuración.
		// También hay Configuration.getBoolean, .getString, etc...
		
		// en el archivo de configuración init.1statebuilder.argExample 100 y se puede usar ese valor.
		this.publishers = Configuration.getInt(prefix + ".publishers");
		this.subscribers = Configuration.getInt(prefix + ".subscribers");
		System.out.println("Publishers: " + publishers);
		System.out.println("Subscribers: "+ subscribers);
	}

	/**
	 * Ejecución de la inicialización en el momento de crear el overlay en el
	 * sistema
	 *
	 */
	@Override
	public boolean execute() {
		System.out.println("EJECUTAMOS EL SIMULADOR");
		/**
		 * Para comenzar tomaremos un nodo cualquiera de la red, a través de un random
		 */
		//int nodoInicial = CommonState.r.nextInt(Network.size());
		
		
	
		/**Es conveniente inicializar los nodos, puesto que los nodos 
		 * son una clase clonable y si asignan valores desde el constructor
		 *  todas tomaran los mismos valores, puesto que tomaran la misma dirección
		 * de memoria*/
		System.out.println("Inicializamos los nodos:");
		Random random = new Random();
		for (int i = 0; i < publishers; i++) {			
			((ExampleNode) Network.get(i)).setPublisher(true);
			for (int j=0; j<=random.nextInt(3);j++){
			int c= random.nextInt(Network.size());
			if(!((ExampleNode) Network.get(c)).isTopic()){
				((ExampleNode) Network.get(c)).setTopic(true);
			}
			((Publisher)Network.get(i)).register_publisher(c);
			}
		}
		
		for (int i = Network.size()-subscribers; i < Network.size(); i++) {			
			((ExampleNode) Network.get(i)).setSubscriber(true);
			for (int j=0; j<=random.nextInt(8);j++){
				int c= random.nextInt(Network.size());
				while(!((ExampleNode) Network.get(c)).isTopic()){
					c= random.nextInt(Network.size());
				}
				((Subscriber)Network.get(i)).register_subscriber((ExampleNode)Network.get(c));
			}
			
		}
		for (int i = 0; i < Network.size(); i++) {			
			System.out.println(((ExampleNode)Network.get(i)).show_all());
		}
		System.out.println("FIN DE INICIALIZACIÓN.\n");
		return true;
	}
}

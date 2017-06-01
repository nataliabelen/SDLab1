package cl.usach.sd;

import java.util.Random;

import peersim.config.Configuration;
import peersim.core.CommonState;
import peersim.core.Control;
import peersim.core.Linkable;
import peersim.core.Network;
import peersim.core.Node;
import peersim.edsim.EDSimulator;
/**
 * Clase que se encarga de generar tr�fico en la red.
 * @author Natalia
 *
 */
public class TrafficGenerator implements Control {
	private final static String PAR_PROT = "protocol";
	private final int layerId;
	private int probabilities;
	private int test;
	
	/**
	 * Constructor predeterminado de TrafficGenerator
	 * @param prefix
	 */
	public TrafficGenerator(String prefix) {
		layerId = Configuration.getPid(prefix + "." + PAR_PROT);
		probabilities = Configuration.getInt(prefix + ".probabilities");
		test = Configuration.getInt(prefix + ".test");
	}

	/**
	 * Funci�n que se ejecuta para generar tr�fico en la red. Parte buscando un nodo al azar y �ste realiza una acci�n random (o especifica leer readme) 
	 */
	@Override
	public boolean execute() {
		// Consideraremos cualquier nodo de manera aleatoria de la red para comenzar y finalizar
		ExampleNode initNode = (ExampleNode)Network.get(CommonState.r.nextInt(Network.size())); 	
		do_random(initNode, layerId, test);
		// Y se env�a, para realizar la simulaci�n
		// Los par�metros corresponde a:
		// long arg0: Delay del evento
		// Object arg1: Evento enviado
		// Node arg2: Nodo por el cual inicia el env�o del dato
		// int arg3: N�mero de la capa del protocolo que creamos (en este caso
		// de layerId)
		
	
		return false;
	}

	/**
	 * Funci�n generada para realizar acciones de forma aleatoria en los nodos que reciben alg�n mensaje y as� hacer m�s
	 * din�mica la red.
	 * @param myNode es el nodo en el cual se ejecuta la acci�n.
	 * @param layerId capa
	 * @param prueba entero que especifica si se quiere probar alguna funcionalidad en particular. (m�s informaci�n leer el readme)
	 */
	public void do_random(ExampleNode myNode, int layerId, int prueba){

		Random random = new Random();
		int r =random.nextInt(probabilities);
		if (prueba !=0) r = prueba;
		int randSize;
		if(r <2){
			Message message = myNode.publish();
			EDSimulator.add(0, message, myNode, layerId);
		}
		else if(r ==2 && myNode.isPublisher()){
			randSize = random.nextInt(Network.size());
			((Publisher)myNode).register_publisher(randSize);
		}
		else if(r ==3 && myNode.isPublisher()){
			randSize = random.nextInt(Network.size());
			((Publisher)myNode).deregister_publisher(randSize);
		}
		else if(r ==4 ){
			myNode.setSubscriber(true);
			randSize = random.nextInt(Network.size());
			Message message=((Subscriber)myNode).register_subscriber(randSize);
			EDSimulator.add(0, message, myNode, layerId);
		}
		else if(r==5 && myNode.isSubscriber()){
			randSize = random.nextInt(Network.size());
			Message message=((Subscriber)myNode).deregister_subscriber(randSize);
			EDSimulator.add(0, message, myNode, layerId);
		}
		else if(r==6 && myNode.isPublisher()){
			Message message=((Publisher)myNode).delete_publication();
			if (message!=null){
				EDSimulator.add(0, message, myNode, layerId);
			}
			
		}
	}
}

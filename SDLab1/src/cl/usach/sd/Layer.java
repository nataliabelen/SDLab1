package cl.usach.sd;

import java.util.ArrayList;

import peersim.config.Configuration;
import peersim.config.FastConfig;
import peersim.core.CommonState;
import peersim.core.Linkable;
import peersim.core.Network;
import peersim.core.Node;
import peersim.dynamics.WireKOut;
import peersim.edsim.EDProtocol;
import peersim.transport.Transport;

public class Layer implements Cloneable, EDProtocol {
	private static final String PAR_TRANSPORT = "transport";
	private static String prefix = null;
	private int transportId;
	private int layerId;

	/**
	 * Método en el cual se va a procesar el mensaje que ha llegado al Nodo
	 * desde otro Nodo. Cabe destacar que el mensaje va a ser el evento descrito
	 * en la clase, a través de la simulación de eventos discretos.
	 */
	@Override
	public void processEvent(Node myNode, int layerId, Object event) {
		/**Este metodo trabajará sobre el mensaje*/

		Message message = (Message) event;
		message.setTTL(message.getTTL()-1);
		//Si type es 0 significa que el mensaje está buscando el topic
		if (message.getType()==0){
			if(((ExampleNode)myNode).getID()==message.getDestination()){
				System.out.println("Se ha encontrado el topico " + ((ExampleNode)myNode).getID()+" para enviar el mensaje "+message.getValue());
				((ExampleNode)myNode).add_message(message);
				ArrayList<Integer> subscribers = ((ExampleNode)myNode).getSubscribers();
				if(subscribers.isEmpty()) System.out.println("El topico no tiene subscriptores aún");
				else{
					for (int number : subscribers) {
						Message x = new Message(message.getValue(),number, message.getTTL());
						x.setType(1);
						sendmessage(myNode, layerId, x);
						System.out.println("Se ha generado el mensaje para el nodo: "+number);
					}
				}
			}
			else{
				sendmessage(myNode, layerId, message);
			}
		}
		else {
			if(((ExampleNode)myNode).getID()==message.getDestination()){
				((Subscriber)myNode).request_update(message.getValue());
			}
			else{
				sendmessage(myNode, layerId, message);
			}
		}
		getStats();
	}

	private void getStats() {
		Observer.message.add(1);
	}

	public void sendmessage(Node currentNode, int layerId, Object message) {
		/**Con este método se enviará el mensaje de un nodo a otro
		 * CurrentNode, es el nodo actual
		 * message, es el mensaje que viene como objeto, por lo cual se debe trabajar sobre él
		 */
		
		/**----EJEMPLO 1:----*/
		/**
		 * Para esto se debe descomentar el archivo de configuración init.0rndlink.undir false
		 * Con la función degree, se puede conseguir la cantidad de vecinos que posee un nodo*/
		int randDegree = CommonState.r.nextInt(((Linkable) currentNode.getProtocol(0)).degree());
		/**Se selecciona un nodo destino a través de este random*/
		Node sendNode = ((Linkable) currentNode.getProtocol(0)).getNeighbor(randDegree);

		/**---EJEMPLO 2: ----*/
		
		//int randSize = CommonState.r.nextInt(Network.size());
		//Node sendNode = Network.get(randSize);
		
		/*Antes de enviar al siguiente nodo asignamos el valor del mensaje al nodo (por hacer algo)*/
		int value = ((Message) message).getValue();
		ExampleNode actual = (ExampleNode) currentNode;
		
		
		/**
		 * Envío del dato a través de la capa de transporte, la cual enviará
		 * según el ID del emisor y el receptor
		 */	
		((Transport) currentNode.getProtocol(transportId)).send(currentNode, sendNode, message, layerId);
		System.out.println("\tNodo "+(int) actual.getID()+" envia mensaje "+value+" a nodo "+ (int) ((ExampleNode) sendNode).getID());
		// Otra forma de hacerlo
		// ((Transport)
		// currentNode.getProtocol(FastConfig.getTransport(layerId))).send(currentNode,
		// searchNode(sendNode), message, layerId);

	}

	/**
	 * Constructor por defecto de la capa Layer del protocolo construido
	 * 
	 * @param prefix
	 */
	public Layer(String prefix) {
		/**
		 * InicializaciÃ³n del Nodo
		 */
		Layer.prefix = prefix;
		transportId = Configuration.getPid(prefix + "." + PAR_TRANSPORT);
		/**
		 * Siguiente capa del protocolo
		 */
		layerId = transportId + 1;
	}

	private Node searchNode(int id) {
		return Network.get(id);
	}

	/**
	 * Definir Clone() para la replicacion de protocolo en nodos
	 */
	public Object clone() {
		Layer dolly = new Layer(Layer.prefix);
		return dolly;
	}
}

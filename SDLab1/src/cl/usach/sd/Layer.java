package cl.usach.sd;

import java.util.ArrayList;
import java.util.Random;

import peersim.config.Configuration;
import peersim.config.FastConfig;
import peersim.core.CommonState;
import peersim.core.Linkable;
import peersim.core.Network;
import peersim.core.Node;
import peersim.dynamics.WireKOut;
import peersim.edsim.EDProtocol;
import peersim.transport.Transport;
/**
 * Clase que se encarga de manejar los eventos (estos serían los mensajes) en la red.
 * @author Natalia
 *
 */
public class Layer implements Cloneable, EDProtocol {
	private static final String PAR_TRANSPORT = "transport";
	private static String prefix = null;
	private int transportId;
	private int layerId;


	/**
	 * Constructor por defecto de la capa Layer del protocolo construido
	 * Extrae las variables de probabilities y test descritas en el readme.
	 * @param prefix
	 */
	public Layer(String prefix) {
		// Inicialización del Nodo
		Layer.prefix = prefix;
		transportId = Configuration.getPid(prefix + "." + PAR_TRANSPORT);
		//Siguiente capa del protocolo
		layerId = transportId + 1;

		
	}

	/**
	 * Método en el cual se va a procesar el mensaje que ha llegado al Nodo
	 * desde otro Nodo. Cabe destacar que el mensaje va a ser el evento descrito
	 * en la clase, a través de la simulación de eventos discretos.
	 * Los mensajes que llegan se distinguen por el tipo de mensaje que son.
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
				if(subscribers.isEmpty()) System.out.println("El tópico no tiene subscriptores aún");
				else{
					for (int number : subscribers) {
						Message x = new Message(message.getValue(),number, message.getTTL());
						x.setType(1);
						sendmessage(myNode, layerId, x);
						getStats();
						System.out.println("Se ha generado el mensaje para el subscriptor: "+number);
						getStats();
					}
				}
			}
			else{
				sendmessage(myNode, layerId, message);
				getStats();
			}
		}
		//si type es 1 significa que el mensaje está buscando al subscriptor.
		else if(message.getType()==1) {
			if(((ExampleNode)myNode).getID()==message.getDestination()){
				((Subscriber)myNode).request_update(message.getValue());
			}
			else{
				sendmessage(myNode, layerId, message);
				getStats();
			}
		}
		//si type es 2 significa que es un subscriptor queriendo añadirse a un topico.
		else if(message.getType()==2) {
			if(((ExampleNode)myNode).getID()==message.getDestination()){

				((Topic)myNode).add_subscriber(message.getCreator());
			}
			else{
				sendmessage(myNode, layerId, message);
				getStats();
			}
		}
		//Si type es 3 significa que el subscriptor quiere desinscribirse a un topico
		else  if(message.getType()==3){
			if(((ExampleNode)myNode).getID()==message.getDestination()){
				((Topic)myNode).remove_subscriber(message.getCreator());
			}
			else{
				sendmessage(myNode, layerId, message);
				getStats();
			}
		}
		//Si type es 4 significa que el publisher quiere eliminar una publicación
		else  {
			if(((ExampleNode)myNode).getID()==message.getDestination()){
				((Topic)myNode).remove_message(message);
			}
			else{
				sendmessage(myNode, layerId, message);
				getStats();
			}
		}
	}

	
	
	private void getStats() {
		Observer.message.add(1);
	}

	/**
	 * Con este método se enviará el mensaje de un nodo a otro vecino dentro de la red.
	 * @param currentNode es el nodo actual
	 * @param layerId capa
	 * @param message es el mensaje que viene como objeto, por lo cual se debe trabajar sobre él
	 */
	public void sendmessage(Node currentNode, int layerId, Object message) {
		int randDegree = CommonState.r.nextInt(((Linkable) currentNode.getProtocol(0)).degree());
		//Se selecciona un nodo destino a través de este random*/
		Node sendNode = ((Linkable) currentNode.getProtocol(0)).getNeighbor(randDegree);

		int value = ((Message) message).getValue();
		int type = ((Message) message).getType();
		ExampleNode actual = (ExampleNode) currentNode;
		
		//Envío del dato a través de la capa de transporte, la cual enviará según el ID del emisor y el receptor
		((Transport) currentNode.getProtocol(transportId)).send(currentNode, sendNode, message, layerId);
		System.out.println("\tNodo "+(int) actual.getID()+" envia mensaje "+value+", tipo "+type+" a nodo "+ (int) ((ExampleNode) sendNode).getID());
	}


	/**
	 * Definir Clone() para la replicacion de protocolo en nodos
	 */
	public Object clone() {
		Layer dolly = new Layer(Layer.prefix);
		return dolly;
	}
}

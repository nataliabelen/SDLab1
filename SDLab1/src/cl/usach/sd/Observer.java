package cl.usach.sd;

import java.util.Random;

import peersim.config.Configuration;
import peersim.core.CommonState;
import peersim.core.Control;
import peersim.core.Network;
import peersim.core.Node;
import peersim.edsim.EDSimulator;
import peersim.util.IncrementalStats;
/**
 * Clase que se encarga de mostrar los nodos actualizados en la red, con la información básica.
 * @author Natalia
 *
 */
public class Observer implements Control {

	private int layerId;
	private String prefix;

	public static IncrementalStats message = new IncrementalStats();

	/**
	 * Constructor predeterminado de la clase Observer
	 * @param prefix
	 */
	public Observer(String prefix) {
		this.prefix = prefix;
		this.layerId = Configuration.getPid(prefix + ".protocol");

	}

	
	/**
	 * Función que se ejecuta cada cierto tiempo (definido en config-example.cfg) en la cual se muestra la información
	 * actual de los nodos de la red.
	 */
	@Override
	public boolean execute() {
		Random random = new Random();
		System.err.println("[time="+ CommonState.getTime() +"]["+(int) message.getSum()+" Total send message]-- Resume:");
		for (int i = 0; i < Network.size(); i++) {			
			System.err.println(((ExampleNode) Network.get(i)).show_all());
		}
		return false;
	}

}

package cl.usach.sd;

import java.util.Random;

import peersim.config.Configuration;
import peersim.core.CommonState;
import peersim.core.Control;
import peersim.core.Network;
import peersim.core.Node;
import peersim.edsim.EDSimulator;
import peersim.util.IncrementalStats;

public class Observer implements Control {

	private int layerId;
	private String prefix;
	private double prob_publish;
	private double prob_delete_publication;
	private double prob_deregister_publisher;
	private double prob_register_subscriber;
	private double prob_deregister_subscriber;

	public static IncrementalStats message = new IncrementalStats();

	public Observer(String prefix) {
		this.prefix = prefix;
		this.layerId = Configuration.getPid(prefix + ".protocol");
		this.prob_publish = (double)Configuration.getPid(prefix + ".publish");
		this.prob_delete_publication = (double)Configuration.getPid(prefix + ".deletePublication");
		this.prob_deregister_subscriber = (double)Configuration.getPid(prefix + ".deregisterPublisher");
		this.prob_register_subscriber = (double)Configuration.getPid(prefix + ".registerSubscriber");
		this.prob_deregister_subscriber = (double)Configuration.getPid(prefix + ".deregisterSubscriber");
	}

	
	//imprimir info de topicos, subscriber.
	@Override
	public boolean execute() {
		Random random = new Random();
		System.err.println("[time="+ CommonState.getTime() +"]["+(int) message.getSum()+" Total send message]-- Resume:");
		for (int i = 0; i < Network.size(); i++) {			
			System.err.println(((ExampleNode) Network.get(i)).show_all());
		}
		/**
		Node initNode = Network.get(CommonState.r.nextInt(Network.size())); 		
		Message message = ((Publisher)initNode).publish();
		EDSimulator.add(0, message, initNode, layerId);
		*/
		double r = random.nextDouble();
		if (r<=this.prob_publish){
			Node initNode = Network.get(CommonState.r.nextInt(Network.size())); 		
			Message message = ((Publisher)initNode).publish();
			EDSimulator.add(0, message, initNode, layerId);
		}
		r = random.nextDouble();
		if (r<=this.prob_register_subscriber){
			Node initNode = Network.get(CommonState.r.nextInt(Network.size())); 		
			//Message message = ((Subscriber)initNode).register_subscriber(sub);
			EDSimulator.add(0, message, initNode, layerId);
		}
		return false;
	}

}

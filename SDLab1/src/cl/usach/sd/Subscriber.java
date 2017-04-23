package cl.usach.sd;

public interface Subscriber {

	void register_subscriber(Topic sub);
	void request_update(int message);
	void deregister_subscriber(Topic topic);
}

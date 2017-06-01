README:
Laboratorio 1 de Sistemas Distribuidos 2017-1
Profesor: Daniel Wladdimiro 
Ayudante: Maximiliano Pérez.
Integrante: Natalia Pérez.


Este laboratorio consiste en simular un protocolo Publisher-Subscriber en una red peer to peer.
Algunas variables que se pueden modificar para probar esta simulación en config/config-example.cfg, son:
línea 4: SIZE que especifica la cantidad de nodos que esta red tendrá (este valor será constante a lo largo de la simulación)
línea 6: P es la cantidad de publicadores que habrán en un comienzo (inicialización), tiene que ser un número menor o igual al tamaño de la red
línea 8: S es la cantidad de subscriptores que habrán en un comienzo (inicialización), tiene que ser un número menor o igual al tamaño de la red
línea 10: T es un número especifico para probar el correcto funcionamiento de las acciones de los nodos dentro de la red. 
	Cada vez que un nodo recibe un mensaje, realizará una acción (dependiendo de la probabilidad de ocurrencia)
	Se detalla acontinuación las acciones:
	1: publisher. Si sólo prueba publisher, los nodos sólo se encargarán de publicar en un tópico cualquiera dentro de la red. Si no es tópico, lo creará
	2: register_publisher. Los nodos sólo se encargarán de registrarse en un tópico random.
	3: deregister_publisher. El publisher se desregistrará de un tópico
	4: register_subscriber. Un subscriber se inscribirá en un tópico, para esto tiene que mandar un mensaje en la red.
	5: deregister_subscriber. Un subscriber se desinscribirá de un tópico en la red.
	6: delete_publication. Un publicador, si tiene mensajes publicados, podrá eliminar alguno.
	0: random. si se deja activado el 0, el nodo podrá realizar cualquier acción de las antes mencionadas, de forma random  (teniendo más probabilidad el "publish")
línea 12: Pr Probabilidad de ocurrencia de las acciones anteriores, o más bien probabilidad de que no ocurra.
	Como se vio en el apartado anterior, existen acciones desde la 0 a la 6. Por lo que si la probabilidad Pr es 7 (que es minimo que puede tener)
	Si o si realizará alguna de estas acciones al pasar por la red. Si el número es 15 por ejemplo, significa que existen 9/15 probabilidades de que no ejecute ninguna acción
	al pasar por ese nodo. Esto implica que la red estará más descongestionada al momento de enviar mensajes.
	Si el número se aleja demasiado (por ejemplo 100) existe la probabilidad de 1/100 de que el nodo realice una acción. Por lo que se aconseja un número 20 aproximadamente.
línea 18: CYCLES es la cantidad de ciclos que habrán en la simulación
línea 20: CYCLE es la cantidad de tiempo transcurrido que habrá en un ciclo (se muestra por la aparición de current time en consola)
línea 46: OBSERVER_STEP que es la cantidad de tiempo transcurrido en la cual vuelve a llamar al observer (se muestra la información de los nodos por pantalla)
línea 47: TRAFFIC_STEP que es la cantidad de tiempo transcurrido en la cual vuelve a llamar al trafficGenerator, el cual genera nuevos mensajes en la red (nuevas acciones)

Como consejo es que al ejecutar "Run Configurations" ir a "Common" y clickear en Output file :${workspace_loc:/SDLab1}/output.txt

Con esta información ya es posible echar a correr el laboratorio.
y así se guardará el archivo de salida, lo cual será más fácil al momento de evaluar como se comporta la red.

Datos del laboratorio:
El ExampleNode que es el nodo de la red, se puede comportar como Publisher, Subscriber y Topic.
Al inicializar la red se declaran algunos como publisher y otros como subscriber. luego en trafficGeneration se inicia con sólo un nodo tratando de publicar en un tópico.
A medida que el mensaje va pasando de nodo a nodo, los nodos pueden realizar algunas acciones (que están definidas en el método "do_random" de Layer)
Estas pueden ser: un nodo cualquiera puede publicar en la red, un publisher puede registrarse en un tópico, o desregistrarse, un subscriptor puede subscribirse a un tópico, 
o desinscribirse, y un publicador puede eliminar una publicación ya hecha.
Todos estos cambios hacen que el nodo transite entre publisher, subscriber y topic, enviando y recibiendo mensajes. Por lo cual, 
el Observer se encarga de cada cierto tiempo ir mostrando esa información actualizada de los nodos.

Además en la carpeta /doc se encuentra el javadoc generado, para mejor visión de las clases, métodos, etc.
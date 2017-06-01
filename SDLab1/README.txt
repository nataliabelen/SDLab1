README:
Laboratorio 1 de Sistemas Distribuidos 2017-1
Profesor: Daniel Wladdimiro 
Ayudante: Maximiliano P�rez.
Integrante: Natalia P�rez.


Este laboratorio consiste en simular un protocolo Publisher-Subscriber en una red peer to peer.
Algunas variables que se pueden modificar para probar esta simulaci�n en config/config-example.cfg, son:
l�nea 4: SIZE que especifica la cantidad de nodos que esta red tendr� (este valor ser� constante a lo largo de la simulaci�n)
l�nea 6: P es la cantidad de publicadores que habr�n en un comienzo (inicializaci�n), tiene que ser un n�mero menor o igual al tama�o de la red
l�nea 8: S es la cantidad de subscriptores que habr�n en un comienzo (inicializaci�n), tiene que ser un n�mero menor o igual al tama�o de la red
l�nea 10: T es un n�mero especifico para probar el correcto funcionamiento de las acciones de los nodos dentro de la red. 
	Cada vez que un nodo recibe un mensaje, realizar� una acci�n (dependiendo de la probabilidad de ocurrencia)
	Se detalla acontinuaci�n las acciones:
	1: publisher. Si s�lo prueba publisher, los nodos s�lo se encargar�n de publicar en un t�pico cualquiera dentro de la red. Si no es t�pico, lo crear�
	2: register_publisher. Los nodos s�lo se encargar�n de registrarse en un t�pico random.
	3: deregister_publisher. El publisher se desregistrar� de un t�pico
	4: register_subscriber. Un subscriber se inscribir� en un t�pico, para esto tiene que mandar un mensaje en la red.
	5: deregister_subscriber. Un subscriber se desinscribir� de un t�pico en la red.
	6: delete_publication. Un publicador, si tiene mensajes publicados, podr� eliminar alguno.
	0: random. si se deja activado el 0, el nodo podr� realizar cualquier acci�n de las antes mencionadas, de forma random  (teniendo m�s probabilidad el "publish")
l�nea 12: Pr Probabilidad de ocurrencia de las acciones anteriores, o m�s bien probabilidad de que no ocurra.
	Como se vio en el apartado anterior, existen acciones desde la 0 a la 6. Por lo que si la probabilidad Pr es 7 (que es minimo que puede tener)
	Si o si realizar� alguna de estas acciones al pasar por la red. Si el n�mero es 15 por ejemplo, significa que existen 9/15 probabilidades de que no ejecute ninguna acci�n
	al pasar por ese nodo. Esto implica que la red estar� m�s descongestionada al momento de enviar mensajes.
	Si el n�mero se aleja demasiado (por ejemplo 100) existe la probabilidad de 1/100 de que el nodo realice una acci�n. Por lo que se aconseja un n�mero 20 aproximadamente.
l�nea 18: CYCLES es la cantidad de ciclos que habr�n en la simulaci�n
l�nea 20: CYCLE es la cantidad de tiempo transcurrido que habr� en un ciclo (se muestra por la aparici�n de current time en consola)
l�nea 46: OBSERVER_STEP que es la cantidad de tiempo transcurrido en la cual vuelve a llamar al observer (se muestra la informaci�n de los nodos por pantalla)
l�nea 47: TRAFFIC_STEP que es la cantidad de tiempo transcurrido en la cual vuelve a llamar al trafficGenerator, el cual genera nuevos mensajes en la red (nuevas acciones)

Como consejo es que al ejecutar "Run Configurations" ir a "Common" y clickear en Output file :${workspace_loc:/SDLab1}/output.txt

Con esta informaci�n ya es posible echar a correr el laboratorio.
y as� se guardar� el archivo de salida, lo cual ser� m�s f�cil al momento de evaluar como se comporta la red.

Datos del laboratorio:
El ExampleNode que es el nodo de la red, se puede comportar como Publisher, Subscriber y Topic.
Al inicializar la red se declaran algunos como publisher y otros como subscriber. luego en trafficGeneration se inicia con s�lo un nodo tratando de publicar en un t�pico.
A medida que el mensaje va pasando de nodo a nodo, los nodos pueden realizar algunas acciones (que est�n definidas en el m�todo "do_random" de Layer)
Estas pueden ser: un nodo cualquiera puede publicar en la red, un publisher puede registrarse en un t�pico, o desregistrarse, un subscriptor puede subscribirse a un t�pico, 
o desinscribirse, y un publicador puede eliminar una publicaci�n ya hecha.
Todos estos cambios hacen que el nodo transite entre publisher, subscriber y topic, enviando y recibiendo mensajes. Por lo cual, 
el Observer se encarga de cada cierto tiempo ir mostrando esa informaci�n actualizada de los nodos.

Adem�s en la carpeta /doc se encuentra el javadoc generado, para mejor visi�n de las clases, m�todos, etc.
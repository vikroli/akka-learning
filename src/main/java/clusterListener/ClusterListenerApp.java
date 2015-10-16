package clusterListener;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class ClusterListenerApp {

  public static void main(String[] args) {
    final ActorSystem system = ActorSystem.create("helloakka");
    final ActorRef listener = system.actorOf(Props.create(ClusterListener.class), "listener");


  }
}

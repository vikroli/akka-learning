package clusterSender;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class ClusterSenderApp {

  public static void main(String[] args) {
    final ActorSystem system = ActorSystem.create("helloakka");
    final ActorRef sender = system.actorOf(Props.create(ClusterSender.class), "sender");


  }
}

package receiver;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class ReceiverLauncher {

  public static void main(String[] args) {
      final ActorSystem system = ActorSystem.create("helloakka");
      final ActorRef receiver = system.actorOf(Props.create(Receiver.class), "receiver");

  }
}

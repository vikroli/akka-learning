package sender;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import common.Ping;
import common.Pong;
import receiver.Receiver;

public class Sender extends UntypedActor {
  final ActorSystem system = ActorSystem.create("helloakka");
  final ActorRef receiver = system.actorOf(Props.create(Receiver.class), "receiver");

  @Override
  public void onReceive(Object message) throws Exception {
    try {
      if (message instanceof String) {
        Ping p = new Ping((String) message);
        // router.tell(p, getSelf());
      } else if (message instanceof Pong) {
        System.out.println("Antwort erhalten: " + ((Pong) message).toString());
      } else {
        unhandled(message);
      }
    } catch (Exception e) {
      // getSender().tell(new Status.Failure(e), getSelf());
      throw e;
    }

  }

}

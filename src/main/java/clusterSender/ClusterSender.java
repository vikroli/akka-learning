package clusterSender;

import akka.actor.UntypedActor;
import clusterMassages.Ping;
import clusterMassages.Pong;

public class ClusterSender extends UntypedActor {

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

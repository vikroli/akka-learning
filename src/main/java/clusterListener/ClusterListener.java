package clusterListener;

import akka.actor.UntypedActor;
import clusterMassages.Ping;
import clusterMassages.Pong;

public class ClusterListener extends UntypedActor {

  @Override
  public void onReceive(Object msg) throws Exception {
    if (msg instanceof Ping) {
      Pong response = new Pong(((Ping) msg).getMsgId(), "Pong");
      getSender().tell(response, getSelf());
    } else
      unhandled(msg);
  }

}

package clusterListener;

import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.ClusterEvent.MemberEvent;
import clusterMassages.Ping;
import clusterMassages.Pong;

public class ClusterListener extends UntypedActor {

  Cluster cluster = Cluster.get(context().system());

  {
    cluster.subscribe(self(), MemberEvent.class, ClusterEvent.class);
    System.err.println(cluster);
  }

  @Override
  public void onReceive(Object msg) throws Exception {
    System.out.println("ClusterListener.onReceive(): Nachricht erhalten");
    if (msg instanceof Ping) {
      System.out.println("ClusterListener.onReceive(): Ping erhalten");
      Pong response = new Pong(((Ping) msg).getMsgId(), "Pong");
      getSender().tell(response, getSelf());
    } else
      System.out.println("ClusterListener.onReceive(): unbekannte Nachricht");
    unhandled(msg);
  }

}

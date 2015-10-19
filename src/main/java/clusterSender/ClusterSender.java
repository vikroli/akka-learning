package clusterSender;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.ClusterEvent.MemberEvent;
import akka.routing.FromConfig;
import clusterListener.ClusterListener;
import clusterMassages.Ping;
import clusterMassages.Pong;

public class ClusterSender extends UntypedActor {
  ActorRef listener = getContext()
      .actorOf(FromConfig.getInstance().props(Props.create(ClusterListener.class)), "listener");

  Cluster cluster = Cluster.get(context().system());

  {
    cluster.subscribe(self(), MemberEvent.class, ClusterEvent.class);
    System.err.println(cluster);
  }

  @Override
  public void onReceive(Object message) throws Exception {
    System.out.println("name: " + message.getClass().getName());
    try {
      if (message instanceof Ping) {
        System.out.println("Ping im Sender bekommen");
        listener.tell(message, getSelf());
      } else if (message instanceof String) {
        System.out.println("String im Sender bekommen");
        listener.tell(new Ping((String) message), getSelf());
      } else if (message instanceof Pong) {
        System.out.println("Pong im Sender bekommen: " + ((Pong) message).toString());
      } else {
        System.out.println("unhandled im Sender bekommen");
        unhandled(message);
      }
    } catch (Exception e) {
      // getSender().tell(new Status.Failure(e), getSelf());
      throw e;
    }

  }

}

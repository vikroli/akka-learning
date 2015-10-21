package clusterSender;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent.MemberEvent;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.FromConfig;
import clusterMassages.Ping;
import clusterMassages.Pong;

public class ClusterSender extends UntypedActor {

  ActorRef listener = getContext().actorOf(FromConfig.getInstance().props(), "listenerRouter");
  // .actorOf(FromConfig.getInstance().props(Props.create(ClusterListener.class)),
  // "listenerRouter");
  public static long seqId = 1l;

  // ActorRef listener = getContext().actorOf(FromConfig.getInstance().props(), "listener");
  LoggingAdapter log = Logging.getLogger(getContext().system(), this);
  Cluster cluster = Cluster.get(getContext().system());

  // subscribe to cluster
  @Override
  public void preStart() {
    cluster.subscribe(getSelf(), MemberEvent.class);
  }

  // re-subscribe when restart
  @Override
  public void postStop() {
    cluster.unsubscribe(getSelf());
  }

  @Override
  public void onReceive(Object message) throws Exception {
    try {
      if (message instanceof String) {
        Ping p = new Ping((String) message, seqId++);
        System.out.println("String bekommen, msgId="+p.getMsgId());
        listener.tell(p, getSelf());
      } else if (message instanceof Pong) {
        System.out.println("Pong bekommen, msgId= " + ((Pong) message).getMsgId());
      } else {
        System.out.println("unhandled bekommen, class=" + message.getClass().getName());
        unhandled(message);
      }
    } catch (Exception e) {
      // getSender().tell(new Status.Failure(e), getSelf());
      throw e;
    }
  }
}

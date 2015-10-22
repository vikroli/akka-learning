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

  private ActorRef listener =
      getContext().actorOf(FromConfig.getInstance().props(), "listenerRouter");
  public static long seqId = 1l;


  private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
  private Cluster cluster = Cluster.get(getContext().system());

  // subscribe to cluster
  @Override
  public void preStart() {
    log.info("jkjh");
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
        log.info("Ping command received, msgId=" + p.getMsgId());
        listener.tell(p, getSelf());
      } else if (message instanceof Pong) {
        log.info("Recieved a Pong with msgId=" + ((Pong) message).getMsgId());
      } else {
        log.info("Received unhandled, class=" + message.getClass().getName());
        unhandled(message);
      }
    } catch (Exception e) {
      throw e;
    }
  }
}

package clusterListener;

import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent.MemberEvent;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import clusterMassages.Ping;
import clusterMassages.Pong;

public class ClusterListener extends UntypedActor {
  private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
  private Cluster cluster = Cluster.get(getContext().system());

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
  public void onReceive(Object msg) throws Exception {
    if (msg instanceof Ping) {
      log.info("Received a Ping with msgId=" + ((Ping) msg).getMsgId());
      Pong response = new Pong(((Ping) msg).getMsgId(), "Pong");
      getSender().tell(response, getSelf());
    } else
      unhandled(msg);
  }
}

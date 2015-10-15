package receiver;

import akka.actor.UntypedActor;
import common.Ping;
import common.Pong;

public class Receiver extends UntypedActor {

  @Override
  public void onReceive(Object msg) throws Exception {
    if (msg instanceof Ping) {
      Pong response = new Pong(((Ping) msg).getMsgId(), "Pong");
      getSender().tell(response, getSelf());
    } else
      unhandled(msg);
  }

}

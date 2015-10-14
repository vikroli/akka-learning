import akka.actor.UntypedActor;

public class Receiver extends UntypedActor {

  @Override
  public void onReceive(Object msg) throws Exception {
    if (msg instanceof Ping) {
      ((Ping) msg).setMsg("Pong");
      getSender().tell(msg, getSelf());
    } else
      unhandled(msg);
  }

}

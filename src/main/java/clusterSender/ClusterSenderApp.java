package clusterSender;

import static akka.pattern.Patterns.ask;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.concurrent.TimeUnit;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.OnSuccess;
import akka.util.Timeout;
import clusterMassages.Ping;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

public class ClusterSenderApp {

  public static void main(String[] args) {
    // final ActorSystem system = ActorSystem.create("helloakka");
    // final ActorRef sender = system.actorOf(Props.create(ClusterSender.class), "sender");
    // Override the configuration of the port when specified as program argument
    final String port = args.length > 0 ? args[0] : "2551";
    final Config config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port)
        .withFallback(ConfigFactory.parseString("akka.cluster.roles = [sender]"))
        .withFallback(ConfigFactory.load("sender"));

    ActorSystem system = ActorSystem.create("ClusterSystem", config);

    final ActorRef sender = system.actorOf(Props.create(ClusterSender.class), "sendder");
    final FiniteDuration interval = Duration.create(2, TimeUnit.SECONDS);
    final Timeout timeout = new Timeout(Duration.create(5, TimeUnit.SECONDS));
    final ExecutionContext ec = system.dispatcher();
    // final AtomicInteger counter = new AtomicInteger();
    system.scheduler().schedule(interval, interval,
        // () -> ask(sender, new Ping("hello" + counter.incrementAndGet()), timeout)
        () -> ask(sender, new Ping("hello"), timeout).onSuccess(new OnSuccess<Object>() {
          @Override
          public void onSuccess(Object result) {
            System.out.println(result);
          }
        }, ec), ec);

  }

}

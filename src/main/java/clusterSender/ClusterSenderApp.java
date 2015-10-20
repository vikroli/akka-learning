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
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

public class ClusterSenderApp {

  public static void main(String[] args) {
    Config config = ConfigFactory.load("sender");
    ActorSystem system = ActorSystem.create("ClusterSystem", config);

    ActorRef sender = system.actorOf(Props.create(ClusterSender.class), "sender");

    final FiniteDuration interval = Duration.create(2, TimeUnit.SECONDS);
    final Timeout timeout = new Timeout(Duration.create(10, TimeUnit.SECONDS));
    final ExecutionContext ec = system.dispatcher();



    system.scheduler().schedule(interval, interval,
        () -> ask(sender, "hello", timeout).onSuccess(new OnSuccess<Object>() {
          @Override
          public void onSuccess(Object result) {
            System.out.println(result);
          }
        }, ec), ec);



    System.out.println("ClusterSenderApp.main()");

  }

}

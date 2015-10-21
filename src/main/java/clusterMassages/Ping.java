package clusterMassages;

import java.io.Serializable;

import akka.routing.ConsistentHashingRouter.ConsistentHashable;

public class Ping implements Serializable, ConsistentHashable {

  private static final long serialVersionUID = 7379971135052589365L;
  public String msg;
  public long msgId;

  public Ping(String message, long msgId) {
    super();
    this.msg = message;
    this.msgId = msgId;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public long getMsgId() {
    return msgId;
  }

  public void setMsgId(long msgId) {
    this.msgId = msgId;
  }

  @Override
  public Object consistentHashKey() {
    return msg;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((msg == null) ? 0 : msg.hashCode());
    result = prime * result + (int) (msgId ^ (msgId >>> 32));
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Ping other = (Ping) obj;
    if (msg == null) {
      if (other.msg != null)
        return false;
    } else if (!msg.equals(other.msg))
      return false;
    if (msgId != other.msgId)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Ping [msg=" + msg + ", msgId=" + msgId + "]";
  }

}

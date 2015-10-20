package clusterMassages;

import java.io.Serializable;

public class Pong implements Serializable {

  private static final long serialVersionUID = -4883187596839840069L;
  public long msgId;
  public String message;

  public Pong(long msgId, String message) {
    super();
    this.msgId = msgId;
    this.message = message;
  }

  public long getMsgId() {
    return msgId;
  }

  public void setMsgId(long msgId) {
    this.msgId = msgId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((message == null) ? 0 : message.hashCode());
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
    Pong other = (Pong) obj;
    if (message == null) {
      if (other.message != null)
        return false;
    } else if (!message.equals(other.message))
      return false;
    if (msgId != other.msgId)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Pong [msgId=" + msgId + ", message=" + message + "]";
  }

}

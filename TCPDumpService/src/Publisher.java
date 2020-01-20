import javax.xml.ws.Endpoint;

import it.unige.swplatforms.TCPDumpServiceNoAnt;

public class Publisher
{
   public static void main(String[] args)
   {
      Endpoint.publish("http://localhost:7654/TCPDumpService", new TCPDumpServiceNoAnt());
   }
}
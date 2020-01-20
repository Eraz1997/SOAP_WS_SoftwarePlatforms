import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import it.unige.swplatforms.TCPDumpServiceInterfaceNoAnt;

public class TestClient{
   public static void main(String[] args) throws Exception
   {
      URL url = new URL("http://localhost:7654/TCPDumpService");
      QName q0 = new QName("http://swplatforms.unige.it/", "TCPDumpServiceService");
	  Service service = Service.create(url, q0);

      QName q1 = new QName("http://swplatforms.unige.it/", "TCPDumpServicePort");
	  TCPDumpServiceInterfaceNoAnt port = service.getPort(q1, TCPDumpServiceInterfaceNoAnt.class);

	  if(!port.start())
		  return;
	  System.out.printf("[+] Started\n");
	  Thread.sleep(10000);
	  if(!port.stop())
		  return;
	  System.out.printf("[+] Stopped\n");
	  byte[] pcap = port.get_pcap();
	  if(pcap == null)
		  return;
	  System.out.printf("%s\n", new String(pcap));
	  
	  System.exit(0);

   }
}
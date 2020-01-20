package it.unige.swplatforms;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface TCPDumpServiceInterfaceNoAnt {

	@WebMethod boolean start();
	@WebMethod boolean stop();
	@WebMethod byte[] get_pcap();
}

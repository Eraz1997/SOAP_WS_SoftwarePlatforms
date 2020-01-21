from zeep import Client
import time
import base64

STRINGA = "esempio"

print ("Waiting for webserver container")
time.sleep(5)

print ("[+] Get Web Service Description Language")
cryptows = Client ("http://webservice:8080/axis2/services/CipherSolver?wsdl")
snifferws = Client ("http://webservice:8080/axis2/services/TCPDumpService?wsdl")

print ("[+] Start Sniffing ")
if not snifferws.service.start("eth0"):
	print ("[-] Start sniff error")
	exit ()

time.sleep(1)
print ("[+] Sending string for ROT 13")
rot = cryptows.service.decodeRot(STRINGA, 3)
if not rot:
	print ("[-] ROT13 error")
	exit ()

time.sleep(1)
print ("[+] ROT result: ", rot)
print ("[+] Stop Sniffing ")
if not snifferws.service.stop():
	print ("[-] Stop sniffing error")
	exit ()

print ("[+] Get traffic from the sniffer container")
out = snifferws.service.get_pcap()

time.sleep(1)
with open ("/results/traffic.pcap", 'wb+') as f:
	f.write(base64.b64decode(out))

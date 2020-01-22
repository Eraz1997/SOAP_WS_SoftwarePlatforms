from zeep import Client
import time
import base64

STRINGA = "esempio"

print ("[+] Get Web Service Description Language")
cryptows = Client ("http://ec2-35-180-125-104.eu-west-3.compute.amazonaws.com:8080/axis2/services/CipherSolver?wsdl")
snifferws = Client ("http://ec2-35-180-125-104.eu-west-3.compute.amazonaws.com:8080/axis2/services/TCPDumpService?wsdl")

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
with open ("./traffic.pcap", 'wb+') as f:
	f.write(base64.b64decode(out))

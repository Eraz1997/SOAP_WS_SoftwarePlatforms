package it.unige.swplatforms;
import java.io.File;
import java.nio.file.Files;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;   

//@WebService(endpointInterface = "it.unige.swplatforms.TCPDumpService")
public class TCPDumpService {

	private Process p;
	
	public TCPDumpService() {
		p = null;
	}
	public void test() {
		return;
	}
	
	public void log(String text) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		System.out.println((new String("[")).concat(dtf.format(now).concat((new String("] ")).concat(text))));
		return;
	}
	
	private boolean errorWithLog(String text) {
		log((new String("[ERROR] ")).concat(text));  
		return false;
	}
	
	//@Override
	public boolean start(String _interface) {
		log("Starting service");
		if(_interface == null || _interface.length() == 0)
			return errorWithLog("no interface selected");
		if (p != null && p.isAlive())
			return errorWithLog("service already started");
		p = null;
		try {
			p = Runtime.getRuntime().exec((new String("tcpdump -i ")).concat(_interface.concat(" -w out.pcap")));
			if (!p.isAlive()) 
				throw new IOException();
		} catch (IOException e) {
			return errorWithLog("process start failed");
		}
		log("Success");
		return true;
	}

	//@Override
	public boolean stop() {
		log("Stopping service");
		if(p == null || !p.isAlive())
			return errorWithLog("no process running (are you root?)");
		p.destroy();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(p != null && p.isAlive())
			return errorWithLog("process stop failed");
		log("Success");
		return true;
	}

	//@Override
	public byte[] get_pcap() {
		log("Getting pcap file");
		File f = new File("out.pcap");
		if (!f.exists()) {
			errorWithLog("file does not exist");
			return null;
		}
		byte[] fileContent;
		try {
			fileContent = Files.readAllBytes(f.toPath());
		} catch (IOException e) {
			errorWithLog("read failed");
			return null;
		}
		log("Success");
		return fileContent;
	}
	
}

package it.unige.swplatforms;
import javax.jws.WebService;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    

import java.io.File;
import java.nio.file.Files;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

@WebService(endpointInterface = "it.unige.swplatforms.TCPDumpService")
public class TCPDumpServiceNoAnt implements TCPDumpServiceInterfaceNoAnt {
	
	private Process p;
	
	public TCPDumpServiceNoAnt() {
		p = null;
	}

	private void log(String text) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		System.out.println("[" + dtf.format(now) + "] " + text);  
	}
	
	private boolean errorWithLog(String text) {
		log("ERROR: " + text);
		return false;
	}
	
	@Override
	public boolean start() {
		log("Starting service");
		if (p != null && p.isAlive())
			return errorWithLog("service already started");
		p = null;
		try {
			p = Runtime.getRuntime().exec("tcpdump -w out.pcap");
			if (!p.isAlive()) 
				throw new IOException();
		} catch (IOException e) {
			return errorWithLog("process start failed");
		}
		log("Success");
		return true;
	}

	@Override
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

	@Override
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

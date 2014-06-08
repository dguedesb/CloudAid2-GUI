package models;

import java.util.HashMap;

import usdl.constants.enums.CLOUDEnum;

public class CLOUDEnumMiddleMan {
	
	HashMap<String, String> QuantMiddleMan;
	HashMap<String,String> QualMiddleMan;
	
	HashMap<String, String> prettyPrint;
	
	public HashMap<String, String> getQuantMiddleMan() {
		return QuantMiddleMan;
	}

	public HashMap<String, String> getQualMiddleMan() {
		return QualMiddleMan;
	}
	
	public HashMap<String, String> getPrettyPrint() {
		return prettyPrint;
	}
	
	public CLOUDEnumMiddleMan()
	{
		QuantMiddleMan = new HashMap<String,String>();
		QualMiddleMan = new HashMap<String,String>();
		prettyPrint = new HashMap<String,String>();
		populate();
	}
	
	
	//let's populate our MiddleMan - KEY (user string concept) - VALUE (cloud concept)
	private void populate() {
		prettyPrint.put("Price", "price");
		//Qualitative Features
		QualMiddleMan.put("api", CLOUDEnum.API.getConceptString());
		prettyPrint.put("API", "api");
		QualMiddleMan.put("commandline", CLOUDEnum.COMMAND_LINE.getConceptString());
		prettyPrint.put("Command Line Interface", "commandline");
		QualMiddleMan.put("console", CLOUDEnum.CONSOLE.getConceptString());
		prettyPrint.put("Console Interface", "console");
		QualMiddleMan.put("gui", CLOUDEnum.GUI.getConceptString());
		prettyPrint.put("GUI", "gui");
		QualMiddleMan.put("web", CLOUDEnum.WEB.getConceptString());
		prettyPrint.put("Web Interface", "web");
		QualMiddleMan.put("interface", CLOUDEnum.OTHER_INTERFACE.getConceptString());
		prettyPrint.put("Other Interface", "interface");
		QualMiddleMan.put("consistency", CLOUDEnum.CONSISTENCY.getConceptString());
		prettyPrint.put("Consistency", "consistency");
		QualMiddleMan.put("durability", CLOUDEnum.DURABILITY.getConceptString());
		prettyPrint.put("Durability", "durability");
		QualMiddleMan.put("performance", CLOUDEnum.PERFORMANCE.getConceptString());
		prettyPrint.put("Performance", "performance");
		QualMiddleMan.put("reliability", CLOUDEnum.RELIABILITY.getConceptString());
		prettyPrint.put("Reliability", "reliability");
		QualMiddleMan.put("scalability", CLOUDEnum.SCALABILITY.getConceptString());
		prettyPrint.put("Scalability", "scalability");
		QualMiddleMan.put("security", CLOUDEnum.SECURITY.getConceptString());
		prettyPrint.put("Security", "security");
		QualMiddleMan.put("ssl", CLOUDEnum.SSL.getConceptString());
		prettyPrint.put("SSL", "ssl");
		QualMiddleMan.put("encryption", CLOUDEnum.ENCRYPTION.getConceptString());
		prettyPrint.put("Encryption", "encryption");
		QualMiddleMan.put("location", CLOUDEnum.LOCATION.getConceptString());
		prettyPrint.put("Location", "location");
		QualMiddleMan.put("monitoring", CLOUDEnum.MONITORING.getConceptString());
		prettyPrint.put("Monitoring", "monitoring");
		QualMiddleMan.put("platform", CLOUDEnum.PLATFORM.getConceptString());
		prettyPrint.put("Platform", "platform");
		QualMiddleMan.put("replication", CLOUDEnum.REPLICATION.getConceptString());
		prettyPrint.put("Replication", "replication");
		QualMiddleMan.put("computinginstance", CLOUDEnum.COMPUTINGINTANCE.getConceptString());
		prettyPrint.put("Computing Instance", "computinginstance");
		QualMiddleMan.put("cputype", CLOUDEnum.CPUTYPE.getConceptString());
		prettyPrint.put("CPU Type", "cputype");
		QualMiddleMan.put("graphicalcard", CLOUDEnum.GRAPHICALCARD.getConceptString());
		prettyPrint.put("Graphic card", "graphicalcard");
		QualMiddleMan.put("loadbalancing", CLOUDEnum.LOADBALANCING.getConceptString());
		prettyPrint.put("Load Balancing", "loadbalancing");
		QualMiddleMan.put("memoryallocation", CLOUDEnum.MEMORYALLOCATION.getConceptString());
		prettyPrint.put("Memory Allocation", "memoryallocation");
		QualMiddleMan.put("storagetype", CLOUDEnum.STORAGETYPE.getConceptString());
		prettyPrint.put("Storage Type", "storagetype");
		QualMiddleMan.put("failover", CLOUDEnum.FAILOVER.getConceptString());
		prettyPrint.put("Fail Over", "failover");
		QualMiddleMan.put("developercenter", CLOUDEnum.DEVELOPERCENTER.getConceptString());
		prettyPrint.put("Developer Center", "developercenter");
		QualMiddleMan.put("forum", CLOUDEnum.FORUM.getConceptString());
		prettyPrint.put("Forum", "forum");
		QualMiddleMan.put("livechat", CLOUDEnum.LIVECHAT.getConceptString());
		prettyPrint.put("Live Chat", "livechat");
		QualMiddleMan.put("manual", CLOUDEnum.MANUAL.getConceptString());
		prettyPrint.put("Manual", "manual");
		QualMiddleMan.put("support24_7", CLOUDEnum.SUPPORT_24_7.getConceptString());
		prettyPrint.put("Support 24/7", "support24_7");
		QualMiddleMan.put("supportteam", CLOUDEnum.SUPPORTTEAM.getConceptString());
		prettyPrint.put("Support Team", "supportteam");
		QualMiddleMan.put("videos", CLOUDEnum.VIDEOS.getConceptString());
		prettyPrint.put("Videos", "videos");
		QualMiddleMan.put("other_support", CLOUDEnum.OTHER_SUPPORT.getConceptString());
		prettyPrint.put("Other Support", "other_support");
		QualMiddleMan.put("opensource", CLOUDEnum.OPENSOURCE.getConceptString());
		prettyPrint.put("Open Source", "opensource");
		QualMiddleMan.put("proprietary", CLOUDEnum.PROPRIETARY.getConceptString());
		prettyPrint.put("Proprietary", "proprietary");
		QualMiddleMan.put("arch32bit", CLOUDEnum.BIT32.getConceptString());
		prettyPrint.put("32 Bit Architecture", "arch32bit");
		QualMiddleMan.put("arch64bit", CLOUDEnum.BIT64.getConceptString());
		prettyPrint.put("64 Bit Architecture", "arch64bit");
		QualMiddleMan.put("embedded", CLOUDEnum.EMBEDDED.getConceptString());
		prettyPrint.put("Embedded", "embedded");
		QualMiddleMan.put("mobile", CLOUDEnum.MOBILE.getConceptString());
		prettyPrint.put("Mobile", "mobile");
		QualMiddleMan.put("windows", CLOUDEnum.WINDOWS.getConceptString());
		prettyPrint.put("Windows", "windows");
		QualMiddleMan.put("unix", CLOUDEnum.UNIX.getConceptString());
		prettyPrint.put("Unix", "unix");
		QualMiddleMan.put("realtime", CLOUDEnum.REALTIME.getConceptString());
		prettyPrint.put("Real Time", "realtime");
		QualMiddleMan.put("backup_recovery", CLOUDEnum.BACKUP_RECOVERY.getConceptString());
		prettyPrint.put("Back-up Recovery", "backup_recovery");
		QualMiddleMan.put("redundancy", CLOUDEnum.REDUNDANCY.getConceptString());
		prettyPrint.put("Redundancy", "redundancy");
		QualMiddleMan.put("publicip", CLOUDEnum.PUBLICIP.getConceptString());
		prettyPrint.put("Public IP", "publicip");
		QualMiddleMan.put("elasticip", CLOUDEnum.ELASTICIP.getConceptString());
		prettyPrint.put("Elastic IP", "elasticip");
		QualMiddleMan.put("ipv4", CLOUDEnum.IPV4.getConceptString());
		prettyPrint.put("IPV4", "ipv4");
		QualMiddleMan.put("ipv6", CLOUDEnum.IPV6.getConceptString());
		prettyPrint.put("IPV6", "ipv6");
		QualMiddleMan.put("protocol", CLOUDEnum.PROTOCOL.getConceptString());
		prettyPrint.put("Protocol", "protocol");
		QualMiddleMan.put("language", CLOUDEnum.LANGUAGE.getConceptString());
		prettyPrint.put("Language", "language");
		QualMiddleMan.put("messageprotocol", CLOUDEnum.MESSAGEPROTOCOL.getConceptString());
		prettyPrint.put("Message Protocol", "messageprotocol");
		QualMiddleMan.put("messagetype", CLOUDEnum.MESSAGETYPE.getConceptString());
		prettyPrint.put("Message Type", "messagetype");
		QualMiddleMan.put("feature", CLOUDEnum.FEATURE.getConceptString());
		prettyPrint.put("Feature", "feature");
		
		
		//Quantitative Features
		QuantMiddleMan.put("availability", CLOUDEnum.AVAILABILITY.getConceptString());
		prettyPrint.put("Availability", "availability");
		QuantMiddleMan.put("cachesize", CLOUDEnum.CACHESIZE.getConceptString());
		prettyPrint.put("Cache size", "cachesize");
		QuantMiddleMan.put("cpuspeed", CLOUDEnum.CPUSPEED.getConceptString());
		prettyPrint.put("CPU Speed", "cpuspeed");
		QuantMiddleMan.put("datainexternal", CLOUDEnum.DATAINEXTERNAL.getConceptString());
		prettyPrint.put("Data IN External", "datainexternal");
		QuantMiddleMan.put("dataininternal", CLOUDEnum.DATAININTERNAL.getConceptString());
		prettyPrint.put("Data IN Internal", "dataininternal");
		QuantMiddleMan.put("dataoutexternal", CLOUDEnum.DATAOUTEXTERNAL.getConceptString());
		prettyPrint.put("Data OUT External", "dataoutexternal");
		QuantMiddleMan.put("dataoutinternal", CLOUDEnum.DATAOUTINTERNAL.getConceptString());
		prettyPrint.put("Data OUT Internal", "dataoutinternal");
		QuantMiddleMan.put("dataprocessed", CLOUDEnum.DATAPROCESSED.getConceptString());
		prettyPrint.put("Data Processed", "dataprocessed");
		QuantMiddleMan.put("disksize", CLOUDEnum.DISKSIZE.getConceptString());
		prettyPrint.put("Disk Size", "disksize");
		QuantMiddleMan.put("filesize", CLOUDEnum.FILESIZE.getConceptString());
		prettyPrint.put("File size", "filesize");
		QuantMiddleMan.put("memorysize", CLOUDEnum.MEMORYSIZE.getConceptString());
		prettyPrint.put("Memory size", "memorysize");
		QuantMiddleMan.put("networkdelay", CLOUDEnum.NETWORKDELAY.getConceptString());
		prettyPrint.put("Network delay", "networkdelay");
		QuantMiddleMan.put("networkinternalbandwidth", CLOUDEnum.NETWORKINTERNALBANDWIDTH.getConceptString());
		prettyPrint.put("Network Internal Bandwidth", "networkinternalbandwidth");
		QuantMiddleMan.put("networklatency", CLOUDEnum.NETWORKLATENCY.getConceptString());
		prettyPrint.put("Network Latency", "networklatency");
		QuantMiddleMan.put("networkpublicbandwidth", CLOUDEnum.NETWORKPUBLICBANDWIDTH.getConceptString());
		prettyPrint.put("Network Public Bandwidth", "networkpublicbandwidth");
		QuantMiddleMan.put("storagecapacity", CLOUDEnum.STORAGECAPACITY.getConceptString());
		prettyPrint.put("Storage Capacity", "storagecapacity");
		QuantMiddleMan.put("traffic", CLOUDEnum.TRAFFIC.getConceptString());
		prettyPrint.put("Traffic", "traffic");
		QuantMiddleMan.put("transferrate", CLOUDEnum.TRANSFERRATE.getConceptString());
		prettyPrint.put("Transferrate", "transferrate");
		QuantMiddleMan.put("apicalls", CLOUDEnum.APICALLS.getConceptString());
		prettyPrint.put("API Calls", "apicalls");
		QuantMiddleMan.put("applications", CLOUDEnum.APPLICATIONS.getConceptString());
		prettyPrint.put("Applications", "applications");
		QuantMiddleMan.put("copyrequests", CLOUDEnum.COPYREQUESTS.getConceptString());
		prettyPrint.put("Copy Requests", "copyrequests");
		QuantMiddleMan.put("cpucores", CLOUDEnum.CPUCORES.getConceptString());
		prettyPrint.put("CPU Cores", "cpucores");
		QuantMiddleMan.put("cpuflop", CLOUDEnum.CPUFLOP.getConceptString());
		prettyPrint.put("CPU Flop", "cpuflop");
		QuantMiddleMan.put("deleterequests", CLOUDEnum.DELETEREQUESTS.getConceptString());
		prettyPrint.put("DELETE Requests", "deleterequests");
		QuantMiddleMan.put("getrequests", CLOUDEnum.GETREQUESTS.getConceptString());
		prettyPrint.put("GET Requests", "getrequests");
		QuantMiddleMan.put("httprequests", CLOUDEnum.HTTPREQUEST.getConceptString());
		prettyPrint.put("HTTP Requests", "httprequests");
		QuantMiddleMan.put("httpsrequests", CLOUDEnum.HTTPSREQUEST.getConceptString());
		prettyPrint.put("HTTPS Requests", "httpsrequests");
		QuantMiddleMan.put("iooperations", CLOUDEnum.IOOPERATIONS.getConceptString());
		prettyPrint.put("I/O Operations", "iooperations");
		QuantMiddleMan.put("listrequests", CLOUDEnum.LISTREQUESTS.getConceptString());
		prettyPrint.put("LIST Requests", "listrequests");
		QuantMiddleMan.put("postrequests", CLOUDEnum.POSTREQUESTS.getConceptString());
		prettyPrint.put("POST Requests", "postrequests");
		QuantMiddleMan.put("putrequests", CLOUDEnum.PUTREQUESTS.getConceptString());
		prettyPrint.put("PUT Requests", "putrequests");
		QuantMiddleMan.put("queries", CLOUDEnum.QUERIES.getConceptString());
		prettyPrint.put("Queries", "queries");
		QuantMiddleMan.put("readsrequests", CLOUDEnum.READSREQUESTS.getConceptString());
		prettyPrint.put("READ Requests", "readsrequests");
		QuantMiddleMan.put("records", CLOUDEnum.RECORDS.getConceptString());
		prettyPrint.put("Records", "records");
		QuantMiddleMan.put("transactions", CLOUDEnum.TRANSACTIONS.getConceptString());
		prettyPrint.put("Transactions", "transactions");
		QuantMiddleMan.put("users", CLOUDEnum.USERS.getConceptString());
		prettyPrint.put("Users", "users");
		QuantMiddleMan.put("websites", CLOUDEnum.WEBSITES.getConceptString());
		prettyPrint.put("Websites", "websites");
		QuantMiddleMan.put("writesrequests", CLOUDEnum.WRITESREQUESTS.getConceptString());
		prettyPrint.put("WRITE Requests", "writesrequests");
		QuantMiddleMan.put("numberofips", CLOUDEnum.NUMBEROFIPS.getConceptString());
		prettyPrint.put("Number of IPS", "numberofips");
		QuantMiddleMan.put("messagenumber", CLOUDEnum.MESSAGENUMBER.getConceptString());
		prettyPrint.put("Message number", "messagenumber");
		QuantMiddleMan.put("dedicatedip", CLOUDEnum.DEDICATEDIP.getConceptString());
		prettyPrint.put("Dedicated IP", "dedicatedip");
	}
}
